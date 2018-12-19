package com.example.sumey.webo;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    private TextView textView6, textView7, textView8;
    double targetX, targetY, pointUz, esik;
    private Button button3, btnListele;
    private ListView veriListele;


    double x = 0, y = 0;



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.d("location:",location.toString());
                Log.d(" x : ", location.getLongitude() + "");
                Log.d(" y : ", location.getLatitude() + "");
                x = location.getLongitude();
                y = location.getLatitude();
                targetX = 38.6803759;
                targetY = 27.3126387;
                pointUz = Math.sqrt((x - targetX) * (x - targetX) + (y - targetY) * (y - targetY));
                esik = 0.0011;
                Log.d("uzaklik : ", pointUz + "");
                if (pointUz <= esik) {
                    Log.d("Su an : ", "iş yerindesiniz");
                } else {
                    Log.d("Su an : ", "iş yerinde değilsiniz");
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        // Add a marker in Sydney and move the camera
        LatLng teknokent = new LatLng(38.6803759, 27.3126387);
        mMap.addMarker(new MarkerOptions().position(teknokent).title("Teknokenttesiniz"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(teknokent, 15));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            if (requestCode == 1) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }

    /****************** VERİ TABANI İŞLEMLERİ*********************/
    public void Listele() {
        VeriTabani vt = new VeriTabani(this.getApplicationContext());
        List<String> list = vt.VeriListele();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MapsActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);

        veriListele.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);
        textView8 = (TextView) findViewById(R.id.textView8);
        button3 = (Button) findViewById(R.id.button3);
        btnListele = (Button) findViewById(R.id.btnListele);
        veriListele = (ListView) findViewById(R.id.veriListele);
        Button neredeyimButton = findViewById(R.id.button);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
 //       FragmentManager childFragmentMager = mapFragment.getChildFragmentManager();
   //     childFragmentMager.beginTransaction().apply(ch);

        mapFragment.getMapAsync(this);
        LinearLayout mapLayout = (LinearLayout) findViewById(R.id.mapLayout);
        mapLayout.setVisibility(View.INVISIBLE);
        neredeyimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView6 != null || textView6==null){
                    textView6.setText(String.valueOf(x));
                    textView7.setText(String.valueOf(y));
                }


                if(textView8 != null || textView8==null){
                    if(pointUz <= esik){
                        Log.d("Su an : ","iş yerindesiniz");
                        textView8.setText("İşyerindesiniz");
                    }
                    else{
                        Log.d("Su an : ","iş yerinde değilsiniz");
                        textView8.setText("İşyerinde değilsiniz");
                    }
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String konum1 = textView6.getText().toString();
                String konum2 = textView7.getText().toString();
                String durum = textView8.getText().toString();
                VeriTabani vt = new VeriTabani(MapsActivity.this);
                vt.VeriEkle(konum1, konum2, durum);
            }
        });

        btnListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listele();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String konum1 = textView6.getText().toString();
                String konum2 = textView7.getText().toString();
                String durum = textView8.getText().toString();
                VeriTabani vt = new VeriTabani(MapsActivity.this);
                vt.VeriEkle(konum1, konum2, durum);
                Listele();
            }
        });

        /*****************************************************************/


    }
}