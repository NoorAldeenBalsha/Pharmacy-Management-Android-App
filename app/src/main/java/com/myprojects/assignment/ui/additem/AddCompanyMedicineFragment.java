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

public class AddCompanyMedicineFragment extends Fragment {

    private Spinner companySpinner;
    private EditText medicineNameEditText;
    private EditText medicineDescriptionEditText;
    private Button addMedicineButton;
    DataBaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_company_medicine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        companySpinner = view.findViewById(R.id.spinner_company);
        medicineNameEditText = view.findViewById(R.id.edittext_medicine_name);
        medicineDescriptionEditText = view.findViewById(R.id.edittext_medicine_description);
        addMedicineButton = view.findViewById(R.id.button_add_medicine);
        db=new DataBaseHelper(requireContext());
        // Dummy data for the spinners
        String[] companies = {"pharmasyr", "Asia", "elsaad", "Biomed"};
        ArrayAdapter<String> companyAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, companies);
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companySpinner.setAdapter(companyAdapter);

        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company = companySpinner.getSelectedItem().toString();
                String medicineName = medicineNameEditText.getText().toString().trim();
                String medicineDescription = medicineDescriptionEditText.getText().toString().trim();

                if (medicineName.isEmpty()||company.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a medicine name or company", Toast.LENGTH_SHORT).show();
                }
                if (db != null && db.InsertData(medicineName, " ", " ", company)) {
                    Toast.makeText(requireContext(), "medicine name and company added successfully!", Toast.LENGTH_SHORT).show();
                    // Reset fields
                    medicineNameEditText.setText("");

                } else {
                    Toast.makeText(requireContext(), "Failed to save medicine name and company", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
