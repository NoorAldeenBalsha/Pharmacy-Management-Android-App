package com.myprojects.assignment.ui.mypharma;

import android.content.Context;
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
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.adapter.CardNavigation;
import com.myprojects.assignment.ui.edit.EditCardFragment;

import java.util.Arrays;
import java.util.List;

public class MyPharmacyFragment extends Fragment implements CardNavigation {

    private RecyclerView cardRecyclerView;

    private Context context;

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
        Fragment editCardFragment = EditCardFragment.newInstance(cardData);
        AppCompatActivity activity = (AppCompatActivity) context;
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, editCardFragment)
                .addToBackStack(null)
                .commit();
        hideRecyclerView();
    }

    private List<CardData> createCardList() {
        // Replace with your actual data retrieval logic
        return Arrays.asList(
                new CardData("Cabinet 1", "Description 1", "Cabinet"),
                new CardData("Cabinet 2", "Description 2", "Cabinet")
        );
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
