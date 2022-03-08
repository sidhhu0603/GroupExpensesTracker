package com.example.groupexpensestracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ExpenseGraphFragment extends Fragment {

    DatabaseHelper myDb;
    Cursor initial_cursor,cursor;
    TextView totalExpense;
    Button endTrip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.expense_graph, container, false );

        myDb = new DatabaseHelper(getActivity());
        totalExpense = root.findViewById(R.id.totalExpense);
        endTrip = root.findViewById(R.id.endTrip);
        cursor = myDb.getAllExpense();
        int sum1=0;
        if(cursor.moveToFirst()){
            do{
                Integer value = Integer.parseInt(cursor.getString(2));
                sum1+=value;
            }while (cursor.moveToNext());
        }
        totalExpense.setText("Total Trip Expense: Rs "+sum1);
        endTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_Builder = new AlertDialog.Builder(getActivity());
                a_Builder.setMessage("Are you sure you want to finish and close the app ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDropped = myDb.dropDatabase();
                        if(isDropped){
                            Toast.makeText(getActivity(),"Tracking Ended Successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getActivity(),GroupCreation.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT",true);
                            startActivity(intent);
                            getActivity().finish();
                        }else{
                            Toast.makeText(getActivity(),"Error in Ending Tracking",Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = a_Builder.create();
                alertDialog.setTitle("Alert !!!");
                alertDialog.show();
            }
        });
        initial_cursor = myDb.getExpenseType();
        List<String> ls = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        if(initial_cursor.moveToFirst()){
            do{
                String ExpType = initial_cursor.getString(0);
                set.add(ExpType);
            }while (initial_cursor.moveToNext());
        }
        System.out.println(set);
        List<String> Expense_Type = new ArrayList<String>();
        List<Integer> Expense_Value = new ArrayList<Integer>();
        for(String ExpType : set){
            Cursor cursor = myDb.getExpenseTypeByName(ExpType);
            Expense_Type.add(ExpType);
            int sum = 0;
            if(cursor.moveToFirst()){
                do{
                    String expValue = cursor.getString(2);
                    sum = sum + Integer.parseInt(expValue);
                }while (cursor.moveToNext());
            }
            Expense_Value.add(sum);
        }
        String [] ExpenseType_Arr = new String[Expense_Type.size()];
        ExpenseType_Arr = Expense_Type.toArray(ExpenseType_Arr);
        Integer [] ExpenseValue_Arr = new Integer[Expense_Value.size()];
        ExpenseValue_Arr = Expense_Value.toArray(ExpenseValue_Arr);
        List<PieEntry> pieEntries = new ArrayList<PieEntry>();
        for(int i=0;i<ExpenseType_Arr.length;i++){
            pieEntries.add(new PieEntry(ExpenseValue_Arr[i],ExpenseType_Arr[i]));
        }
        cursor = myDb.getAllData();
        cursor.moveToFirst();
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Value in Rupees");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.YELLOW);

        PieChart pieChart = (PieChart)root.findViewById(R.id.pie_chart);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setData(pieData);
        Legend legend = pieChart.getLegend();
        legend.setTextSize(15f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();
        pieChart.animateY(100, Easing.EasingOption. EaseInOutCubic);
        return root;

    }
}

