package com.example.bunnyeats;

public class YelpItem {
    private String name;
    private String rating;
    private String address;

    public YelpItem(String name, String rating, String address){
        this.name = name;
        this.rating = rating;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
