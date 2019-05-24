package com.example.myapplication.async;

import android.os.AsyncTask;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


// AsyncTask<[Input_Parameter Type], [Progress_Report Type], [Result Type]>
public class LoginTask extends AsyncTask<Void, Void, String> {

    private String login;
    private String password;

    public LoginTask(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("http://128.65.16.62:8989/api/login");
            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("Authorization","Basic "+new String(Base64.encode((login + ":" + password).getBytes(),0)));
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            String newLine = System.getProperty("line.separator");
            while ((inputLine = in.readLine()) != null) {
                if (this.isCancelled()) {
                    return null;
                }
                response.append(inputLine + newLine);
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            System.out.println("Проверяй http на телефоне!!");
            return null;
        }
    }
}
