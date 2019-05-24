package com.example.myapplication.async;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


// AsyncTask<[Input_Parameter Type], [Progress_Report Type], [Result Type]>
public class MessgesTask extends AsyncTask<Void, Void, String> {

    private String serialNumber;
    private String from;
    private String to;
    private String token;

    public MessgesTask(String serialNumber, String from, String to, String token) {
        this.serialNumber = serialNumber;
        this.from = from;
        this.to = to;
        this.token = token;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {

            String string = "http://128.65.16.62:8989/api/applications/3/nodes/" +
                    serialNumber + "/volumes/dates?from=" + from + "&to=" + to +
                    "&allDevices=false";

            System.out.println(string);

            URL url = new URL(string);

            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("Authorization","Bearer "+token);
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
            e.printStackTrace();
            System.out.println("Проверяй http на телефоне!!");
            return null;
        }
    }
}
