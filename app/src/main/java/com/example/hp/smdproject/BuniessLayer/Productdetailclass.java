package com.example.hp.smdproject.BuniessLayer;

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

    public Productdetailclass()
    {
        ID=0;
        PID=0;
        Description="";
        Size=0;
        Price=0;
        img=0;
    }
    public Productdetailclass(int pid,String D,int S,int P,int id,Bitmap B)
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
