package com.example.cryptos.activity.about;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.cryptos.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng cryptos = new LatLng(-7.314236392489847, 110.4978821799159);
        mMap.addMarker(new MarkerOptions().position(cryptos).title("Cryptos Inc."));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cryptos, 15f));
    }
}