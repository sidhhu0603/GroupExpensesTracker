package com.example.groupexpensestracker;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GroupCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_creation);

        Button sizeSelectionBtn = findViewById(R.id.sizeSelectionBtn);
        sizeSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView grps = findViewById(R.id.groupSize);
                TextView warningText = findViewById(R.id.warningText);
                    Integer GrpSize = Integer.parseInt(grps.getText().toString());
                    if(GrpSize<=1){
                        warningText.setText("Please enter a valid group size");
                    }else{
                        warningText.setText("");
                        Intent i = new Intent(GroupCreation.this,AddMembers.class);
                        i.putExtra("GroupSize",GrpSize.toString());
                        startActivity(i);
                    }


            }
        });
    }
}
