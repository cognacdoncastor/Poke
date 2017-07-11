package com.example.cognac.poke;

/**
 * Created by Cognac on 7/11/2017.
 */

public class Item {
    int num;
    String name;
    String imageUrl;
    String effect;
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNum() {
        String []urlParties = url.split("/");
        return Integer.parseInt(urlParties[urlParties.length - 1]);
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Item(String name, String imageUrl,  String url) {
        this.url = url;

        this.name = name;
        this.imageUrl = imageUrl;

    }
}
