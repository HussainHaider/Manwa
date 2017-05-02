package com.example.hp.smdproject;

import android.util.Log;

/**
 * Created by shoib on 4/15/2017.
 */

public class User implements java.io.Serializable {
    int ID;
    String Password;
    String Name;
    String Address;
    String Country;
    String Email;
    String image;
    int Creidt_Card;

    User()
    {
        ID=0;
        Name="";
        Address="";
        Country="";
        Email="";
        image="";
    }
    User(int id,String name,String address,String country,String email,String image,int creditCard,String Pass)
    {
        ID=id;
        Name=name;
        Address=address;
        Country=country;
        Email=email;
        this.image=image;
        Creidt_Card=creditCard;
        Password=Pass;
    }
    User(String id,String name,String email)
    {
        int result = Integer.parseInt(id);
        ID=result;
        Log.d("Userid",id);
        Name=name;
        Log.d("Username",name);
        Email=email;
        Log.d("Useremail",email);
    }

    public int getsur()
    {
        for(int i=0;i<Name.length();i++)
        {
            if(Name.charAt(i)==' ')
                return i+1;
        }
        return -1;
    }

}
