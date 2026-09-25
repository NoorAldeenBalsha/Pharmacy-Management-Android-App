package com.myprojects.assignment.ui.additem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.myprojects.assignment.R;

public class AddItemFragment extends Fragment {

    private static final String TAG = "AddItemFragment";

    private Spinner itemTypeSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemTypeSpinner = view.findViewById(R.id.spinner_item_types);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.item_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemTypeSpinner.setAdapter(adapter);

        itemTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemType = parent.getItemAtPosition(position).toString();
                navigateToFragment(selectedItemType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void navigateToFragment(String itemType) {
        Fragment fragment = null;
        switch (itemType) {
            case "Company":
                fragment = new AddCompanyFragment();
                break;
            case "Cabinet":
                fragment = new AddCabinetFragment();
                break;
            case "Shelf":
                fragment = new AddShelfFragment();
                break;
            case "Company Medicine":
                fragment = new AddCompanyMedicineFragment();
                break;
            case "Medicine":
                fragment = new AddMyPharmacyMedicineFragment();
                break;

        }
        if (fragment != null) {
            Log.d(TAG, "Navigating to fragment: " + fragment.getClass().getSimpleName());
            AppCompatActivity activity = (AppCompatActivity) getContext();
            if (activity != null) {
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.form_container, fragment)
                        .commit();
            }
        }
    }
}
