package com.myprojects.assignment.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.assignment.R;
import com.myprojects.assignment.adapter.CardAdapter;
import com.myprojects.assignment.adapter.CardNavigation;
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.ui.companies.ShowCompaniesFragment;
import com.myprojects.assignment.ui.edit.EditCardFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements CardNavigation {

    private SearchView searchView;
    private RecyclerView cardRecyclerView;
    private CardAdapter cardAdapter;
    private List<CardData> cardDataList;
    private static final String TAG = "CompaniesFragment";
    private Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_search, container, false);
//
//        searchView = view.findViewById(R.id.search_view);
//        recyclerView = view.findViewById(R.id.recycler_view);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // Populate dummy data
//        cardDataList = createDummyCardList();
//        cardAdapter = new CardAdapter(getContext(), cardDataList, this);
//        recyclerView.setAdapter(cardAdapter);
//
//        // Search logic not implemented
//
//        return view;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardRecyclerView = view.findViewById(R.id.card_recycler_view);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Assuming cardDataList is populated with data
        CardAdapter adapter = new CardAdapter(getContext(), cardDataList, (CardNavigation) this);
        cardRecyclerView.setAdapter(adapter);

        Fragment fragment = new SearchResultsFragment();
        Log.d(TAG, "Navigating to fragment: " + fragment.getClass().getSimpleName());
        AppCompatActivity activity = (AppCompatActivity) context;
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, fragment) // Replace the current fragment
                .commit();
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

    public void navigateToEditCardFragment(CardData cardData) {
        Fragment editCardFragment = EditCardFragment.newInstance(cardData);
        AppCompatActivity activity = (AppCompatActivity) context;
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, editCardFragment)
                .addToBackStack(null)
                .commit();
        hideRecyclerView();
    }
}
