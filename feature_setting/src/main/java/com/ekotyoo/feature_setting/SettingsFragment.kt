package com.ekotyoo.feature_setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ekotyoo.core.utils.Theme
import com.ekotyoo.pokedex.MainActivity
import com.ekotyoo.pokedex.notification.NotificationWorker
import java.util.*
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val listPreference =
            findPreference<ListPreference>(getString(com.ekotyoo.core.R.string.pref_key_theme))
        listPreference?.setOnPreferenceChangeListener { _, newValue ->
            val themeMode = newValue as String
            updateTheme(Theme.valueOf(themeMode.uppercase(Locale.getDefault())).value)
            true
        }

        val switchPreference =
            findPreference<SwitchPreference>(getString(com.ekotyoo.core.R.string.pref_key_notification))
        switchPreference?.setOnPreferenceChangeListener { _, newValue ->
            val notificationOn = newValue as Boolean
            val notificationWorker = PeriodicWorkRequestBuilder<NotificationWorker>(
                1, TimeUnit.DAYS
            ).setInitialDelay(1, TimeUnit.MINUTES).addTag(NOTIFICATION_WORKER_TAG).build()
            if (notificationOn) {
                WorkManager.getInstance(requireContext()).enqueue(notificationWorker)
            } else {
                WorkManager.getInstance(requireContext())
                    .cancelAllWorkByTag(NOTIFICATION_WORKER_TAG)
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title =
            getString(com.ekotyoo.core.R.string.settings)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as MainActivity).supportActionBar?.title =
            getString(com.ekotyoo.core.R.string.app_name)
    }

    private fun updateTheme(mode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(mode)
        requireActivity().recreate()
        return true
    }

    companion object {
        private const val NOTIFICATION_WORKER_TAG = "notification_worker_tag"
    }
}