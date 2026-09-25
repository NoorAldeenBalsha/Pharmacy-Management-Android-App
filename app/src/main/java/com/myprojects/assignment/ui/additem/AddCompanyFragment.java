package com.myprojects.assignment.ui.additem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.myprojects.assignment.DataBaseHelper;
import com.myprojects.assignment.R;

public class AddCompanyFragment extends Fragment {

    private EditText nameEditText, descriptionEditText;
    private Button saveButton;
    private DataBaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_company, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameEditText = view.findViewById(R.id.edit_text_name);
        descriptionEditText = view.findViewById(R.id.edit_text_description);
        saveButton = view.findViewById(R.id.button_save);

        // Initialize the DataBaseHelper object
        db = new DataBaseHelper(requireContext());

        saveButton.setOnClickListener(v -> saveCompany());
    }

    private void saveCompany() {
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the company
        if (db != null && db.InsertData(" ", " ", " ", name)) {
            Toast.makeText(requireContext(), "Company added successfully!", Toast.LENGTH_SHORT).show();

            // Reset fields
            nameEditText.setText("");
            descriptionEditText.setText("");

            // Navigate to the main screen
            NavHostFragment.findNavController(this).popBackStack();
        } else {
            Toast.makeText(requireContext(), "Failed to save company", Toast.LENGTH_SHORT).show();
        }
    }
}