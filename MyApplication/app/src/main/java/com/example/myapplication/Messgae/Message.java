package com.example.myapplication.Messgae;

public class Message {

    private String mod_time;
    private double dv0;
    private double temp;
    private double vbat;

    public Message(String mod_time, double dv0, double temp,double vbat){

       this.mod_time = mod_time;
       this.dv0 = dv0;
       this.temp = temp;
       this.vbat = vbat;

    }

    String getMod_time(){
        return this.mod_time;
    }

    double getDv0(){
        return this.dv0;
    }

    double getTemp(){
        return this.temp;
    }

    double getVbat(){
        return this.vbat;
    }

}
