package com.myprojects.assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.myprojects.assignment.databinding.ActivityMainBinding;
import com.myprojects.assignment.ui.settings.SettingsFragment;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "theme_prefs";
    private static final String PREF_THEME_KEY = "current_theme";

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private MediaPlayer mediaPlayer;
    private boolean isSoundEnabled;
    static Context context;
    LinearLayout item1,item2,item3,item4,item5,item6,item7,item8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.click_sound);
        mediaPlayer.setOnCompletionListener(mp -> {
            // Release the MediaPlayer resources after completion
            mediaPlayer.release();
            mediaPlayer = null;
        });

        // Retrieve sound preference
        SharedPreferences sharedSoundPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        isSoundEnabled = sharedSoundPreferences.getBoolean("sound_enabled", true);


        // Load saved theme
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int themeId = sharedPreferences.getInt(PREF_THEME_KEY, R.style.Theme_Assignment);
        setTheme(themeId);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view ->
                Snackbar.make(view, "sariehmuhammad@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show()
        );

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_companies, R.id.nav_add_item,  R.id.nav_search)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        playClickSound();

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // Navigate to SettingsFragment
            AppCompatActivity activity = this;
            Fragment settingsFragment = new SettingsFragment();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, settingsFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void playClickSound() {
        if (isSoundEnabled && mediaPlayer != null) {
            mediaPlayer.seekTo(0); // Rewind to beginning if already playing
            mediaPlayer.start();
        }
    }
}
