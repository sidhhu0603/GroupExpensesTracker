package com.example.groupexpensestracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ModifyExpensesFragment extends Fragment {

    TextView t4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.modify_expenses_fragment, container, false );

        t4 = root.findViewById(R.id.modifytext);
        return root;

    }
}
