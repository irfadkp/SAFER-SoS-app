package com.example.safer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class sosactivated extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    private Button button;
    private ImageView b1;
    double latitude=0;
    double longitude=0;
    String msg,lat,lng,city,road,place;


    int count=0;

    FusedLocationProviderClient client;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sosactivated);

        client = LocationServices.getFusedLocationProviderClient(sosactivated.this);

        /*if(checkPermission(Manifest.permission.SEND_SMS)) {
        } else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }*/

        button = findViewById(R.id.button3);
        b1 = findViewById(R.id.imageView5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count=1;
                startActivity(new Intent(sosactivated.this,MainActivity.class));

            }
        });


        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                if(count==0) {

                    button.setVisibility(View.GONE);
                    coordinates();

                }
            }
        }.start();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

    }

    public boolean isOnline() {

        final ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = cm.getActiveNetworkInfo();
                if (ni != null) {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                final Network n = cm.getActiveNetwork();
                if (n != null) {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);
                    if (nc != null) {
                        return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                    }
                }
            }
        }
        return false;
    }

    public void mail(final String string1, final String string2){
        new Thread() {
            public void run() {
                try {
                    Sender sender = new Sender("teampanacea000@gmail.com","panacea@123" );
                    sender.sendMail("Feedback : SOS location",  string1 , "teampanacea000@gmail.com",string2);
                } catch (Exception e) {
                }
            }
        }.start();

    }

    private void loc_func (){
        Toast.makeText(this, "phone=", Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(this);

            List<Address> addresses = null;
            //double latitude = 0;
            //double longitude=0;
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            //String country = addresses.get(0).getCountryName();
            /*String state = addresses.get(0).getAdminArea();*/
            city = addresses.get(0).getSubAdminArea();
            /* String pin = addresses.get(0).getPostalCode();*/
            road= addresses.get(0).getFeatureName();
            place = addresses.get(0).getAddressLine(0);
            //  tv.setText("country"+country);
            // tv.setText("address:" +place);
            //ta.setText("address"+city);
            //return city;
            //Toast.makeText(this,"Geocode working",Toast.LENGTH_SHORT).show();
            match("Angamaly");

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void coordinates() {

        if (ActivityCompat.checkSelfPermission(sosactivated.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        } else {
            client.getLastLocation().addOnSuccessListener(sosactivated.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        lat = String.valueOf(latitude);
                        lng = String.valueOf(longitude);
                        Toast.makeText(sosactivated.this, "coordinates working", Toast.LENGTH_SHORT).show();

                        if (isOnline()) {
                            loc_func();
                        }
                        else {
                            msg = "https://www.google.com/maps?q=" + lat + "," + lng;
                            sms("6282514141",msg);
                        }
                    }
                }
            });
        }

    }

    public void match(String address) {

        Toast.makeText(this, "phone=", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = getSharedPreferences("com.example.sosactivated" , MODE_PRIVATE).edit();
        editor.putString("patrol","Kumbala:9946500101,Hosdurg:9946500102,Thaliparamb:9946500103");
        editor.putString("station","Angamaly PS:6282514141:shoagmlekmrl.pol@kerala.gov.in,Nedumbassery PS:9497980518:shonsyekmrl.pol@kerala.gov.in,Kalady PS:9497980468:shokldyekmrl.pol@kerala.gov.in");
        editor.commit();
        String data = getSharedPreferences("com.example.sosactivated" ,MODE_PRIVATE).getString("station","Not found");

        String phone = null;
        //String[] ad=address.split(",");
        //Toast.makeText(this,ad[0]+"     "+ad[1]+"      "+ad[2],Toast.LENGTH_SHORT).show();

        if(data.contains(address)) {
            String[] a = data.split(",");

            for(int i=0;i!=a.length;i++) {

                if(a[i].contains(address) ){
                    String[] b = a[i].split(":");
                    phone=b[1];
                    String mail=b[2];
                    Toast.makeText(this, "phone="+phone+"email"+mail, Toast.LENGTH_SHORT).show();
                    String m= "https://www.google.com/maps?q=" + lat + "," + lng + "\n" + city + road + place;
                    sms(phone,m);
                    mail(m,"agnespaul255@gmail.com");
                    Toast.makeText(this,"Match working"+phone,Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void sms(String ph,String smsMessage) {
        String phoneNumber = ph;
        //String smsMessage = "ALERT SIGNAL"+"\n---EMERGENCY---\n";

        if(checkPermission(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,smsMessage,null,null);
            Toast.makeText(this, "Message sent", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
        }
    }

    public void call() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:8281483870"));

        if (ActivityCompat.checkSelfPermission(sosactivated.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this,permission);
        return(check == PackageManager.PERMISSION_GRANTED);
    }



}
