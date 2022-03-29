package com.example.githubrepoviewer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Database;

import com.example.githubrepoviewer.database.RepoDatabase;
import com.example.githubrepoviewer.database.RepoEntity;

public class DetailsActivity extends AppCompatActivity {
    private TextView mDetails;
    private int id;
    private String url;
    private Button branch;
    private Button issues;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
        }
        mDetails = findViewById(R.id.tv_details_repo);
        Intent intent = getIntent();
        mDetails.setText(intent.getStringExtra("reponame") +"\n" +intent.getStringExtra("repodesc") );
        id = intent.getIntExtra("id",0);
        url = intent.getStringExtra("url");
        int issue = intent.getIntExtra("issue",0);
        issues = findViewById(R.id.button_details_issues);
        issues.setText("ISSUES(" + issue+")");
        branch = findViewById(R.id.button_details_branch);
        issues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsActivity.this, "This functionality will be Comming Soon", Toast.LENGTH_SHORT).show();

            }
        });
        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsActivity.this, "This functionality will be Comming Soon", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ide = item.getItemId();
        if(ide == R.id.delete_menu){

            RepoEntity entity = RepoDatabase.getDatabase(getApplicationContext()).RepoDao().getRepo(id);
            RepoDatabase.getDatabase(getApplicationContext()).RepoDao().deleteRepo(entity);
            finish();
            return true;
        }else if(ide == R.id.browse_menu){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
