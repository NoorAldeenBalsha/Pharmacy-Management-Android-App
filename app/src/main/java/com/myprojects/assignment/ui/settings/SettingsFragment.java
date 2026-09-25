package com.myprojects.assignment.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myprojects.assignment.R;

public class SettingsFragment extends Fragment {

    private static final String PREFS_NAME = "theme_prefs";
    private static final String PREF_THEME_KEY = "current_theme";
    private static final String PREF_SOUND_KEY = "sound_enabled";

    private Switch soundSwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        soundSwitch = view.findViewById(R.id.switch_sound);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, requireContext().MODE_PRIVATE);
        boolean soundEnabled = sharedPreferences.getBoolean(PREF_SOUND_KEY, true); // Default to true if not found
        soundSwitch.setChecked(soundEnabled);

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(PREF_SOUND_KEY, isChecked);
                editor.apply();
            }
        });

        Button defaultThemeButton = view.findViewById(R.id.button_default_theme);
        Button greenThemeButton = view.findViewById(R.id.button_green_theme);
        Button redThemeButton = view.findViewById(R.id.button_red_theme);

        defaultThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme(R.style.Theme_Assignment); // Change to default theme
            }
        });


        greenThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme(R.style.Theme_Assignment_Green); // Change to green theme
            }
        });

        redThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme(R.style.Theme_Assignment_Red); // Change to red theme
            }
        });
    }

    private void changeTheme(int themeId) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, requireContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_THEME_KEY, themeId);
        editor.apply();

        restartActivity();
    }

    private void restartActivity() {
        Intent intent = requireActivity().getIntent();
        requireActivity().finish();
        startActivity(intent);
    }
}
