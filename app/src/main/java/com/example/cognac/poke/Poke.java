package com.example.cognac.poke;

/**
 * Created by Cognac on 7/11/2017.
 */

public class Poke {

    private String name;
    private int number;
    private String url;

    public Poke(String name, String url) {
        this.name = name;
        this.url = url;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        String []urlParties = url.split("/");
        return Integer.parseInt(urlParties[urlParties.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
