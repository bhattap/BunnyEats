package com.example.bunnyeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class WeatherResult extends AppCompatActivity {

    private String weatherName;
    private RelativeLayout imageLayout;
    private TextView resultTextView;

    private FusedLocationProviderClient fusedLocationClient;
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_result);

        Intent receivedIntent = getIntent();
        weatherName = "";
        weatherName = receivedIntent.getStringExtra("weatherName");
        imageLayout = findViewById(R.id.imageLayout);
        resultTextView = findViewById(R.id.resultTextView);
        setBackground();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        permissionForLocation();

    }

    private void setBackground(){
        if (weatherName.equals("sunny")){
            imageLayout.setBackgroundResource(R.drawable.sunny);
            resultTextView.setText("The weather looks nice and warm today.");
            resultTextView.setVisibility(View.VISIBLE);
        }
        else if (weatherName.equals("cloudy")){
            imageLayout.setBackgroundResource(R.drawable.cloudy);
            resultTextView.setText("When it's cloudy, a bowl of ramen is the way to go");
            resultTextView.setVisibility(View.VISIBLE);
        }
        else if (weatherName.equals("snowy")){
            imageLayout.setBackgroundResource(R.drawable.snowy);
            resultTextView.setText("It looks like it's freezing outside");
            resultTextView.setVisibility(View.VISIBLE);
        }
        else if (weatherName.equals("rainy")){
            imageLayout.setBackgroundResource(R.drawable.rainy);
            resultTextView.setText("Have a bowl of soup while taking shelter from the rain");
            resultTextView.setVisibility(View.VISIBLE);
        }
        else {
            //this is the default!! our model couldnt detect anything
            //send a simple message about food you can enjoy in all weather
            imageLayout.setBackgroundResource(R.color.colorPrimary);
            resultTextView.setText("We weren't able to classify the weather using this picture, but here are some places serving Thai, which is delicious in all kids of weather");
            resultTextView.setVisibility(View.VISIBLE);
        }
    }

    private void permissionForLocation(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    2000);
        } else{
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2000){
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation(){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            createList(longitude, latitude);
                        }
                    }
                });
    }

    private void createList(double longitude, double latitude){
        Toast.makeText(WeatherResult.this, longitude+", " + latitude, Toast.LENGTH_LONG).show();
    }
}
