package com.example.nodemcu_gps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.nodemcu_gps.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Boolean findMaps;
    private ActivityMapsBinding binding;
    TextView txt_lat, txt_lon, txt_time;
    Bundle intentExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        txt_lat = findViewById(R.id.lat);
        txt_lon = findViewById(R.id.lon);
        txt_time = findViewById(R.id.time);
        intentExtras=getIntent().getExtras();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        if (intentExtras != null){
//            String time = intentExtras.getString("time");
            String latitude = intentExtras.getString("latitude");
            String longitude = intentExtras.getString("longitude");
            txt_lat.setText(latitude);
            txt_lon.setText(longitude);
//            txt_time.setText(time);
        }
        String la=txt_lat.getText().toString();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        ProgressDialog progress_map = new ProgressDialog(MapsActivity.this);
        progress_map.setMessage("Cek Lokasi");
        progress_map.setCancelable(true);
        progress_map.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress_map.show();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, new LocationListener() {
            List<Address> addresses = null;

            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    Double output_lat = Double.valueOf(txt_lat.getText().toString());
                    Double output_lon = Double.valueOf(txt_lon.getText().toString());
                    addresses = geocoder.getFromLocation(output_lat, output_lon, 1);
                    if (addresses != null){
                        Address returnAdd = addresses.get(0);
                        StringBuilder stringBuilder = new StringBuilder("");
                        for (int i=0; i<returnAdd.getMaxAddressLineIndex();i++){
                            stringBuilder.append(returnAdd.getAddressLine(i)).append("\n");

                        }
                        Log.w("Lokasi Sekarang", stringBuilder.toString());
                    }else {
                        Log.w("error","no adress");
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                progress_map.show();
                if (findMaps) {
                    progress_map.dismiss();
                    String adreslines=addresses.get(0).getAddressLine(0);
                    Double output_lat = Double.valueOf(txt_lat.getText().toString());
                    Double output_lon = Double.valueOf(txt_lon.getText().toString());
//                    Log.d("outputlat",output_lat+"outputlon"+output_lon);
                    LatLng lokasi_sekarang = new LatLng(output_lat,output_lon);
//                    LatLng lokasi_sekarang = new LatLng(-6.322805068974093, 107.30124662609772);
                    MarkerOptions markerOptions =  new MarkerOptions().position(lokasi_sekarang).title("Motor Anda");
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasi_sekarang));
//                    txt_lat.setText(String.valueOf(output_lat));
//                    txt_lon.setText(String.valueOf(output_lon));
//                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    txt_time.setText("Lokasi Motor : "+adreslines);

                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        findMaps = true;

        // Add a marker in Sydney and move the camera

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}