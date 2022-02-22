package com.example.groupexpensestracker;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.WindowManager;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class HomePage extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_CLOSE = 0;
    private static final int POS_HOME = 1;
    private static final int POS_MEMBERS = 2;
    private static final int POS_ADD_EXPENSES = 3;
    private static final int POS_MODIFY_EXPENSES = 4;
    private static final int POS_CALCULATE_AND_TRACK_EXPENSES = 5;
    private static final int POS_LOGOUT = 7;


    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_MEMBERS),
                createItemFor(POS_ADD_EXPENSES),
                createItemFor(POS_MODIFY_EXPENSES),
                createItemFor(POS_CALCULATE_AND_TRACK_EXPENSES),
                new SpaceItem(260),
                createItemFor(POS_LOGOUT)
        ));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);
    }

    private DrawerItem createItemFor(int position){
        return new SimpleItem(screenIcons[position],screenTitles[position])
                .withIconTint(color(R.color.purple_700))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.purple_700))
                .withSelectedTextTint(color(R.color.black));
    }
    @SuppressLint("SupportAnnotationUsage")
    @ColorInt
    private int color(@ColorRes int res){
        return ContextCompat.getColor(this,res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i<ta.length();i++){
            int id = ta.getResourceId(i,0);
            if (id!=0){
                icons[i] = ContextCompat.getDrawable(this,id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_HOME){
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.container,homeFragment);
        }

        else if (position == POS_MEMBERS){
            MembersFragment membersFragment = new MembersFragment();
            transaction.replace(R.id.container,membersFragment);
        }

        else if (position == POS_ADD_EXPENSES){
            AddExpensesFragment addExpensesFragment = new AddExpensesFragment();
            transaction.replace(R.id.container,addExpensesFragment);
        }

        else if (position == POS_MODIFY_EXPENSES){
            ModifyExpensesFragment modifyExpensesFragment = new ModifyExpensesFragment();
            transaction.replace(R.id.container,modifyExpensesFragment);
        }

        else if (position == POS_CALCULATE_AND_TRACK_EXPENSES){
             CandTExpensesFragment candTExpensesFragment = new CandTExpensesFragment();
            transaction.replace(R.id.container,candTExpensesFragment);
        }

        else if (position == POS_LOGOUT){
            finish();
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }
}