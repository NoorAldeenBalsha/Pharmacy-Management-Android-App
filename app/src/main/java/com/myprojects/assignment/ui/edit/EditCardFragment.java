package com.myprojects.assignment.ui.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myprojects.assignment.R;
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.ui.home.HomeFragment;

public class EditCardFragment extends Fragment {

    private static final String ARG_CARD_DATA = "card_data";

    private CardData cardData;

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

        EditText labelEditText = view.findViewById(R.id.edit_card_label);
        EditText descriptionEditText = view.findViewById(R.id.edit_card_description);
        Button saveButton = view.findViewById(R.id.save_button);
        Button deleteButton = view.findViewById(R.id.delete_button);

        labelEditText.setText(cardData.getLabel());
        descriptionEditText.setText(cardData.getDescription());

        saveButton.setOnClickListener(v -> {
            // Update the card data
            cardData.setLabel(labelEditText.getText().toString());
            cardData.setDescription(descriptionEditText.getText().toString());

            // Return to the previous fragment
            getParentFragmentManager().popBackStack();
            showRecyclerView();
        });

        deleteButton.setOnClickListener(v -> {
            // Handle deletion logic

            // Return to the previous fragment
            getParentFragmentManager().popBackStack();
            showRecyclerView();
        });
    }

    private void showRecyclerView() {
        HomeFragment homeFragment = (HomeFragment) getParentFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        if (homeFragment != null) {
            homeFragment.showRecyclerView();
        }
    }
}
