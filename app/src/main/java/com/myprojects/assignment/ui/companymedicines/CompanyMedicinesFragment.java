package com.myprojects.assignment.ui.companymedicines;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.assignment.R;
import com.myprojects.assignment.adapter.CardAdapter;
import com.myprojects.assignment.databinding.FragmentCompaniesBinding;
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.adapter.CardNavigation;
import com.myprojects.assignment.ui.edit.EditCardFragment;

import java.util.*;

public class CompanyMedicinesFragment extends Fragment implements CardNavigation {

    private FragmentCompaniesBinding binding;
    private Context context;
    List<String> pharmaceuticals_name;
    Set<String> pharmaceuticals_name_Set;
    String PH_Company;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public CompanyMedicinesFragment(String PH_company) {
        // Required empty public constructor
        PH_Company=PH_company;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String databasepath = getContext().getDatabasePath("data_assignment.db").getPath();
        // Open the SQLite database
        SQLiteDatabase db = SQLiteDatabase.openDatabase(databasepath, null, SQLiteDatabase.OPEN_READONLY);
        // Query the database to get all rows from the "Pharmaceuticals" table
        Cursor cursor = db.rawQuery(
                "SELECT pharmaceuticals_name FROM Pharmaceuticals WHERE pharmaceuticals_company ="
                        +"'"
                        +PH_Company
                        +"'"
                , null);
        // Create a HashSet to store the unique pharmaceutical companies
        pharmaceuticals_name_Set = new HashSet<>();
        // Loop through the cursor and add the values to the HashSet
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String company = cursor.getString(cursor.getColumnIndex("pharmaceuticals_name"));
                    pharmaceuticals_name_Set.add(company);
                } while (cursor.moveToNext());
            }
        }
        // Convert the HashSet to an ArrayList if needed
        pharmaceuticals_name = new ArrayList<>(pharmaceuticals_name_Set);
        binding = FragmentCompaniesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<CardData> cardDataList = createShelfList();

        RecyclerView recyclerView = binding.cardRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new CardAdapter(getContext(), cardDataList, this));

        class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
            private final int verticalSpaceHeight;

            public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
                this.verticalSpaceHeight = verticalSpaceHeight;
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = verticalSpaceHeight;
            }
        }

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));

        return root;
    }

    private List<CardData> createShelfList() {
        // Replace with real data
        List<CardData>cardDataList=new ArrayList<>();
        for(String value :pharmaceuticals_name){
            for (int i=0;i<1;i++){
                cardDataList.add(new CardData(value, "", "Medicine"));
            }
        }
        return cardDataList;
    }

    @Override
    public void navigateToEditCardFragment(CardData cardData) {
        Fragment editCardFragment = EditCardFragment.newInstance(cardData);
        AppCompatActivity activity = (AppCompatActivity) context;
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, editCardFragment)
                .addToBackStack(null)
                .commit();
        hideRecyclerView();
    }

    private void showRecyclerView() {
        if (binding != null && binding.cardRecyclerView != null) {
            binding.cardRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void hideRecyclerView() {
        if (binding != null && binding.cardRecyclerView != null) {
            binding.cardRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
