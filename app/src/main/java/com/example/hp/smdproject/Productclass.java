package com.example.hp.smdproject;

/**
 * Created by HP on 21-Apr-17.
 */
public class Productclass {
    int ID;
    String Name;
    int no_of_product;

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
    public Productclass(int id,String D)
    {
        ID=id;
        Name=D;
//        no_of_product=n;
    }
    public String getname()
    {
        return Name;
    }

    public int getID() {
        return ID;
    }
}
