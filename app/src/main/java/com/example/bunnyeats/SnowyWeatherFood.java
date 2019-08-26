package com.example.bunnyeats;

import java.util.HashMap;

public class SnowyWeatherFood {

    private HashMap<String, String> snowyFoodAndSayingPairs;

    public SnowyWeatherFood(){
        snowyFoodAndSayingPairs = new HashMap<>();
        snowyFoodAndSayingPairs.put("szechuan",
                "Spicy food is perfect on a snowy day. Go grab some Szechuan cuisine.");
        snowyFoodAndSayingPairs.put("hot pot",
                "Share some hot pot with a group of friends in the chilly weather.");
        snowyFoodAndSayingPairs.put("pho",
                "Enjoy a warm and delicious bowl of pho by yourself while it snows outside.");
    }

    public HashMap<String, String> getSnowyFoodAndSayingPairs() {
        return snowyFoodAndSayingPairs;
    }
}
