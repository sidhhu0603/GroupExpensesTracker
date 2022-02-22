package com.example.groupexpensestracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CandTExpensesFragment extends Fragment {

    TextView t5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.c_and_t_expenses_fragment, container, false );

        t5 = root.findViewById(R.id.candttext);
        return root;

    }
}
