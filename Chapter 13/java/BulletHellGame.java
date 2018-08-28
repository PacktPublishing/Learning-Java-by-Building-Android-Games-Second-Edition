package com.gamecodeschool.c13bullethell;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Random;

class BulletHellGame extends SurfaceView implements Runnable{

    // Are we currently debugging
    boolean mDebugging = true;

    // Objects for the game loop/thread
    private Thread mGameThread = null;
    private volatile boolean mPlaying;
    private boolean mPaused = true;

    // Objects for drawing
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;

    // Keep track of the frame rate
    private long mFPS;
    // The number of milliseconds in a second
    private final int MILLIS_IN_SECOND = 1000;

    // Holds the resolution of the screen
    private int mScreenX;
    private int mScreenY;

    // How big will the text be?
    private int mFontSize;
    private int mFontMargin;

    // These are for the sound
    private SoundPool mSP;
    private int mBeepID = -1;
    private int mTeleportID = -1;

    // Up to 10000 bullets
    private Bullet[] mBullets = new Bullet[10000];
    private int mNumBullets = 0;
    private int mSpawnRate = 1;

    private Random mRandomX = new Random();
    private Random mRandomY = new Random();

    private Bob mBob;
    private boolean mHit = false;
    private int mNumHits;
    private int mShield = 10;

    // Let's time the game
    private long mStartGameTime;
    private long mBestGameTime;
    private long mTotalGameTime;

    // This is the constructor method that gets called
    // from BullethellActivity
    public BulletHellGame(Context context, int x, int y) {
        super(context);

        mScreenX = x;
        mScreenY = y;
        // Font is 5% of screen width
        mFontSize = mScreenX / 20;
        // Margin is 2% of screen width
        mFontMargin = mScreenX / 50;

        mOurHolder = getHolder();
        mPaint = new Paint();


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


        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("beep.ogg");
            mBeepID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("teleport.ogg");
            mTeleportID = mSP.load(descriptor, 0);

        }catch(IOException e){
            Log.e("error", "failed to load sound files");
        }

        for(int i = 0; i < mBullets.length; i++){
            mBullets[i] = new Bullet(mScreenX);
        }

        mBob = new Bob(context, mScreenX, mScreenY);

