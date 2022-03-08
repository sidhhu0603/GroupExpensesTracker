package com.example.groupexpensestracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ModifyExpensesFragment extends Fragment {

    DatabaseHelper myDb;
    Spinner sp_mem,sp_exp;
    TextView expValue,warningText;
    Button updateBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.modify_expenses_fragment, container, false );

        myDb = new DatabaseHelper(getActivity());
        ArrayList<String> nameList = initializeMemSpinner();
        sp_mem = root.findViewById(R.id.sp_mem);
        sp_exp = root.findViewById(R.id.sp_exp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,R.id.txt,nameList);
        sp_mem.setAdapter(adapter);
        updateBtn = root.findViewById(R.id.updateBtn);
        warningText = root.findViewById(R.id.warningText);
        sp_mem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String memSelected = sp_mem.getSelectedItem().toString();
                ArrayList<String> ExpenseList = initializeExpSpinner(memSelected.charAt(0)+"");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,R.id.txt,ExpenseList);
                sp_exp.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ArrayList<String> ExpenseList = initializeExpSpinner("1");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,R.id.txt,ExpenseList);
                sp_exp.setAdapter(adapter);
            }
        });
        sp_exp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String memSelected = sp_mem.getSelectedItem().toString();
                String expSelected = sp_exp.getSelectedItem().toString();
                Cursor cursor = myDb.getValueByExpenseNameAndId(expSelected,memSelected.charAt(0)+"");
                while(cursor.moveToNext()){
                    String Value = cursor.getString(2);
                    expValue = root.findViewById(R.id.expValue);
                    expValue.setHint("Current Value: Rs "+Value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int items = sp_exp.getAdapter().getCount();
                expValue = root.findViewById(R.id.expValue);
                String value = expValue.getText().toString();
                if(items==0){
                    warningText.setText("You cannot modify this data");
                    return;
                }else{
                    if(value.length()==0){
                        warningText.setText("Please enter a valid value");
                        return;
                    }else{
                        warningText.setText("");
                        String memSelected = sp_mem.getSelectedItem().toString();
                        String expSelected = sp_exp.getSelectedItem().toString();
                        boolean isUpdated = myDb.updateByIdAndExpense(expSelected,memSelected.charAt(0)+"",value);
                        if(isUpdated)
                            Toast.makeText(getActivity(),"Data Modified",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(),"Data Not Modified",Toast.LENGTH_LONG).show();
                        expValue.setText("");
                        expValue.setHint("Current Value: Rs "+value);
                    }
                }
            }
        });
        return root;
    }
    public ArrayList<String> initializeMemSpinner(){
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

    public ArrayList<String> initializeExpSpinner(String Id){
        ArrayList<String> list = new ArrayList<String>();
        try{
            Cursor res = myDb.getExpensebyId(Id);
            if(res.getCount()==0){
                expValue =getActivity().findViewById(R.id.expValue);
                expValue.setHint("Currently having no expense");
                return list;
            }else{
                while(res.moveToNext()){
                    String Expense = res.getString(1);
                    list.add(Expense);
                }
            }
        }
        catch (Exception e){
            System.out.println("Error "+e);
        }
        return list;

    }
}



