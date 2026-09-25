package com.myprojects.assignment.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.assignment.R;
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.ui.cabinet.CabinetFragment;
import com.myprojects.assignment.ui.companymedicines.CompanyMedicinesFragment;
import com.myprojects.assignment.ui.edit.EditCardFragment;
import com.myprojects.assignment.ui.shelf.ShelfFragment;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private static final String TAG = "CardAdapter";

    private List<CardData> cardList;
    private Context context;
    private CardNavigation cardNavigation;
    private MediaPlayer mediaPlayer;
    private boolean isSoundEnabled;


    public CardAdapter(Context context, List<CardData> cardList, CardNavigation cardNavigation) {
        this.context = context;
        this.cardList = cardList;
        this.cardNavigation = cardNavigation;

        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(context, R.raw.click_sound);
        mediaPlayer.setVolume(1.0f, 1.0f); // Set volume (left, right)
        mediaPlayer.setOnCompletionListener(mp -> {
            // Release the MediaPlayer resources after completion
            mediaPlayer.release();
            mediaPlayer = null;
        });

        // Retrieve sound preference
        SharedPreferences sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE);
        isSoundEnabled = sharedPreferences.getBoolean("sound_enabled", true);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardData cardData = cardList.get(position);

        holder.labelTextView.setText(cardData.getLabel());
        holder.descriptionTextView.setText(cardData.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG, "Card clicked: " + cardData.getLabel());

            Fragment fragment;
            switch (cardData.getId()) {
                case "Cabinet":
                    fragment = new CabinetFragment( cardData.getLabel().toString().split(":")[1].trim());
                    break;
                case "Shelf":
                    fragment = new ShelfFragment( cardData.getLabel().toString().split(":")[1].trim(),cardData.getDescription().toString());
                    break;
                case "Company":
                    fragment = new CompanyMedicinesFragment(cardData.getLabel().toString());
                    break;
                default:
                    Log.e(TAG, "Unknown card label: " + cardData.getId());
                    return;
            }

            //Log.d(TAG, "Navigating to fragment: " + fragment.getClass().getSimpleName());
            AppCompatActivity activity = (AppCompatActivity) context;
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, fragment)
                    .addToBackStack(null)
                    .commit();

            // Play click sound
            playClickSound();
        });

        holder.editButton.setOnClickListener(v -> {
            cardNavigation.navigateToEditCardFragment(cardData);

            // Play click sound
            playClickSound();
        });
    }

    @Override
    public int getItemCount() {
        return cardList != null ? cardList.size() : 0;
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        TextView labelTextView;
        TextView descriptionTextView;
        ImageButton editButton;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            labelTextView = itemView.findViewById(R.id.card_label);
            descriptionTextView = itemView.findViewById(R.id.card_description);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }

    // Method to play click sound
    private void playClickSound() {
        if (isSoundEnabled && mediaPlayer != null) {
            mediaPlayer.seekTo(0); // Rewind to beginning if already playing
            mediaPlayer.start();
        }
    }
}