        startGame();
    }


    // Called to start a new game
    public void startGame(){
        mNumHits = 0;
        mNumBullets = 0;
        mHit = false;

        // Did the player survive longer than previously
        if(mTotalGameTime > mBestGameTime){
            mBestGameTime = mTotalGameTime;
        }

    }

    // Spawns ANOTHER bullet
    private void spawnBullet(){
        // Add one to the number of bullets
        mNumBullets++;

        // Where to spawn the next bullet
        // And in which direction should it travel
        int spawnX;
        int spawnY;
        int velocityX;
        int velocityY;

        // This code will change in chapter 13
        // Don't spawn to close to Bob
        if (mBob.getRect().centerX() < mScreenX / 2) {
            // Bob is on the left
            // Spawn bullet on the right
            spawnX = mRandomX.nextInt
                    (mScreenX / 2) + mScreenX / 2;
            // Head right
            velocityX = 1;
        } else {
            // Bob is on the right
            // Spawn bullet on the left
            spawnX = mRandomX.nextInt
                    (mScreenX / 2);
            // Head left
            velocityX = -1;
        }

        // Don't spawn to close to Bob
        if (mBob.getRect().centerY() < mScreenY / 2) {
            // Bob is on the top
            // Spawn bullet on the bottom
            spawnY = mRandomY.nextInt
                    (mScreenY / 2) + mScreenY / 2;
            // Head down
            velocityY = 1;
        } else {
            // Bob is on the bottom
            // Spawn bullet on the top
            spawnY = mRandomY.nextInt
                    (mScreenY / 2);
            // head up
            velocityY = -1;
        }

        // Spawn the bullet
        mBullets[mNumBullets - 1]
                .spawn(spawnX, spawnY, velocityX, velocityY);
    }

    // Handles the game loop
    @Override
    public void run() {
        while (mPlaying) {

            long frameStartTime = System.currentTimeMillis();

            if(!mPaused){
                update();
                // Now all the bullets have been moved
                // we can detect any collisions
                detectCollisions();

            }

            draw();

            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame >= 1) {
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }

        }
    }


    // Update all the game objects
    private void update(){
        for(int i = 0; i < mNumBullets; i++){
            mBullets[i].update(mFPS);
        }
    }

    private void detectCollisions(){
        // Has a bullet collided with a wall?
        // Loop through each active bullet in turn
        for(int i = 0; i < mNumBullets; i++) {
            if (mBullets[i].getRect().bottom > mScreenY) {
                mBullets[i].reverseYVelocity();
            }

            if (mBullets[i].getRect().top < 0) {
                mBullets[i].reverseYVelocity();
            }

            if (mBullets[i].getRect().left < 0) {
                mBullets[i].reverseXVelocity();
            }

            if (mBullets[i].getRect().right > mScreenX) {
                mBullets[i].reverseXVelocity();
            }

        }

        // Has a bullet hit Bob?
        // Check each bullet for an intersection with Bob's RectF
        for (int i = 0; i < mNumBullets; i++) {

            if (RectF.intersects(mBullets[i].getRect(), mBob.getRect())) {
                // Bob has been hit
                mSP.play(mBeepID, 1, 1, 0, 0, 1);

                // This flags that a hit occured so that the draw
                // method "knows" as well
                mHit = true;

                // Rebound the bullet that collided
                mBullets[i].reverseXVelocity();
                mBullets[i].reverseYVelocity();

                // keep track of the number of hits
                mNumHits++;

                if(mNumHits == mShield) {
                    mPaused = true;
                    mTotalGameTime = System.currentTimeMillis() - mStartGameTime;
                    startGame();
                }
            }

        }
    }

    private void draw(){
        if (mOurHolder.getSurface().isValid()) {
            mCanvas = mOurHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 243, 111, 36));
            mPaint.setColor(Color.argb(255, 255, 255, 255));

            // All the drawing code will go here
            for(int i = 0; i < mNumBullets; i++){
                mCanvas.drawRect(mBullets[i].getRect(), mPaint);
            }

            mCanvas.drawBitmap(mBob.getBitmap(),
                    mBob.getRect().left, mBob.getRect().top, mPaint);


            mPaint.setTextSize(mFontSize);
            mCanvas.drawText("Bullets: " + mNumBullets +
                            "  Shield: " + (mShield - mNumHits) +
                            "  Best Time: " + mBestGameTime /
                            MILLIS_IN_SECOND,
                    mFontMargin, mFontSize, mPaint);


            // Don't draw the current time when paused
            if(!mPaused) {
                mCanvas.drawText("Seconds Survived: " +
                                ((System.currentTimeMillis() -
                                        mStartGameTime) / MILLIS_IN_SECOND),
                        mFontMargin, mFontMargin * 30, mPaint);
            }


            if(mDebugging) {
                printDebuggingText();
            }

            mOurHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() &
                MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                if(mPaused){
                    mStartGameTime = System.currentTimeMillis();
                    mPaused = false;
                }

                if(mBob.teleport(motionEvent.getX(), motionEvent.getY())){
                    mSP.play(mTeleportID, 1, 1, 0, 0, 1);
                }
                break;

            case MotionEvent.ACTION_UP:

                mBob.setTelePortAvailable();
                spawnBullet();
                break;
        }
        return true;
    }

    public void pause() {
        mPlaying = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    public void resume() {
        mPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    private void printDebuggingText(){
        int debugSize = 35;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);

        mCanvas.drawText("FPS: " + mFPS , 10,
                debugStart + debugSize, mPaint);
        mCanvas.drawText("Bob left: " + mBob.getRect().left , 10,
                debugStart + debugSize *2, mPaint);
        mCanvas.drawText("Bob top: " + mBob.getRect().top , 10,
                debugStart + debugSize *3, mPaint);
        mCanvas.drawText("Bob right: " + mBob.getRect().right , 10,
                debugStart + debugSize *4, mPaint);
        mCanvas.drawText("Bob bottom: " + mBob.getRect().bottom , 10,
                debugStart + debugSize *5, mPaint);
        mCanvas.drawText("Bob centerX: " + mBob.getRect().centerX() , 10,
                debugStart + debugSize *6, mPaint);
        mCanvas.drawText("Bob centerY: " + mBob.getRect().centerY() , 10,
                debugStart + debugSize *7, mPaint);

    }
}
