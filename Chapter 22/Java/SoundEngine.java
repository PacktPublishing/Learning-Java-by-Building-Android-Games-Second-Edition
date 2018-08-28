package com.gamecodeschool.c22platformer;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;

class SoundEngine {
    // for playing sound effects
    private static SoundPool mSP;
    private static int mJump_ID = -1;
    private static int mReach_Objective_ID = -1;
    private static  int mCoin_Pickup_ID = -1;
    private static  int mPlayer_Burn_ID = -1;

    private static SoundEngine ourInstance;

    public static SoundEngine getInstance(Context context) {
        ourInstance = new SoundEngine(context);
        return ourInstance;
    }

    public SoundEngine(Context c){
        // Initialize the SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            AssetManager assetManager = c.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("jump.ogg");
            mJump_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("reach_objective.ogg");
            mReach_Objective_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("coin_pickup.ogg");
            mCoin_Pickup_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("player_burn.ogg");
            mPlayer_Burn_ID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }

    }


    public static void playJump(){
        mSP.play(mJump_ID,1, 1, 0, 0, 1);
    }

    public static void playReachObjective(){
        mSP.play(mReach_Objective_ID,1, 1, 0, 0, 1);
    }

    public static void playCoinPickup(){
        mSP.play(mCoin_Pickup_ID,1, 1, 0, 0, 1);
    }

    public static void playPlayerBurn(){
        mSP.play(mPlayer_Burn_ID,1, 1, 0, 0, 1);
    }

}
