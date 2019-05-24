package com.example.myapplication.Contact;

public class Contact {

    String name;

    String phone;

    public Contact(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    String getName(){
        return this.name;
    }

    String getPhone(){
        return this.phone;
    }

}
