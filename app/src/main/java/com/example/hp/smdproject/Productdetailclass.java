package com.example.hp.smdproject;

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

    Productdetailclass()
    {
        ID=0;
        PID=0;
        Description="";
        Size=0;
        Price=0;
        img=0;
    }
    Productdetailclass(int pid,String D,int S,int P,int id,int im)
    {
        ID=id;
        Description=D;
        PID=pid;
        Size=S;
        Price=P;
        img=im;
    }
    public int getPrice()
    {
        return Price;
    }
    public int getImage()
    {
        return img;
    }
    public void setImg(int a)
    {
        img=a;
    }

    public int getID() {
        return ID;
    }
}
