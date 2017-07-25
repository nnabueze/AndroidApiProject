package com.example.eze.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EZE on 7/25/2017.
 */

public class HttpUrlManager {
    public static String getData(String uri){
        BufferedReader read = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            read = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = read.readLine()) != null){
                sb.append(line+"\n");
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (read != null){
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
