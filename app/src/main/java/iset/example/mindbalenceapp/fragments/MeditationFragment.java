package iset.example.mindbalenceapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import iset.example.mindbalenceapp.R;

public class MeditationFragment extends Fragment {

    public MeditationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meditation, container, false); // Replace "fragment_home" with your layout file name
    }



}