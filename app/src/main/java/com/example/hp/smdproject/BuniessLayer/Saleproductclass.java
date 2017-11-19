package com.example.hp.smdproject.BuniessLayer;

import android.graphics.Bitmap;

/**
 * Created by HP on 03-May-17.
 */
public class Saleproductclass {

    int offer_ID;
    String Name;
    String Description;
    int Price;
    int img;
    Bitmap image;

    public Saleproductclass()
    {
        offer_ID=0;
        Description="";
        Name="";
        Price=0;
        img=0;
    }
    public Saleproductclass(int id,String n,int P,String D,Bitmap B)
    {
        offer_ID=id;
        Description=D;
        Name=n;
        Price=P;
        image=B;
    }
    public int getPrice()
    {
        return Price;
    }
    public Bitmap getImage()
    {
        return image;
    }
    public void setImg(int a)
    {
        img=a;
    }
    public String getName()
    {
        return Name;
    }

    public int getID() {
        return offer_ID;
    }
}
