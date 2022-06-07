package com.univalle.javiermurguia.proyectotelefericoturistico2.Fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.univalle.javiermurguia.proyectotelefericoturistico2.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}