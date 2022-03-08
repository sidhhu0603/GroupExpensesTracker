package com.example.groupexpensestracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import android.widget.Toast;

import java.util.ArrayList;


public class AddExpensesFragment extends Fragment {

    DatabaseHelper myDb;
    Button addExpense;
    Spinner sp;
    TextView expenseType,value;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.add_expenses_fragment, container, false );

        myDb = new DatabaseHelper(getActivity());
        ArrayList<String> nameList = initializeSpinner();
        System.out.println(nameList);
        sp = root.findViewById(R.id.spinner);
        expenseType = root.findViewById(R.id.expenseType);
        value = root.findViewById(R.id.value);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,R.id.txt,nameList);
        sp.setAdapter(adapter);
        addExpense = root.findViewById(R.id.addExpense);
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memSelected = sp.getSelectedItem().toString();
                String ExpenseType = expenseType.getText().toString();
                String Value = value.getText().toString();
                if(memSelected.length()==0 || ExpenseType.length()==0 || Value.length()==0){
                    Toast.makeText(getActivity(),"Please Fill In Valid Data",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isInserted = myDb.insertDataTb2(ExpenseType,Value,memSelected.charAt(0)+"");
                if(isInserted)
                    Toast.makeText(getActivity(),"Expense Added",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(),"Expense Not Added",Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

    public ArrayList<String> initializeSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        try{
            Cursor res = myDb.getAllData();
            if(res.getCount()==0){
                return list;
            }else{
                while(res.moveToNext()){
                    String memId = res.getString(0);
                    String memName = res.getString(1);
                    list.add(memId+"."+memName);
                }
            }
        }
        catch (Exception e){
            System.out.println("Error "+e);
        }
        return list;
    }
}

