package com.example.githubrepoviewer.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = RepoEntity.class,version = 1,exportSchema = false)
public abstract class RepoDatabase extends RoomDatabase {

    private static RepoDatabase sInstance;
    private static final String DATABASE_NAME = "billsDatabse";
    private static final Object LOCK = new Object();

    public static RepoDatabase getDatabase(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        RepoDatabase.class,RepoDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()

                        // we are a good android citizen and don`t allow main thread queries
                        .build();
            }
        }
        return sInstance;
    }

    public abstract RepoDao RepoDao();
}
