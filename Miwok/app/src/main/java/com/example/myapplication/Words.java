package com.example.myapplication;
    class Words{
    private final String mivok;
    private final String english;
    private final int resourceId;
    private final int color;
    private int songId;


    Words(String mivok, String english,int color,int songId){
        this.mivok = mivok;
        this.english = english;
        this.resourceId = -1;
        this.color = color;
        this.songId = songId;
    }
     Words(String mivok, String english,int resourceId,int color,int songId){
        this.mivok = mivok;
        this.english = english;
        this.resourceId = resourceId;
        this.color = color;
        this.songId = songId;
    }
    String getMivok(){
         return mivok;
    }
    String getEnglish(){
        return english;
    }
    int getResourceId(){
        return resourceId;
    }
    int getColor(){return color;}
    int getSongId(){return songId;}



}
