package com.myprojects.assignment.ui.additem;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.myprojects.assignment.R;

public class RunAddItemFragment extends Fragment {

    private static final String TAG = "RunAddItemFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Navigate to AddItemFragment
        Fragment fragment = new AddItemFragment();
        Log.d(TAG, "Navigating to fragment: " + fragment.getClass().getSimpleName());
        AppCompatActivity activity = (AppCompatActivity) getContext();
        if (activity != null) {
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, fragment)
                    .commit();
        }
    }
}
