package com.example.hp.smdproject.BuniessLayer;

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

    public int getID() {
        return ID;
    }

    public String getPassword() {
        return Password;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getCountry() {
        return Country;
    }

    public String getEmail() {
        return Email;
    }

    public String getImage() {
        return image;
    }

    public int getCreidt_Card() {
        return Creidt_Card;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCreidt_Card(int creidt_Card) {
        Creidt_Card = creidt_Card;
    }



    public User()
    {
        ID=0;
        Name="";
        Address="";
        Country="";
        Email="";
        image="";
    }
    public User(int id,String name,String address,String country,String email,String image,int creditCard,String Pass)
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
    public User(String id,String name,String email)
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
