package com.example.githubrepoviewer.database;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RepoDao {
    @Query("Select * from Repos")
    LiveData<List<RepoEntity>> loadAllRepos();

    @Insert
    void insertRepo(RepoEntity repoEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRepo(RepoEntity repoEntity);

    @Delete
    void deleteRepo(RepoEntity sno);

    @Query("Select * from Repos where sno = :id")
    RepoEntity getRepo(int id);

}
