package com.example.miwok;

public class Word {

    private String mdefaultTranslation;

    private String mmiwokTranslation;

    private int mImageResourceId = NO_IMAGE_PROVIDED;   //store reference id for the image

    private static final int NO_IMAGE_PROVIDED = -1;     //no image is present

    private int mAudioResourceID;

    public Word(String defaultTranslation, String miwokTranslation,int audioResourceID){
        mdefaultTranslation = defaultTranslation;
        mmiwokTranslation = miwokTranslation;
        mAudioResourceID = audioResourceID;
    }

    public Word(String defaultTranslation, String miwokTranslation,int imaageResourceId,int audioResourceID){
        mdefaultTranslation = defaultTranslation;
        mmiwokTranslation = miwokTranslation;
        mImageResourceId = imaageResourceId;
        mAudioResourceID = audioResourceID;
    }

    public String getdefaultTranslation(){
        return mdefaultTranslation;
    }

    public String getmiwokTranslation(){
        return mmiwokTranslation;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceID(){return mAudioResourceID;}

}
