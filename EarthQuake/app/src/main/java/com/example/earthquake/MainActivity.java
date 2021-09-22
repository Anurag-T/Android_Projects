package com.example.earthquake;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.loader.app.LoaderManager;
//import androidx.loader.content.Loader;
import android.app.LoaderManager;
import android.content.Loader;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Quake>> {
    private static final String URL_USGS = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    static ArrayList<Quake> quakelist = new ArrayList<Quake>();
    CustomAdapter customAdapter;
    private TextView EmptyText;
    private ProgressBar progressBar;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = (ProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

       customAdapter = new CustomAdapter(this,quakelist);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(customAdapter);
        EmptyText = (TextView)findViewById(R.id.empty_view);
        listView.setEmptyView(EmptyText);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected())

         {
                LoaderManager loaderManager = getLoaderManager();


                loaderManager.initLoader(1, null, this).forceLoad();

            }else{
            progressBar.setVisibility(View.GONE);
            EmptyText.setText("No Internet Connection");

        }




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quake current = (Quake)listView.getItemAtPosition(position);
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getmUrl()));
                if (browser.resolveActivity(getPackageManager()) != null) {
                    startActivity(browser);
                }
            }
        });
    }

    @Override
    public Loader<List<Quake>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(URL_USGS);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new AsyncEarthQuake(this, uriBuilder.toString());    }
    @Override
    public void onLoadFinished(Loader<List<Quake>> loader, List<Quake> data) {
        customAdapter.clear();
        progressBar.setVisibility(View.GONE);
        if (quakelist != null && !quakelist.isEmpty())
            customAdapter.addAll(quakelist);
        else{
            EmptyText.setText("No Data Found");
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Quake>> loader) {
        customAdapter.clear();
    }


    @SuppressLint("StaticFieldLeak")
    public static class AsyncEarthQuake extends AsyncTaskLoader<List<Quake>>{
        private final String strings;

        public AsyncEarthQuake(Context context,String strings) {

            super(context);
            this.strings = strings;
        }

        @Override
        public ArrayList<Quake> loadInBackground() {
            try {
                quakelist = QuakeItems.getQuakeArrayList(strings);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return quakelist;

        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }


    }

}