package com.ekotyoo.pokedex

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.ekotyoo.pokedex.settings.Theme
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_theme),
            getString(R.string.pref_value_auto)
        )?.apply {
            val mode = Theme.valueOf(this.uppercase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
        }
    }
}