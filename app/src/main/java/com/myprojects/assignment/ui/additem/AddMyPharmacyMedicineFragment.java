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

public class AddMyPharmacyMedicineFragment extends Fragment {

    private Spinner cabinetSpinner;
    private Spinner shelfSpinner;
    private Spinner companySpinner;
    private EditText medicineNameEditText;
    private Button addMedicineButton;
    DataBaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_medicine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cabinetSpinner = view.findViewById(R.id.spinner_cabinet);
        shelfSpinner = view.findViewById(R.id.spinner_shelf);
        companySpinner = view.findViewById(R.id.spinner_company);
        medicineNameEditText = view.findViewById(R.id.edittext_medicine_name);
        addMedicineButton = view.findViewById(R.id.button_add_medicine);
        db=new DataBaseHelper(requireContext());

        // Dummy data for the spinners
        String[] cabinets = {"خزينة: المسكنات", "خزينة: الأعصاب", "خزينة: مضاد للداء السكري", "خزينة: القلب", "خزينة: ضغط الدم"};
        String[] shelves = {"رف: شراب", "رف: حب"};
        String[] companies = {"pharmasyr", "Asia", "elsaad", "Biomed"};

        ArrayAdapter<String> cabinetAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, cabinets);
        cabinetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cabinetSpinner.setAdapter(cabinetAdapter);

        ArrayAdapter<String> shelfAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, shelves);
        shelfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shelfSpinner.setAdapter(shelfAdapter);

        ArrayAdapter<String> companyAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, companies);
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companySpinner.setAdapter(companyAdapter);

        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company = companySpinner.getSelectedItem().toString();
                String cabinet = cabinetSpinner.getSelectedItem().toString().split(":")[1].trim();
                String shelf = shelfSpinner.getSelectedItem().toString().split(":")[1].trim();
                String medicineName = medicineNameEditText.getText().toString().trim();

                if (medicineName.isEmpty()||cabinet.isEmpty()||shelf.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a medicine name or cabinet name or shelf ", Toast.LENGTH_SHORT).show();
                }
                if (db != null && db.InsertData(medicineName, shelf, cabinet, company)) {
                    Toast.makeText(requireContext(), "medicine name and cabinet name and shelf added successfully!", Toast.LENGTH_SHORT).show();
                    // Reset fields
                    medicineNameEditText.setText("");

                } else {
                    Toast.makeText(requireContext(), "Failed to save medicine name and cabinet name and shelf ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
