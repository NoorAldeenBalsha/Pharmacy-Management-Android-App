package com.myprojects.assignment.ui.search;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.assignment.R;
import com.myprojects.assignment.adapter.ExpandableListAdapter;
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.models.Cabinet;
import com.myprojects.assignment.models.Company;
import com.myprojects.assignment.models.Medicine;
import com.myprojects.assignment.models.Shelf;

import java.util.*;

public class SearchResultsFragment extends Fragment {
    private SearchView searchView;
    private List<Medicine> allMedicine;
    private RecyclerView expandableRecyclerView;
    private ExpandableListAdapter expandableListAdapter;
    private List<Company> allCompanies; // Original unfiltered list
    private Context context;
    private List<String> pharmaceuticals_company, pharmaceutical_type, pharmaceuticals_type_medication, pharmaceuticals_name;
    private Set<String> pharmaceuticals_company_Set, pharmaceutical_type_Set, pharmaceuticals_type_medication_Set, pharmaceutical_name_Set;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String databasePath = getContext().getDatabasePath("data_assignment.db").getPath();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READONLY);
        populateDataLists(db);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        expandableRecyclerView = root.findViewById(R.id.expandable_recycler_view);
        expandableRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Initialize and set the adapter with dummy data
        allCompanies = createDummyData(db);
        expandableListAdapter = new ExpandableListAdapter(getContext(), allCompanies);
        expandableRecyclerView.setAdapter(expandableListAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        expandableRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));
        searchView=root.findViewById(R.id.search_view);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        db.close();
        return root;
    }

    private void filterList(String newText) {
        List<Company> fliterlist=new ArrayList<>();
        for(Company company :allCompanies){
            if(company.getName().toLowerCase().contains(newText.toLowerCase())){
                fliterlist.add(company);
            }
        }
        if(fliterlist.isEmpty()){

        }else {
            expandableListAdapter.setFilterList(fliterlist);
        }
    }
    private void populateDataLists(SQLiteDatabase db) {
        populateList(db, "pharmaceuticals_company", pharmaceuticals_company_Set, pharmaceuticals_company);
        populateList(db, "pharmaceuticals_type", pharmaceutical_type_Set, pharmaceutical_type);
        populateList(db, "pharmaceuticals_type_medication", pharmaceuticals_type_medication_Set, pharmaceuticals_type_medication);
    }
    @SuppressLint("Range")
    private void populateList(SQLiteDatabase db, String columnName, Set<String> set, List<String> list) {
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + columnName + " FROM Pharmaceuticals", null);
        set = new HashSet<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    set.add(cursor.getString(cursor.getColumnIndex(columnName)));
                } while (cursor.moveToNext());
            }
        }
        list = new ArrayList<>(set);
        cursor.close();
    }
    private List<Company> createDummyData(SQLiteDatabase db) {
        Set<String> uniqueCompanies = new HashSet<>();
        Set<String> uniqueCabinets = new HashSet<>();
        Set<String> uniqueShelves = new HashSet<>();
        populateUniqueValues(db, uniqueCompanies, uniqueCabinets, uniqueShelves);
        List<Company> companies = new ArrayList<>();
        for (String companyName : uniqueCompanies) {
            List<Cabinet> cabinets = new ArrayList<>();
            for (String cabinetName : uniqueCabinets) {
                List<Shelf> shelves = new ArrayList<>();
                for (String shelfName : uniqueShelves) {
                    List<Medicine> medicines = getMedicines(db, shelfName, cabinetName, companyName);
                    shelves.add(new Shelf("Shelf", shelfName, "نوع الدواء", medicines));
                }
                cabinets.add(new Cabinet("Cabinet", cabinetName, "نوع المرض", shelves));
            }
            companies.add(new Company("Company", companyName, "الشركة", cabinets));
        }
        return companies;
    }
    @SuppressLint("Range")
    private void populateUniqueValues(SQLiteDatabase db, Set<String> uniqueCompanies, Set<String> uniqueCabinets, Set<String> uniqueShelves) {
        Cursor cursor = db.rawQuery("SELECT DISTINCT pharmaceuticals_company, pharmaceuticals_type, pharmaceuticals_type_medication FROM Pharmaceuticals", null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    uniqueCompanies.add(cursor.getString(cursor.getColumnIndex("pharmaceuticals_company")));
                    uniqueCabinets.add(cursor.getString(cursor.getColumnIndex("pharmaceuticals_type")));
                    uniqueShelves.add(cursor.getString(cursor.getColumnIndex("pharmaceuticals_type_medication")));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }
    private List<Medicine> getMedicines(SQLiteDatabase db, String shelfName, String cabinetName, String companyName) {
        List<Medicine> medicines = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT pharmaceuticals_name FROM Pharmaceuticals WHERE pharmaceuticals_type_medication = ? AND pharmaceuticals_type = ? AND pharmaceuticals_company = ?",
                new String[]{shelfName, cabinetName, companyName});

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String medicineName = cursor.getString(cursor.getColumnIndex("pharmaceuticals_name"));
                    medicines.add(new Medicine("Medicine", medicineName, "الدواء"));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return medicines;
    }
    private static class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;

        VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}
