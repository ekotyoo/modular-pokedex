package com.ekotyoo.pokedex.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.ekotyoo.pokedex.MainActivity
import com.ekotyoo.pokedex.R
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val listPreference = findPreference<ListPreference>(getString(R.string.pref_key_theme))
        listPreference?.setOnPreferenceChangeListener { _, newValue ->
            val themeMode = newValue as String
            updateTheme(Theme.valueOf(themeMode.uppercase(Locale.getDefault())).value)
            true
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title = getString(R.string.settings)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as MainActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    private fun updateTheme(mode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(mode)
        requireActivity().recreate()
        return true
    }
}

enum class Theme (val value: Int) {
    AUTOMATIC(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    DARK(AppCompatDelegate.MODE_NIGHT_YES),
    LIGHT(AppCompatDelegate.MODE_NIGHT_NO)
}