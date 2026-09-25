package com.myprojects.assignment.ui.companies;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.assignment.R;
import com.myprojects.assignment.adapter.CardAdapter;
import com.myprojects.assignment.adapter.CardNavigation;
import com.myprojects.assignment.databinding.FragmentCompaniesBinding;
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.ui.edit.EditCardFragment;
import com.myprojects.assignment.ui.mypharma.MyPharmacyFragment;

import java.util.List;

public class CompaniesFragment extends Fragment implements CardNavigation {

    private FragmentCompaniesBinding binding;

    private static final String TAG = "CompaniesFragment";
    private Context context;
    private RecyclerView cardRecyclerView;
    private List<CardData> cardDataList;

    public CompaniesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardRecyclerView = view.findViewById(R.id.card_recycler_view);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Assuming cardDataList is populated with data
        CardAdapter adapter = new CardAdapter(getContext(), cardDataList, (CardNavigation) this);
        cardRecyclerView.setAdapter(adapter);

        Fragment fragment = new ShowCompaniesFragment();
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
        Toast.makeText(requireContext(), "you can't edit this just edit name of medicine", Toast.LENGTH_SHORT).show();
    }
}