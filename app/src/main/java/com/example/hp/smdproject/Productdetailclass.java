package com.example.hp.smdproject;

import android.graphics.Bitmap;

/**
 * Created by HP on 21-Apr-17.
 */
public class Productdetailclass {
    int ID;
    int PID;
    String Description;
    int Size;
    int Price;
    int img;
    Bitmap image;

    Productdetailclass()
    {
        ID=0;
        PID=0;
        Description="";
        Size=0;
        Price=0;
        img=0;
    }
    Productdetailclass(int pid,String D,int S,int P,int id,Bitmap B)
    {
        ID=id;
        Description=D;
        PID=pid;
        Size=S;
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

    public int getID() {
        return ID;
    }
}
