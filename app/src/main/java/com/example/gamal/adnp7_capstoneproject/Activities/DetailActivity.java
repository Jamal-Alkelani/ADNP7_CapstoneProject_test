package com.example.gamal.adnp7_capstoneproject.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gamal.adnp7_capstoneproject.Models.Appointment;
import com.example.gamal.adnp7_capstoneproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DetailActivity extends AppCompatActivity {
    public static final String ID_APPOINTMENT = "APPOINTMENT";
    private GoogleMap mMap;
    FloatingActionButton callDriver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        if (!intent.hasExtra(ID_APPOINTMENT)) {
            return;
        }
        final Appointment appointment = intent.getParcelableExtra(ID_APPOINTMENT);
        initViews(appointment);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fr_mapView);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    LatLng sydney = new LatLng(-34, 151);
                    mMap.addMarker(new MarkerOptions().position(sydney).title(getString(R.string.marker_text_detail_activity)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                }
            });
        }
        callDriver = findViewById(R.id.callDriver);
        callDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhoneNumber(appointment.getDriverPhoneNumber());
            }
        });



    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void initViews(Appointment appointment) {
        TextView tv_leavingTime = findViewById(R.id.tv_leavingTime);
        tv_leavingTime.setText(appointment.getLeavingTime());
        TextView tv_busLocation = findViewById(R.id.tv_busLocation);
        tv_busLocation.setText(appointment.getLocation());
        TextView tv_wjha = findViewById(R.id.tv_wjha);
        tv_wjha.setText(appointment.getWjha());
        TextView tv_driver = findViewById(R.id.tv_driver);
        tv_driver.setText(appointment.getDriverName());
        TextView tv_driverPhoneNumber = findViewById(R.id.tv_driverPhoneNumber);
        tv_driverPhoneNumber.setText(appointment.getDriverPhoneNumber());
        TextView tv_passengers = findViewById(R.id.tv_passengers);
        tv_passengers.setText(appointment.getPassengers() + "");
    }
}
