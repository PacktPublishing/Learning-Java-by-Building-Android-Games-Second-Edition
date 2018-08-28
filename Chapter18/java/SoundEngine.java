package com.gamecodeschool.c18scrollingshooter;


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
    private SoundPool mSP;
    private int mShoot_ID = -1;
    private int mAlien_Explode_ID = -1;
    private int mPlayer_explode_ID = -1;


    SoundEngine(Context c){
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
            descriptor = assetManager.openFd("shoot.ogg");
            mShoot_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("alien_explosion.ogg");
            mAlien_Explode_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("player_explosion.ogg");
            mPlayer_explode_ID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }

    }

    void playShoot(){
        mSP.play(mShoot_ID,1, 1, 0, 0, 1);
    }

    void playAlienExplode(){
        mSP.play(mAlien_Explode_ID,1, 1, 0, 0, 1);
    }

    void playPlayerExplode(){
        mSP.play(mPlayer_explode_ID,1, 1, 0, 0, 1);
    }
}
