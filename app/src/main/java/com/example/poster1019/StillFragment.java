package com.example.poster1019;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Contains the still image fragment that acts as one of the pages for the app
 */
public class StillFragment extends Fragment {
    public StillFragment(){
        super(R.layout.fragment_still);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
