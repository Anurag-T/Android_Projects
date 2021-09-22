package com.example.earthquake;

public class Quake {
    private final String mName;
    private final String mMagvalue;
    private final String mDate;
    private final String mUrl;

    Quake(String name,String mMagValue,String mDate,String mUrl){
        this.mName = name;this.mMagvalue = mMagValue;this.mDate = mDate;this.mUrl = mUrl;
    }

    public String getmName(){
        return mName;
    }
    public String getmMagvalue(){return  mMagvalue;}
    public String getmDate(){return mDate;}
    public String getmUrl(){return mUrl;}
}
