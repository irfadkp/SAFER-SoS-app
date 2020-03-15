package com.example.safer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class more extends AppCompatActivity {

    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        b4=(Button) findViewById(R.id.button2);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpinfo3();
            }
        });

        b5=(Button) findViewById(R.id.setting2);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpinfo4();
            }
        });


        b7=(Button) findViewById(R.id.setting3);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpinfo6();
            }
        });

        b6=(Button) findViewById(R.id.setting99);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpinfo5();
            }
        });

    }

    public void openpinfo3(){
        Intent intent=new Intent(this,database.class);
        startActivity(intent);
    }

    public void openpinfo4(){
        Intent intent=new Intent(this,safetrack.class);
        startActivity(intent);
    }
    public void openpinfo5(){
        Intent intent=new Intent(this,language.class);
        startActivity(intent);
    }


    public void openpinfo6(){
        Intent intent=new Intent(this,trackmylocations.class);
        startActivity(intent);
    }




}
