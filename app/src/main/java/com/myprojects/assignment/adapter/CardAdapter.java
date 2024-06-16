package com.myprojects.assignment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.assignment.R;
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.ui.cabinet.CabinetFragment;
import com.myprojects.assignment.ui.companymedicines.CompanyMedicinesFragment;
import com.myprojects.assignment.ui.edit.EditCardFragment;
import com.myprojects.assignment.ui.shelf.ShelfFragment;
import com.myprojects.assignment.adapter.CardNavigation;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private static final String TAG = "CardAdapter";

    private List<CardData> cardList;
    private Context context;
    private CardNavigation cardNavigation;


    public CardAdapter(Context context, List<CardData> cardList, CardNavigation cardNavigation) {
        this.context = context;
        this.cardList = cardList;
        this.cardNavigation = cardNavigation;
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
            Toast.makeText(context, "Card clicked: " + cardData.getLabel(), Toast.LENGTH_SHORT).show();

            Fragment fragment;
            switch (cardData.getId()) {
                case "Cabinet":
                    fragment = new CabinetFragment();
                    break;
                case "Shelf":
                    fragment = new ShelfFragment();
                    break;
                case "Company":
                    fragment = new CompanyMedicinesFragment();
                    break;
                default:
                    Log.e(TAG, "Unknown card label: " + cardData.getId());
//                    Toast.makeText(context, "Unknown card id: " + cardData.getId(), Toast.LENGTH_SHORT).show();
                    return;
            }

            Log.d(TAG, "Navigating to fragment: " + fragment.getClass().getSimpleName());
            AppCompatActivity activity = (AppCompatActivity) context;
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, fragment)
                    .addToBackStack(null)
                    .commit();
        });

//        holder.editButton.setOnClickListener(v -> {
//            Fragment editCardFragment = EditCardFragment.newInstance(cardData);
//            AppCompatActivity activity = (AppCompatActivity) context;
//            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
//
//            // Hide the RecyclerView and show the EditCardFragment
//            transaction.replace(R.id.edit_card_container, editCardFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//
//            // Hide the RecyclerView
//            View recyclerView = activity.findViewById(R.id.card_recycler_view);
//            if (recyclerView != null) {
//                recyclerView.setVisibility(View.GONE);
//            }
//
//            // Show the EditCardFragment container
//            View editCardContainer = activity.findViewById(R.id.edit_card_container);
//            if (editCardContainer != null) {
//                editCardContainer.setVisibility(View.VISIBLE);
//            }
//
//        });
        holder.editButton.setOnClickListener(v -> {
            cardNavigation.navigateToEditCardFragment(cardData);
        });
    }

    @Override
    public int getItemCount() {
        if (cardList == null) {
            return 0;
        } else {
            return cardList.size();
        }
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
}
