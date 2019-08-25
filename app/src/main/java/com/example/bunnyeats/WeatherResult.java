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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherResult extends AppCompatActivity {

    private String weatherName;
    private RelativeLayout imageLayout;
    private TextView resultTextView;

    /*
    The following ArrayLists hold HashMaps of curated food items for each of the weather labels.
    */
    private ArrayList<Map<String, String>> sunnyFoodAndSayingPairs;
    private ArrayList<Map<String, String>> cloudyFoodAndSayingPairs;
    private ArrayList<Map<String, String>> snowyFoodAndSayingPairs;
    private ArrayList<Map<String, String>> rainyFoodAndSayingPairs;

    private int randomIndex;
    private String foodTerm;

    private FusedLocationProviderClient fusedLocationClient;
    private double longitude;
    private double latitude;

    //API Key for Yelp
    private final String apiKey =
            "zg-HdaMM_6ULbi8bL_xSBJLrFPUI7FxgMQpPIL25MS3niImiOYxPwBh-VPvQK2MvYlSZUVsnf1HqCrsQ86C8vblUKjZ2LrI7f0CJ0ISjkUZj4-3Y9v9u21jFvSxaXXYx";

    private ArrayList<Business> businesses;
    private ArrayList<YelpItem> allRestaurants;
    private CustomAdapter customAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_result);

        Intent receivedIntent = getIntent();
        weatherName = "";
        weatherName = receivedIntent.getStringExtra("weatherName");
        imageLayout = findViewById(R.id.imageLayout);
        resultTextView = findViewById(R.id.resultTextView);
        randomIndex = 0;
        foodTerm = "";
        createFoodAndSayingPairs();
        setBackground();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        permissionForLocation();
    }

    private void createFoodAndSayingPairs() {
        sunnyFoodAndSayingPairs = new ArrayList<>();
        cloudyFoodAndSayingPairs = new ArrayList<>();
        snowyFoodAndSayingPairs = new ArrayList<>();
        rainyFoodAndSayingPairs = new ArrayList<>();

        sunnyFoodAndSayingPairs.add(createHashMap("bbq",
                "Enjoy the weather with your friends and some good ol' barbecue!"));
        sunnyFoodAndSayingPairs.add(createHashMap("burger",
                "On a sunny and warm day, pick a place serving delicious burgers."));
        sunnyFoodAndSayingPairs.add(createHashMap("friend chicken",
                "Enjoy the sunny weather at one of these delicious fried chicken places."));
        cloudyFoodAndSayingPairs.add(createHashMap("pasta",
                "Head to one of these places serving your favorite type of pasta on a cloudy day."));
        cloudyFoodAndSayingPairs.add(createHashMap("coffee",
                "Is there a better way to spend a cloudy afternoon than doing some work with great coffee?"));
        cloudyFoodAndSayingPairs.add(createHashMap("sushi",
                "Eat fresh seafood on a cloudy day. Head to any one of these sushi places."));
        snowyFoodAndSayingPairs.add(createHashMap("szechuan",
                "Spicy food is perfect on a snowy day. Go grab some Szechuan cuisine."));
        snowyFoodAndSayingPairs.add(createHashMap("hot pot",
                "Share some hot pot with a group of friends in the chilly weather."));
        snowyFoodAndSayingPairs.add(createHashMap("pho",
                "Enjoy a warm and delicious bowl of pho by yourself while it snows outside."));
        rainyFoodAndSayingPairs.add(createHashMap("ramen",
                "Hey you! Did you know that ramen goes really well with a rainy day? The more you know!"));
        rainyFoodAndSayingPairs.add(createHashMap("soup",
                "A piping bowl of soup is best enjoyed on this rainy day."));
        rainyFoodAndSayingPairs.add(createHashMap("donut",
                "Head into one of these places to escape the rain and enjoy a sweet donut."));

    }

    private HashMap createHashMap(String food, String saying) {
        HashMap<String, String> hashmap = new HashMap<>();
        hashmap.put("food", food);
        hashmap.put("saying", saying);
        return hashmap;
    }

    private void setBackground() {
        //Randomly select a number which will be used to grab one of the
        // special foods in the label our model returned
        Random random = new Random();
        //Get a number from 0 to 2, which will be the index of our chosen saying
        randomIndex = random.nextInt(3);

        if (weatherName.equals("sunny")) {
            imageLayout.setBackgroundResource(R.drawable.sunny);
            //set the displayed text to be the "saying" associated with HashMap located in the ArrayList at the random index
            resultTextView.setText(sunnyFoodAndSayingPairs.get(randomIndex).get("saying"));
            //set the food term (to be searched) to be the "food" associated with HashMap located in the ArrayList at the random index
            foodTerm = sunnyFoodAndSayingPairs.get(randomIndex).get("food");
        } else if (weatherName.equals("cloudy")) {
            imageLayout.setBackgroundResource(R.drawable.cloudy);
            resultTextView.setText(cloudyFoodAndSayingPairs.get(randomIndex).get("saying"));
            foodTerm = cloudyFoodAndSayingPairs.get(randomIndex).get("food");
        } else if (weatherName.equals("snowy")) {
            imageLayout.setBackgroundResource(R.drawable.snowy);
            resultTextView.setText(snowyFoodAndSayingPairs.get(randomIndex).get("saying"));
            foodTerm = snowyFoodAndSayingPairs.get(randomIndex).get("food");
        } else if (weatherName.equals("rainy")) {
            imageLayout.setBackgroundResource(R.drawable.rainy);
            resultTextView.setText(rainyFoodAndSayingPairs.get(randomIndex).get("saying"));
            foodTerm = rainyFoodAndSayingPairs.get(randomIndex).get("food");
        } else {
            //this is the default. our model couldnt detect anything
            //send a simple message about food you can enjoy in all weather
            imageLayout.setBackgroundResource(R.drawable.landing);
            resultTextView.setText("We weren't able to classify the weather using this picture, but here are some places serving Thai, which is delicious in all kids of weather");
            foodTerm = "Thai";
        }
    }

    private void permissionForLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    2000);
        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2000) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            getList(longitude, latitude);
                        }
                    }
                });
    }

    private void getList(double longitude, double latitude) {
        //Use Yelp Fusion API V3

        YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
        YelpFusionApi yelpFusionApi = null;
        try {
            yelpFusionApi = apiFactory.createAPI(apiKey);

        } catch (IOException e) {
            e.printStackTrace();
        }
            Map<String, String> params = new HashMap<>();
            params.put("term", foodTerm);
            params.put("latitude", latitude + "");
            params.put("longitude", longitude + "");

            Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);
            Callback<SearchResponse> callback = new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                    SearchResponse searchResponse = response.body();
                    call.cancel();

                    //Place all the business objects in an ArrayList
                    businesses = searchResponse.getBusinesses();

                    //Create a YelpItem for each business
                    allRestaurants = new ArrayList<>();

                    //i is the counter for the restaurants in the list
                    int i = 0;
                    for (Business business:businesses){
                        i++;
                        //add a new Yelp Item for each restaurant
                        //include name, rating, and address
                        //the business address is calculated this way as you need the "substring" method to remove the "[" and "]" that are a
                            //result of the "toString" method
                        allRestaurants.add(new YelpItem(i+". "+business.getName(),
                                business.getRating()+" ★ (" +
                                        business.getReviewCount() + " reviews)",
                                business.getLocation().getDisplayAddress().toString().substring(1,business.getLocation().getDisplayAddress().toString().length()-1)));
                    }
                    updateListView();
                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    // HTTP error happened, do something to handle it.
                    Toast.makeText(WeatherResult.this,
                            "Something went wrong. Please try again", Toast.LENGTH_LONG).show();
                }
            };

            //asynchronously search so as to not crash the app
            call.enqueue(callback);

    }

    private void updateListView() {
        customAdapter = new CustomAdapter(allRestaurants, WeatherResult.this);
        listView = findViewById(R.id.listView);
        listView.setAdapter(customAdapter);
    }
}
