package com.example.githubrepoviewer.database;

import androidx.room.Entity;
import androidx.room.Ignore;

import androidx.room.PrimaryKey;

@Entity(tableName = "Repos")
public class RepoEntity {
    @PrimaryKey(autoGenerate = true)
    private int sno;
    private String repo_name;
    private String repo_description;
    private String url;
    private int issue;

    @Ignore
    public RepoEntity(String repo_name,String repo_description,String url,int issue){
        this.repo_description = repo_description;
        this.repo_name = repo_name;
        this.url = url;
        this.issue = issue;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public int getIssue() {
        return issue;
    }

    public RepoEntity(int sno, String repo_name, String repo_description, String url, int issue){
        this.repo_description = repo_description;
        this.repo_name = repo_name;
        this.sno = sno;
        this.url = url;
        this.issue = issue;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public void setRepo_description(String repo_description) {
        this.repo_description = repo_description;
    }

    public int getSno() {
        return sno;
    }

    public String getRepo_name() {
        return repo_name;
    }

    public String getRepo_description() {
        return repo_description;
    }
}
