package com.example.hp.smdproject;

/**
 * Created by HP on 23-Feb-17.
 */
public class Produtct_Item {
    private int id;
    private String name;
    private int price;
    //private String currency;
    private int ImageUri;


    //Constructor
    public Produtct_Item()
    {

    }

    public Produtct_Item(int id, String name, int p , int uri)
    {
        this.id = id;
        this.name = name;
        this.price = p;
        //this.currency = description;
        this.ImageUri=uri;
    }

    //Setter, getter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getPrice() {
        return price;
    }

    public void setDescription(int description) {
        this.price = description;
    }

    public int getImage()
    {
        return ImageUri;
    }

}
