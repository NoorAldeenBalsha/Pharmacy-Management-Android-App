package com.myprojects.assignment.util;

import com.myprojects.assignment.R;
import android.content.Context;
import android.media.MediaPlayer;

public class SoundUtil {

    public static MediaPlayer mediaPlayer;

    public static void playClickSound(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.click_sound);
        }
        mediaPlayer.seekTo(0); // Rewind to beginning if already playing
        mediaPlayer.setVolume(1.0f, 1.0f); // Set volume to maximum
        mediaPlayer.start(); // Start playback
    }

    public static void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
