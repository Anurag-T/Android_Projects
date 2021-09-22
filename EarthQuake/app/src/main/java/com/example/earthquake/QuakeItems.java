package com.example.earthquake;



import org.json.JSONArray;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

public final class QuakeItems {

    private QuakeItems() {

    }
    /********************Create URL Object*******************/
    private static URL createUrl(String urlString){
       URL url = null;
        try {
             url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /******************** Fetch json string from inputstream***************/
    private static String getJsonString(InputStream inputStream)throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
       String line  = bufferedReader.readLine();
        while(line != null){
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }

        return  stringBuilder.toString();
    }

    public static ArrayList<Quake> getQuakeArrayList(String strUrl) throws IOException{
        URL url = createUrl(strUrl);
        String str = "";
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {



            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == 200){
                inputStream = connection.getInputStream();
                str = getJsonString(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }



        ArrayList<Quake> quakeArrayList = new ArrayList<>();
        try {
            JSONObject jsonStr = new JSONObject(str);
            JSONArray jsonArray = jsonStr.getJSONArray("features");
            int i=0;
            while (i<jsonArray.length()){

                JSONObject currentObj = jsonArray.getJSONObject(i);
                JSONObject currentVal = currentObj.getJSONObject("properties");
                String name = currentVal.getString("place");
                String mag = currentVal.getString("mag");
                String date = currentVal.getString("time");
                String murl = currentVal.getString("url");
                Date dateObject = new Date(Long.parseLong(date));

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss a");
                String dateToDisplay = dateFormatter.format(dateObject);


                dateToDisplay = dateToDisplay.substring(0,12) + "\n" + dateToDisplay.substring(13,dateToDisplay.length());
                quakeArrayList.add(new Quake(name,mag,dateToDisplay,murl));
                i++;

            }



        } catch (Exception e) {
            e.printStackTrace();

        }
        return quakeArrayList;
    }

}
