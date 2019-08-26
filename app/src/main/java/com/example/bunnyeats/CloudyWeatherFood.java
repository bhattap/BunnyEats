package com.example.bunnyeats;

import java.util.HashMap;

public class CloudyWeatherFood {

    private HashMap<String, String> cloudyFoodAndSayingPairs;

    public CloudyWeatherFood(){
        cloudyFoodAndSayingPairs = new HashMap<>();
        cloudyFoodAndSayingPairs.put("pasta",
                "Head to one of these places serving your favorite type of pasta on a cloudy day.");
        cloudyFoodAndSayingPairs.put("coffee",
                "Is there a better way to spend a cloudy afternoon than doing some work with great coffee?");
        cloudyFoodAndSayingPairs.put("sushi",
                "Eat fresh seafood on a cloudy day. Head to any one of these sushi places.");
    }

    public HashMap<String, String> getSnowyFoodAndSayingPairs() {
        return cloudyFoodAndSayingPairs;
    }
}
