package com.gamecodeschool.cscfinalproject;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.SoundPool;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


class SnakeGame extends SurfaceView implements Runnable{

    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;

    // for playing sound effects
    private SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;
    private int mGameOverID = -1;

    //for playing background music
    private MediaPlayer mediaBackground;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    // How many points does the player have
    private int mScore;
    private int mHighScore;
    private int blockSize;

    Random random = new Random();

    // Objects for drawing
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    // A snake ssss
    private Snake mSnake;
    // And an apple
    // Two Apple types
    private NewApple mGoodApple;
    private NewApple mBadApple;

    // adding in a star power-up!
    private Star mStar;

    // adding in obstacles
    private Obstacle mObstacle;

    // Apple Builders
    private GoodAppleBuilder mGoodAppleBuilder;
    private BadAppleBuilder mBadAppleBuilder;

    // Good/Bad Apple bitmaps
    private Bitmap mBitmapGoodApple;
    private Bitmap mBitmapBadApple;

    // adding reference to audio
    private Audio audio;


    // added the audio class for strategy implementation
    public interface Audio {
        void playEatSound();
        void playCrashSound();
    }



    // This is the constructor method that gets called
    // from SnakeActivity
    public SnakeGame(Context context, Point size, Audio audio) {
        super(context);
        mSP = new SoundPool.Builder().build();
        // Work out how many pixels each block is
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        // Initialize the SoundPool
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in mem
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("gameOver.ogg");
            mGameOverID = mSP.load(descriptor, 0);

            this.audio = audio;

        } catch (IOException e) {
            // Error
        }

        // Initialize the drawing objects
        mSurfaceHolder = getHolder();
        mPaint = new Paint();

        // Call the constructors of our two game objects
        // initialize GoodApple bitmap
        this.mBitmapGoodApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
        this.mBitmapGoodApple = Bitmap.createScaledBitmap(mBitmapGoodApple, blockSize, blockSize, false);

