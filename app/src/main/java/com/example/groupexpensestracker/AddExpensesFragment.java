package com.example.groupexpensestracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AddExpensesFragment extends Fragment {

    TextView t3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.add_expenses_fragment, container, false );

        t3 = root.findViewById(R.id.addexptext);
        return root;

    }
}
