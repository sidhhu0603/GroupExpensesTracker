package com.example.groupexpensestracker;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;

public class AddMembers extends AppCompatActivity{

    DatabaseHelper myDb;
    String GroupSize;
    int Size;

    public void AddingMember(int i){
        final Button addMember = findViewById(R.id.addMember);
        if(i==Size){
            addMember.setText("Submit");
        }
        if(i>Size){
            return;
        }
        final int id = i;
        final TextView memberName = findViewById(R.id.memberName);
        memberName.setHint(id+". Member Name");
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memberName.getText().length()==0) {
                    TextView warningText = findViewById(R.id.warningText);
                    warningText.setText("Please enter a valid member name");
                    return;
                }
                boolean isInserted = myDb.insertDataTb1(memberName.getText().toString());
                if(isInserted)
                    Toast.makeText(AddMembers.this,"Member Added",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AddMembers.this,"Member Not Added",Toast.LENGTH_LONG).show();

                System.out.println(memberName.getText().toString()+id);
                if(addMember.getText().equals("Submit")){
                    Intent intent = new Intent(AddMembers.this,HomePage.class);
                    startActivity(intent);
                }
                memberName.setText("");
                AddingMember(id+1);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_members);
        myDb = new DatabaseHelper(this);
        GroupSize = getIntent().getStringExtra("GroupSize");
        Size = Integer.parseInt(GroupSize);
        AddingMember(1);
    }
}
