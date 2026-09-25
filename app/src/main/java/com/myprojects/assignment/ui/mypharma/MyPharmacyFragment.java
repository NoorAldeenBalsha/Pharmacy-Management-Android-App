package com.myprojects.assignment.ui.mypharma;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.assignment.DataBaseHelper;
import com.myprojects.assignment.R;
import com.myprojects.assignment.adapter.CardAdapter;
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.adapter.CardNavigation;
import com.myprojects.assignment.ui.edit.EditCardFragment;

import java.util.*;

public class MyPharmacyFragment extends Fragment implements CardNavigation {

    private RecyclerView cardRecyclerView;
    private Context context;
    List<String> pharmaceutical_type;
    Set<String> pharmaceutical_type_Set;


    public MyPharmacyFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context.getApplicationContext(); // Use application context to prevent leaks
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String databasepath = getContext().getDatabasePath("data_assignment.db").getPath();
        // Open the SQLite database
        SQLiteDatabase db = SQLiteDatabase.openDatabase(databasepath, null, SQLiteDatabase.OPEN_READONLY);
        // Query the database to get all rows from the "Pharmaceuticals" table
        Cursor cursor = db.rawQuery("SELECT pharmaceuticals_type FROM Pharmaceuticals", null);
        // Create a HashSet to store the unique pharmaceutical companies
        pharmaceutical_type_Set = new HashSet<>();
        // Loop through the cursor and add the values to the HashSet
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String company = cursor.getString(cursor.getColumnIndex("pharmaceuticals_type"));
                    pharmaceutical_type_Set.add(company);
                } while (cursor.moveToNext());
            }
        }
        // Convert the HashSet to an ArrayList if needed
        pharmaceutical_type = new ArrayList<>(pharmaceutical_type_Set);
        // Close the cursor and database connection
        cursor.close();
        db.close();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cardRecyclerView = view.findViewById(R.id.card_recycler_view);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Populate cardDataList with dummy data
        List<CardData> cardDataList = createCardList();

        CardAdapter adapter = new CardAdapter(getContext(), cardDataList, this);
        cardRecyclerView.setAdapter(adapter);

        // Adding item decoration for spacing
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        cardRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));
        return view;
    }


    @Override
    public void navigateToEditCardFragment(CardData cardData) {
        Toast.makeText(requireContext(), "you can't edit this just edit name of medicine", Toast.LENGTH_SHORT).show();
    }

    private List<CardData> createCardList() {
        // Replace with your actual data retrieval logic
        List<CardData>cardDataList=new ArrayList<>();
        for(String value :pharmaceutical_type){
            for (int i=0;i<1;i++){
                cardDataList.add(new CardData("خزينة: "+value, "نوع الدواء", "Cabinet"));
            }
        }
        return cardDataList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cardRecyclerView = null; // Prevent memory leaks
    }

    public void showRecyclerView() {
        if (cardRecyclerView != null) {
            cardRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void hideRecyclerView() {
        if (cardRecyclerView != null) {
            cardRecyclerView.setVisibility(View.GONE);
        }
    }

    // Custom item decoration for spacing between RecyclerView items
    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
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
