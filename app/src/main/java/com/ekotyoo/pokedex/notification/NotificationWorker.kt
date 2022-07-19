package com.ekotyoo.pokedex.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.navigation.NavDeepLinkBuilder
import androidx.preference.PreferenceManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ekotyoo.core.domain.model.Pokemon
import com.ekotyoo.core.domain.repository.IPokemonRepository
import com.ekotyoo.pokedex.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val pokemonRepository: IPokemonRepository,
) : CoroutineWorker(appContext, workerParams) {

    init {
        createNotificationChannel(applicationContext)
    }

    override suspend fun doWork(): Result {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val notificationOn = preferences.getBoolean(
            applicationContext.getString(com.ekotyoo.core.R.string.pref_key_notification),
            false
        )

        if (notificationOn) {
            val randomPokemon = pokemonRepository.getRandomPokemon().first()
            if (randomPokemon != null) {
                createNotification(applicationContext, randomPokemon)
            }
        }

        return Result.Success()
    }

    private fun createNotification(context: Context, pokemon: Pokemon) {
        val bundle = Bundle().apply {
            putString("name", pokemon.name)
        }

        val detailPendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.pokemonDetailFragment)
            .setArguments(bundle)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(pokemon.nameFormatted)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(detailPendingIntent)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                applicationContext.getString(com.ekotyoo.core.R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "notification_pokemon"
    }
}