package com.gamecodeschool.c25platformer.GOSpec;

import android.graphics.PointF;

public abstract class GameObjectSpec {
    private String mTag;
    private String mBitmapName;
    private float mSpeed;
    private PointF mSize;
    private String[] mComponents;
    private int mFramesAnimation;

    GameObjectSpec(String tag,
                   String bitmapName,
                   float speed,
                   PointF size,
                   String[] components,
                   int framesAnimation ){

        mTag = tag;
        mBitmapName = bitmapName;
        mSpeed = speed;
        mSize = size;
        mComponents = components;
        mFramesAnimation = framesAnimation;
    }

    public int getNumFrames(){
        return mFramesAnimation;
    }

    public String getTag(){
        return mTag;
    }

    public String getBitmapName(){
        return mBitmapName;
    }

    public float getSpeed(){
        return mSpeed;
    }

    public PointF getSize(){
        return mSize;
    }

    public String[] getComponents(){
        return mComponents;
    }
}
