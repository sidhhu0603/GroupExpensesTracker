package com.example.groupexpensestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {

    TabLayout tblyt;
    ViewPager vwpgr;
    float v=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tblyt = findViewById(R.id.tab_layout);
        vwpgr = findViewById(R.id.view_pager);

        tblyt.addTab(tblyt.newTab().setText("Login"));
        tblyt.addTab(tblyt.newTab().setText("Sign-Up"));
        tblyt.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),  this, tblyt.getTabCount());
        vwpgr.setAdapter(adapter);
        vwpgr.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tblyt));

        tblyt.setTranslationY(300);

        tblyt.setAlpha(v);
        tblyt.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

        
    }
}

