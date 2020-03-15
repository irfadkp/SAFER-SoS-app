package com.example.safer;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String place ="angamaly";
    Address address=new Address(Locale.getDefault());
    double lat;
    double lng;
    LatLng ad;
    LatLng mid;
    double radius;
    double lt;
    double lg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private  LatLng cordinates(Address address) throws IOException {
        Geocoder gc = new Geocoder(this);
        if (gc.isPresent()) {
            List<Address> list = gc.getFromLocationName(place, 1);
            address = list.get(0);
            lat = address.getLatitude();
            lng = address.getLongitude();
            ad = new LatLng(lat,lng);

        }
        return ad;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng adnew= new LatLng(10.1679, 76.3978);
        LatLng adne= new LatLng(10.2270, 76.3749);
        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        /*try {
           // mMap.addMarker(new MarkerOptions().position(cordinates(address)).title("Marker "));
       // } catch (IOException e) {
         //   e.printStackTrace();
        //}
        */try {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cordinates(address)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 14.0f ) );

        mMap.addCircle(new CircleOptions()
                .center(ad)
                .radius(500.0)
                .strokeWidth(3f)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(70,150,50,50)));

        mMap.addCircle(new CircleOptions()
                .center(adnew)
                .radius(500.0)
                .strokeWidth(3f)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(70,150,50,50)));

        mMap.addCircle(new CircleOptions()
                .center(adne)
                .radius(500.0)
                .strokeWidth(3f)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(70,150,50,50)));
    }



}
