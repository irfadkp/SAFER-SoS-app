package com.example.safer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1=(ImageButton)findViewById(R.id.sosbutton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpinfo();
            }
        });


        button2=(ImageButton)findViewById(R.id.pinfo);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpinfo1();
            }
        });

        button4=(ImageButton)findViewById(R.id.safetrack);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpinfo4();
            }
        });



        button3=(ImageButton)findViewById(R.id.settings);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,more.class);
                startActivity(intent);
            }
        });

        Intent service= new Intent(MainActivity.this,VoiceService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(service);
        } else{
            startService(service);
        }
    }

    public void openpinfo(){
        Intent intent=new Intent(this,sosactivated.class);
        startActivity(intent);
    }

    public void openpinfo1(){
        Intent intent=new Intent(this,Main2Activity.class);
        startActivity(intent);
    }

    public void openpinfo4(){
        Intent intent=new Intent(this,safetrackoriginal.class);
        startActivity(intent);
    }


}