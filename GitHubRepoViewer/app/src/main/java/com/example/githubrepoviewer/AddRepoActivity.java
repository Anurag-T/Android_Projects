package com.example.githubrepoviewer;


import static com.example.githubrepoviewer.NetworkUtil.TYPE_NOT_CONNECTED;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.githubrepoviewer.database.RepoDatabase;
import com.example.githubrepoviewer.database.RepoEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddRepoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<RepoEntity> {
    private TextView mOwner;
    private TextView mReponame;
    private Button mAdd;
    private String mBaseUrl = "https://api.github.com/repos";

    private RepoDatabase mDb;
    private String owner;
    private String repoName;
    static LoaderManager loaderManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repo);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        }
        mOwner = findViewById(R.id.ed_owner);
        mReponame = findViewById(R.id.ed_repo_name);
        mAdd =findViewById(R.id.button_add_repo);
        mDb = RepoDatabase.getDatabase(getApplicationContext());

        loaderManager = getLoaderManager();




        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                owner = mOwner.getText().toString().trim();
                repoName = mReponame.getText().toString().trim();
                load();

                try {
                    if(NetworkUtil.getConnectivityStatus(getApplicationContext()) == TYPE_NOT_CONNECTED){

                        Toast.makeText(AddRepoActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_SHORT).show();
                        return;
                    }else {

                        loaderManager.getLoader(1).forceLoad();


                    }

                } catch (Exception e) {

                    Toast.makeText(AddRepoActivity.this, "No Repository Found", Toast.LENGTH_SHORT).show();
                }

//
            }
        });



    }
    public void load(){
        loaderManager.initLoader(1, null, this);
    }

    public Loader<RepoEntity> onCreateLoader(int i, Bundle bundle) {

        StringBuilder url = new StringBuilder(mBaseUrl);
        url.append("/").append(owner).append("/").append(repoName);

        Uri baseUri = Uri.parse(url.toString());


        Uri.Builder uriBuilder = baseUri.buildUpon();

        return new AsyncRepo(this, uriBuilder.toString());    }
    @Override
    public void onLoadFinished(Loader<RepoEntity> loader, RepoEntity data) {

        if (data != null)
            mDb.RepoDao().insertRepo(data);
        else{
            Toast.makeText(this, "No Repo Found", Toast.LENGTH_SHORT).show();

        }
        finish();

    }

    @Override
    public void onLoaderReset(Loader<RepoEntity> loader) {

    }


    @SuppressLint("StaticFieldLeak")
    public static class AsyncRepo extends AsyncTaskLoader<RepoEntity> {
        private final String strings;

        public AsyncRepo(Context context, String strings) {

            super(context);
            this.strings = strings;
        }

        @Override
        public RepoEntity loadInBackground() {
            RepoEntity entity = null;
            try {
                entity = RepoItems.getRepo(strings);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return entity;

        }

        @Override
        protected void onStartLoading() {

        }


    }



}