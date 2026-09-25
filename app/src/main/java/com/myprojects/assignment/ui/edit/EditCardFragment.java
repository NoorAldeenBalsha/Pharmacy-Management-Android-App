package com.myprojects.assignment.ui.edit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.myprojects.assignment.DataBaseHelper;
import com.myprojects.assignment.MainActivity;
import com.myprojects.assignment.R;
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.ui.home.HomeFragment;
import com.myprojects.assignment.ui.mypharma.MyPharmacyFragment;
import com.myprojects.assignment.ui.shelf.ShelfFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditCardFragment extends Fragment {
    private static final String ARG_CARD_DATA = "card_data";
    private CardData cardData;
    private String oldValue;
    private EditText labelEditText;
    int id;
    private DataBaseHelper db;

    public EditCardFragment() {
        // Required empty public constructor
    }

    public static EditCardFragment newInstance(CardData cardData) {
        EditCardFragment fragment = new EditCardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CARD_DATA, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = (CardData) getArguments().getSerializable(ARG_CARD_DATA);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DataBaseHelper(requireContext());
        labelEditText = view.findViewById(R.id.edit_card_label);
        Button saveButton = view.findViewById(R.id.save_button);
        Button deleteButton = view.findViewById(R.id.delete_button);
        Intent goToMain = new Intent(requireContext(), MainActivity.class);

        labelEditText.setText(cardData.getLabel());
        oldValue = cardData.getLabel().toString().trim();
        id=db.getPharmaceuticalsId(oldValue.toString());
        saveButton.setOnClickListener(v -> {
            String newValue = labelEditText.getText().toString().trim();
            if (!newValue.equals(oldValue)) {
                // Update the card data

                cardData.setLabel(newValue);
                // Save the changes to the database
                // ...
            }
            db.updatePharmaceuticalsName(id,newValue);
            startActivity(goToMain);
        });

        deleteButton.setOnClickListener(v -> {
            // Handle deletion logic
            db.deletePharmaceuticals(id);
            startActivity(goToMain);
        });
    }
    private void showRecyclerView() {
        HomeFragment homeFragment = (HomeFragment) getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        if (homeFragment != null) {
            homeFragment.showRecyclerView();
        }
    }
}
