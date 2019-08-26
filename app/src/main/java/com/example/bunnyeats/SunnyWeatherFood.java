package com.example.bunnyeats;

import java.util.HashMap;

public class SunnyWeatherFood {

    private HashMap<String, String> sunnyFoodAndSayingPairs;

    public SunnyWeatherFood(){
        sunnyFoodAndSayingPairs = new HashMap<>();
        sunnyFoodAndSayingPairs.put("bbq",
                "Enjoy the weather with your friends and some good ol' barbecue!");
        sunnyFoodAndSayingPairs.put("burger",
                "On a sunny and warm day, pick a place serving delicious burgers.");
        sunnyFoodAndSayingPairs.put("friend chicken",
                "Enjoy the sunny weather at one of these delicious fried chicken places.");
    }

    public HashMap<String, String> getSunnyFoodAndSayingPairs() {
        return sunnyFoodAndSayingPairs;
    }
}