        // initialize BadApple bitmap
        this.mBitmapBadApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.badapple);
        this.mBitmapBadApple = Bitmap.createScaledBitmap(mBitmapBadApple, blockSize, blockSize, false);


        this.mGoodAppleBuilder = new GoodAppleBuilder(mBitmapGoodApple, new Point (0, 40) , new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        this.mGoodApple = this.mGoodAppleBuilder.returnApple();

        this.mBadAppleBuilder = new BadAppleBuilder(mBitmapBadApple, new Point (10, 30) , new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        this.mBadApple = this.mBadAppleBuilder.returnApple();

        mStar = new Star(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);
        mStar.spawn();

        mObstacle = new Obstacle(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);
        mObstacle.spawn();

        mSnake = new Snake(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

        //Initializing MediaPlayer
        mediaBackground = MediaPlayer.create(this.getContext(), R.raw.background);

    }


    // Called to start a new game
    public void newGame() {

        // reset the snake
        mSnake.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Get the apple ready for dinner
        if (random.nextInt(2) + 1 > 1) {
            this.mGoodApple.location.x = random.nextInt(this.mGoodApple.mSpawnRange.x - 1) + 1;
            this.mGoodApple.location.y = random.nextInt(this.mGoodApple.mSpawnRange.y - 1) + 1;
            this.mBadApple.location.x = -10;
            this.mBadApple.location.y = 0;
        } else {
            this.mBadApple.location.x = random.nextInt(this.mBadApple.mSpawnRange.x - 1) + 1;
            this.mBadApple.location.y = random.nextInt(this.mBadApple.mSpawnRange.y - 1) + 1;
            this.mGoodApple.location.x = -10;
            this.mGoodApple.location.y = 0;
        }

        // Reset the mScore
        mScore = 0;
        Scanner input = new Scanner("highscore.txt");

        System.out.println(new File("highscore.txt").getAbsolutePath());
        System.out.println("Testing");
        try {

                int test = input.nextInt();
                mHighScore = test;
            input.close();
        }catch(InputMismatchException e){
            //discards
        }
        // Respawn tertiary items
        mStar.spawn();
        mObstacle.spawn();

        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();
    }


    // Handles the game loop
    @Override
    public void run() {
        while (mPlaying) {
            if(!mPaused) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }

            draw();
        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = 10;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }


    // Update all the game objects
    public void update() {

        // Move the snake
        mSnake.move();

        //checking if background music is playing
        if(!mediaBackground.isPlaying()){
            mediaBackground = MediaPlayer.create(this.getContext(), R.raw.background);
            mediaBackground.start();
        }

        // Did the head of the snake eat the apple?
        // Check if GoodApple was eaten
        if(mSnake.checkDinner(this.mGoodApple.location)){
            // After eating an apple, randomly spawn a GoodApple or BadApple
            // If: Spawns GoodApple hides BadApple; Else: vice versa
            if (random.nextInt(2) + 1 > 1) {
                this.mGoodApple.location.x = random.nextInt(this.mGoodApple.mSpawnRange.x - 1) + 1;
                this.mGoodApple.location.y = random.nextInt(this.mGoodApple.mSpawnRange.y - 1) + 1;
                this.mBadApple.location.x = -10;
                this.mBadApple.location.y = 0;
            } else {
                this.mBadApple.location.x = random.nextInt(this.mBadApple.mSpawnRange.x - 1) + 1;
                this.mBadApple.location.y = random.nextInt(this.mBadApple.mSpawnRange.y - 1) + 1;
                this.mGoodApple.location.x = -10;
                this.mGoodApple.location.y = 0;
            }
            // Add to 2 mScore for GoodApple
            mScore = mScore + 2;

            // Play a sound
            mSP.play(mEat_ID, 1, 1, 0, 0, 1);
        }

        // Check if bad apple was eaten
        if(mSnake.checkDinner(this.mBadApple.location)){
            // After eating an apple, randomly spawn a GoodApple or BadApple
            // If: Spawns GoodApple hides BadApple; Else: vice versa
            if (random.nextInt(2) + 1 > 1) {
                this.mGoodApple.location.x = random.nextInt(this.mGoodApple.mSpawnRange.x - 1) + 1;
                this.mGoodApple.location.y = random.nextInt(this.mGoodApple.mSpawnRange.y - 1) + 1;
                this.mBadApple.location.x = -10;
                this.mBadApple.location.y = 0;
            } else {
                this.mBadApple.location.x = random.nextInt(this.mBadApple.mSpawnRange.x - 1) + 1;
                this.mBadApple.location.y = random.nextInt(this.mBadApple.mSpawnRange.y - 1) + 1;
                this.mGoodApple.location.x = -10;
                this.mGoodApple.location.y = 0;
            }
            // Add to 1 mScore for BadApple
            mScore = mScore + 1;

            // Play a sound
            mSP.play(mEat_ID, 1, 1, 0, 0, 1);
        }

        // Did the head of the snake eat the star?
        if (mSnake.checkDinner(mStar.getLocation())) {
            mStar.spawn();  // spawn a new star
            mSP.play(mEat_ID, 1, 1, 0, 0, 1);
            mStar.applySpeedBoost(mSnake);  // speed booster
            mObstacle.spawn();  // spawn new obstacle >:)
        }

        // Did the head of the snake eat the obstacle?
        if (mSnake.checkDinner(mObstacle.getLocation())) {
            mScore = mScore -1;
            mObstacle.spawn();  // spawn new obstacle
        }

        // Did the snake die?
        if (mSnake.detectDeath()) {
            // play the new crash sound using strategy ;)
            if (audio != null) {
                audio.playCrashSound();
            }
            mSP.play(mGameOverID, 1, 1, 0, 0, 1);
            if(mScore > mHighScore){
                mHighScore = mScore;

                try {
                    FileWriter output = new FileWriter("HighScore.txt");
                    output.write(mHighScore);
                } catch (IOException e) {
                    //in the case the save file is not present discards
                }

            }
            mPaused =true;
        }

    }


    // Do all the drawing
    public void draw() {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();

            // Fill the screen with a color
            mCanvas.drawColor(Color.argb(255, 26, 128, 182));

            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(70);
            // Draw the score
            mCanvas.drawText("Score: " + mScore, 20, 100, mPaint);
            mCanvas.drawText("HighScore: " + mHighScore, 20, 200, mPaint);


            // Draw the apple, snake, star, and obstacle
            this.mGoodApple.draw(mCanvas, mPaint);
            this.mBadApple.draw(mCanvas, mPaint);
            mSnake.draw(mCanvas, mPaint);
            mStar.draw(mCanvas, mPaint);
            mObstacle.draw(mCanvas, mPaint);


            // Draw some text while paused
            if(mPaused){

                // Set the size and color of the mPaint for the text
                mPaint.setColor(Color.argb(255, 255, 255, 255));
                mPaint.setTextSize(250);

                // Draw the message
                // We will give this an international upgrade soon
                //mCanvas.drawText("Tap To Play!", 200, 700, mPaint);
                mCanvas.drawText(getResources().
                                getString(R.string.tap_to_play),
                        200, 700, mPaint);
            }


            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (mPaused) {
                    mPaused = false;
                    newGame();

                    // Don't want to process snake direction for this tap
                    return true;
                }

                // Let the Snake class handle the input
                mSnake.switchHeading(motionEvent);
                break;

            default:
                break;

        }
        return true;
    }


    // Stop the thread
    public void pause() {
        mPlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }


    // Start the thread
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }
}
