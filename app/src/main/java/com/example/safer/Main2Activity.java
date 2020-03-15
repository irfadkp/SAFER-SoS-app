package com.example.safer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    EditText st_name;
    EditText st_address;
    EditText st_email;
    EditText st_pin;
    EditText st_phone;
    AutoCompleteTextView st_district;
    AutoCompleteTextView st_state;
    AutoCompleteTextView st_country;
    EditText st_emph1;
    EditText st_emph2;
    EditText st_emph3;


    private Button savebtn;

    SharedPreferences sharedPreferences;
    static final String mypreference = "mypref";
    static final String Name = "nameKey";
    static final String Address = "addressKey";
    static final String Email = "emailKey";
    static final String Pin = "pinKey";
    static final String Phone = "phoneKey";
    static final String District = "districtKey";
    static final String State = "stateKey";
    static final String Country = "countryKey";
    static final String Phone1 = "phone1Key";
    static final String Phone2 = "phone2Key";
    static final String Phone3 = "phone3Key";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        savebtn = (Button) findViewById(R.id.save);

        st_name = (EditText) findViewById(R.id.name);
        st_address = (EditText) findViewById(R.id.address);
        st_email = (EditText) findViewById(R.id.email);
        st_pin = (EditText) findViewById(R.id.pincode);
        st_phone = (EditText) findViewById(R.id.phone);
        st_district = (AutoCompleteTextView) findViewById(R.id.district);
        st_state = (AutoCompleteTextView) findViewById(R.id.state);
        st_country = (AutoCompleteTextView) findViewById(R.id.country);
        st_emph1 = (EditText) findViewById(R.id.emph1);
        st_emph2 = (EditText) findViewById(R.id.emph2);
        st_emph3 = (EditText) findViewById(R.id.emph3);



        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }

        });
        retrieve();


    }

    public void save() {

        String n = st_name.getText().toString();
        String a = st_address.getText().toString();
        String e = st_email.getText().toString();
        String p = st_pin.getText().toString();
        String ph = st_phone.getText().toString();
        String d = st_district.getText().toString();
        String s = st_state.getText().toString();
        String c = st_country.getText().toString();
        String ea = st_emph1.getText().toString();
        String eb = st_emph2.getText().toString();
        String ec = st_emph3.getText().toString();


        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Name, n);
        editor.putString(Address, a);
        editor.putString(Email, e);
        editor.putString(Pin, p);
        editor.putString(Phone, ph);
        editor.putString(District, d);
        editor.putString(State, s);
        editor.putString(Country, c);
        editor.putString(Phone1, ea);
        editor.putString(Phone2, eb);
        editor.putString(Phone3, ec);

        editor.commit();


    }

    public void retrieve() {

        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Name)) {


            st_name.setText(sharedPreferences.getString(Name, ""));
        }


        if (sharedPreferences.contains(Address)) {

            st_address.setText(sharedPreferences.getString(Address, ""));
        }


        if (sharedPreferences.contains(Email)) {

            st_email.setText(sharedPreferences.getString(Email, ""));

        }

        if (sharedPreferences.contains(Pin)) {

            st_pin.setText(sharedPreferences.getString(Pin, ""));

        }

        if (sharedPreferences.contains(Phone)) {

            st_phone.setText(sharedPreferences.getString(Phone, ""));

        }

        if (sharedPreferences.contains(District)) {

            st_district.setText(sharedPreferences.getString(District, ""));

        }

        if (sharedPreferences.contains(State)) {

            st_state.setText(sharedPreferences.getString(State, ""));

        }

        if (sharedPreferences.contains(Country)) {

            st_country.setText(sharedPreferences.getString(Country, ""));

        }

        if (sharedPreferences.contains(Phone1)) {

            st_emph1.setText(sharedPreferences.getString(Phone1, ""));

        }

        if (sharedPreferences.contains(Phone2)) {

            st_emph2.setText(sharedPreferences.getString(Phone2, ""));

        }

        if(sharedPreferences.contains(Phone3)) {

            st_emph3.setText(sharedPreferences.getString(Phone3, ""));

        }



    }
}

