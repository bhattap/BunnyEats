package com.example.bunnyeats;

import java.util.HashMap;

public class RainyWeatherFood {

    private HashMap<String, String> rainyFoodAndSayingPairs;

    public RainyWeatherFood(){
        rainyFoodAndSayingPairs = new HashMap<>();
        rainyFoodAndSayingPairs.put("ramen",
                "Hey you! Did you know that ramen goes really well with a rainy day? The more you know!");
        rainyFoodAndSayingPairs.put("soup",
                "A piping bowl of soup is best enjoyed on this rainy day.");
        rainyFoodAndSayingPairs.put("donut",
                "Head into one of these places to escape the rain and enjoy a sweet donut.");

    }

    public HashMap<String, String> getRainyFoodAndSayingPairs() {
        return rainyFoodAndSayingPairs;
    }
}
