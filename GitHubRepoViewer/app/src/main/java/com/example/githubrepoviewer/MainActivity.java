package com.example.githubrepoviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubrepoviewer.database.RepoDatabase;
import com.example.githubrepoviewer.database.RepoEntity;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.ListItemClickListener {
    private Button mAddepo;
    private List<RepoEntity> entities;

    private TextView mErrorMessage;
    private MainAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LiveData<List<RepoEntity>> mEntities;
    private RepoDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mAddepo = findViewById(R.id.button_add_repo_main);
        mErrorMessage = findViewById(R.id.tv_track_message);
        mAdapter = new MainAdapter(this,this);
        /**************/
        if(entities != null && entities.size() != 0){
            mAddepo.setVisibility(View.GONE);
            mErrorMessage.setVisibility(View.GONE);
        }else{
            mAddepo.setVisibility(View.VISIBLE);
            mErrorMessage.setVisibility(View.VISIBLE);
        }
        /************/
        mRecyclerView = findViewById(R.id.rv_repo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(),layoutManager.getOrientation()));

        mDb = RepoDatabase.getDatabase(getApplicationContext());
        mEntities = mDb.RepoDao().loadAllRepos();
        mEntities.observe(this, new Observer<List<RepoEntity>>() {
            @Override
            public void onChanged(List<RepoEntity> repoEntities) {
                entities = repoEntities;
                if(entities != null && entities.size() != 0){
                    mAddepo.setVisibility(View.GONE);
                    mErrorMessage.setVisibility(View.GONE);
                }else{
                    mAddepo.setVisibility(View.VISIBLE);
                    mErrorMessage.setVisibility(View.VISIBLE);
                }
                mAdapter.setRepos(repoEntities);
            }
        });


        mAddepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,AddRepoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.add_repo_menu){
            Intent intent = new Intent(this,AddRepoActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        RepoEntity entity = entities.get(clickedItemIndex);
        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra("reponame",entity.getRepo_name());
        intent.putExtra("repodesc",entity.getRepo_description());
        intent.putExtra("id",entity.getSno());
        intent.putExtra("url",entity.getUrl());
        intent.putExtra("issue",entity.getIssue());

//        mDb.RepoDao().deleteRepo(entity);
        startActivity(intent);
    }
}