package com.example.safer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class database extends AppCompatActivity {
    EditText textViewlocation;
    TextView returnloc;
    Button trigger;
    DatabaseReference dbRef;
    String Val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        textViewlocation = (EditText)findViewById((R.id.editText));
        trigger = (Button) findViewById((R.id.button4));
        returnloc = findViewById(R.id.textView2);
        dbRef = FirebaseDatabase.getInstance().getReference("location");

        trigger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                String loc = textViewlocation.getText().toString().trim();
                update_trigger_value(loc);
            }

            private void update_trigger_value(final String loc) {

                dbRef.child(loc).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value_ = dataSnapshot.getValue().toString();
                        int increment = Integer.parseInt(value_);
                        increment+=0;
                        value_=String.valueOf(increment);
                        Toast.makeText(database.this,"The number of crimes reported here is //"+ value_, Toast.LENGTH_LONG).show();
                        dbRef.child(loc).setValue(increment);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(database.this, "Database", Toast.LENGTH_SHORT).show();

                    }
                });
            }


        });



    }


}


