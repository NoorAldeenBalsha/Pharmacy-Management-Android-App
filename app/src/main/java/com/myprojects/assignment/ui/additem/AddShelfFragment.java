package com.myprojects.assignment.ui.additem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myprojects.assignment.DataBaseHelper;
import com.myprojects.assignment.R;

public class AddShelfFragment extends Fragment {

    private Spinner cabinetSpinner;
    private EditText shelfNameEditText;
    private EditText shelfDescriptionEditText;
    private Button addShelfButton;
    DataBaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_shelf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cabinetSpinner = view.findViewById(R.id.spinner_cabinet);
        shelfNameEditText = view.findViewById(R.id.edittext_shelf_name);
        shelfDescriptionEditText = view.findViewById(R.id.edittext_shelf_description);
        addShelfButton = view.findViewById(R.id.button_add_shelf);
        db =new DataBaseHelper(requireContext());

        // Dummy data for the spinners
        String[] cabinets = {"خزينة: المسكنات", "خزينة: الأعصاب", "خزينة: مضاد للداء السكري", "خزينة: القلب", "خزينة: ضغط الدم"};

        ArrayAdapter<String> cabinetAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, cabinets);
        cabinetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cabinetSpinner.setAdapter(cabinetAdapter);

        addShelfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cabinet = cabinetSpinner.getSelectedItem().toString().split(":")[1].trim();
                String shelfName = shelfNameEditText.getText().toString().trim();
                String shelfDescription = shelfDescriptionEditText.getText().toString().trim();

                if (shelfName.isEmpty()||cabinet.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a shelf name or cabinet name", Toast.LENGTH_SHORT).show();
                }
                if (db != null && db.InsertData(" ", shelfName, cabinet, " ")) {
                    Toast.makeText(requireContext(), " cabinet name and shelf added successfully!", Toast.LENGTH_SHORT).show();
                    // Reset fields
                    shelfNameEditText.setText("");

                } else {
                    Toast.makeText(requireContext(), "Failed to save  cabinet name and shelf ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
