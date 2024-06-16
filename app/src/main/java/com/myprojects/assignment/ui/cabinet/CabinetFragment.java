package com.myprojects.assignment.ui.cabinet;

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
import com.myprojects.assignment.databinding.FragmentHomeBinding;
import com.myprojects.assignment.features.CardData;
import com.myprojects.assignment.adapter.CardNavigation;
import com.myprojects.assignment.ui.edit.EditCardFragment;

import java.util.Arrays;
import java.util.List;

public class CabinetFragment extends Fragment implements CardNavigation {

    private FragmentHomeBinding binding;
    private Context context;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
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
        return Arrays.asList(
                new CardData("Shelf 1", "Description 1", "Shelf"),
                new CardData("Shelf 2", "Description 2", "Shelf"),
                new CardData("Shelf 3", "Description 2", "Shelf"),
                new CardData("Shelf 4", "Description 2", "Shelf")
        );
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