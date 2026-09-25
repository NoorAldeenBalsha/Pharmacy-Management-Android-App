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


public class AddCabinetFragment extends Fragment {

    private EditText cabinetNameEditText,cabinetDescriptionEditText;
    private Button addCabinetButton;
    DataBaseHelper  db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_cabinet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cabinetNameEditText = view.findViewById(R.id.edittext_cabinet_name);
        cabinetDescriptionEditText = view.findViewById(R.id.edittext_cabinet_description);
        addCabinetButton = view.findViewById(R.id.button_add_cabinet);
        db = new DataBaseHelper(requireContext());
        addCabinetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cabinetName = cabinetNameEditText.getText().toString().trim();
                String cabinetDescription = cabinetDescriptionEditText.getText().toString().trim();

                if (cabinetName.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a cabinet name", Toast.LENGTH_SHORT).show();
                }
                if (db != null && db.InsertData(" ", " ", cabinetName, " ")) {
                    Toast.makeText(requireContext(), "cabinet name added successfully!", Toast.LENGTH_SHORT).show();

                    // Reset fields
                    cabinetNameEditText.setText("");
                    cabinetDescriptionEditText.setText("");

                } else {
                    Toast.makeText(requireContext(), "Failed to save cabinet name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
