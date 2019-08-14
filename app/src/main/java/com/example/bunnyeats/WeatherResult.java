package com.example.bunnyeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WeatherResult extends AppCompatActivity {

    private String weatherName;
    private RelativeLayout imageLayout;
    private TextView resultTextView;
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

    private void createList(){

    }
}
