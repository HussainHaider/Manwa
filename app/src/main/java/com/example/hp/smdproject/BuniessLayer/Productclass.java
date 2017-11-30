package com.example.hp.smdproject.BuniessLayer;

import android.graphics.Bitmap;

/**
 * Created by HP on 21-Apr-17.
 */
public class Productclass {
    int ID;
    String Name;
    int no_of_product;
    Bitmap image;

    public Productclass()
    {
        ID=0;
        Name="";
        no_of_product=0;

    }
    public Productclass(int id,String D,int n)
    {
        ID=id;
        Name=D;
        no_of_product=n;
    }
    public Productclass(int id,String D,Bitmap B)
    {
        ID=id;
        Name=D;
        image=B;
//        no_of_product=n;
    }
    public String getname()
    {
        return Name;
    }

    public int getID() {
        return ID;
    }

    public Bitmap getImage()
    {
        return image;
    }
}
