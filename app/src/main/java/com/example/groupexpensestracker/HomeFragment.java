package com.example.groupexpensestracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.widget.Button;
import android.database.Cursor;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;


public class HomeFragment extends Fragment {

    ExpandingList expandingList;
    DatabaseHelper myDb;
    Cursor cursor,innercursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.home_fragment, container, false );

        expandingList = root.findViewById(R.id.exp_listview);
        myDb = new DatabaseHelper(getActivity());
        cursor = myDb.getAllData();
        int j=0;
        if(cursor.moveToFirst()){
            do{
                ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout_members);
                String memId = cursor.getString(0);
                String memName = cursor.getString(1);
                ((TextView)item.findViewById(R.id.heading_item)).setText(memId+"."+memName);
                innercursor = myDb.getExpensebyId(memId);
                item.createSubItems(innercursor.getCount()+1);
                int sum = 0;
                if(innercursor.getCount()==0){
                    ((TextView)item.getSubItemView(0).findViewById(R.id.child_item)).setText("No Expenses Yet");
                }else{
                    if(innercursor.moveToFirst()){
                        int i=0;
                        do{
                            String expenseName = innercursor.getString(1);
                            String value = innercursor.getString(2);
                            String str = expenseName+" = Rs "+value;
                            ((TextView)item.getSubItemView(i).findViewById(R.id.child_item)).setText(str);
                            sum = sum + Integer.parseInt(value);
                            i++;
                        }while (innercursor.moveToNext());
                        ((TextView)item.getSubItemView(i).findViewById(R.id.child_item)).setText("Total = Rs "+sum);
                    }
                }
                item.setIndicatorIconRes(R.drawable.ic_person);
                switch (j%10){
                    case 0:
                        item.setIndicatorColorRes(R.color.pink);
                        break;
                    case 1:
                        item.setIndicatorColorRes(R.color.orange);
                        break;
                    case 2:
                        item.setIndicatorColorRes(R.color.yellow);
                        break;
                    case 3:
                        item.setIndicatorColorRes(R.color.lightBlue);
                        break;
                    case 4:
                        item.setIndicatorColorRes(R.color.violet);
                        break;
                    case 5:
                        item.setIndicatorColorRes(R.color.lightGreen);
                        break;
                    case 6:
                        item.setIndicatorColorRes(R.color.red);
                        break;
                    case 7:
                        item.setIndicatorColorRes(R.color.darkBlue);
                        break;
                    case 8:
                        item.setIndicatorColorRes(R.color.darkGreen);
                        break;
                    case 9:
                        item.setIndicatorColorRes(R.color.cyan);
                        break;
                }
                j++;
            }while (cursor.moveToNext());
        }
        return root;
    }
}




