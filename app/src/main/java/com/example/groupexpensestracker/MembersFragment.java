package com.example.groupexpensestracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MembersFragment extends Fragment {

    TextView t2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.members_fragment, container, false );

        t2 = root.findViewById(R.id.membertext);

        return root;

    }
}