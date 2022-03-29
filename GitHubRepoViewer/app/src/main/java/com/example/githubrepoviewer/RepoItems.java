package com.example.githubrepoviewer;

import android.util.Log;
import android.widget.Toast;

import com.example.githubrepoviewer.database.RepoEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;

public final class RepoItems {
    private RepoItems(){}

    private static URL createUrl(String urlString){
        URL url = null;
        try {
            
            url = new URL(urlString);
            
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }
        return url;
    }

    private static String getJsonString(InputStream inputStream)throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line  = bufferedReader.readLine();

        while(line != null){
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }

        return  stringBuilder.toString();
    }

/************************************************************************/

    public static RepoEntity getRepo(String strUrl) throws IOException{
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



        RepoEntity newRepo = null;
        try {

            JSONObject jsonStr = new JSONObject(str);

                String repoName = jsonStr.getString("name");
                String repoDesc = jsonStr.getString("description");
                int issue = jsonStr.getInt("open_issues_count");
                if(repoDesc.equals("null")){
                    repoDesc = "No Description";
                }
                StringBuilder builder = new StringBuilder(strUrl);
                builder.replace(8,12,"www.");
                builder.replace(22,28,"");
                newRepo = new RepoEntity(repoName, repoDesc, builder.toString(),issue);




        } catch (Exception e) {
           e.printStackTrace();

        }
        return newRepo;
    }
}
