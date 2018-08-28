package com.gamecodeschool.c21scrollingshooter;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

class GameEngine extends SurfaceView
        implements Runnable,
        GameStarter,
        GameEngineBroadcaster,
        PlayerLaserSpawner,
        AlienLaserSpawner {

    private Thread mThread = null;
    private long mFPS;

    private ArrayList<InputObserver> inputObservers = new ArrayList();
    UIController mUIController;

    private GameState mGameState;
    private SoundEngine mSoundEngine;
    HUD mHUD;
    Renderer mRenderer;
    ParticleSystem mParticleSystem;
    PhysicsEngine mPhysicsEngine;
    Level mLevel;


    public GameEngine(Context context, Point size) {
        super(context);

        mUIController = new UIController(this);
        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);
        mHUD = new HUD(size);
        mRenderer = new Renderer(this);
        mPhysicsEngine = new PhysicsEngine();

        mParticleSystem = new ParticleSystem();
        mParticleSystem.init(1000);

        mLevel = new Level(context,
                new PointF(size.x, size.y), this);
    }

    // For the game engine broadcaster interface
    public void addObserver(InputObserver o) {

        inputObservers.add(o);
    }

    public void deSpawnReSpawn() {
        // Eventually this will despawn
        // and then respawn all the game objects
        ArrayList<GameObject> objects = mLevel.getGameObjects();

        for(GameObject o : objects){
            o.setInactive();
        }
        objects.get(Level.PLAYER_INDEX).spawn(objects.get(Level.PLAYER_INDEX).getTransform());
        objects.get(Level.BACKGROUND_INDEX).spawn(objects.get(Level.PLAYER_INDEX).getTransform());

        for (int i = Level.FIRST_ALIEN; i != Level.LAST_ALIEN + 1; i++) {
            objects.get(i).spawn(objects.get(Level.PLAYER_INDEX).getTransform());
        }

    }

    @Override
    public void run() {
        while (mGameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();
            ArrayList<GameObject> objects = mLevel.getGameObjects();

            if (!mGameState.getPaused()) {
                // Update all the game objects here
                // in a new way

                // This call to update will eveolve with the project
                if(mPhysicsEngine.update(mFPS,objects, mGameState, mSoundEngine, mParticleSystem)){
                    // Player hit
                    deSpawnReSpawn();
                }
            }

            // Draw all the game objects here
            // in a new way
            mRenderer.draw(objects, mGameState, mHUD, mParticleSystem);

            // Measure the frames per second in the usual way
            long timeThisFrame = System.currentTimeMillis()
                    - frameStartTime;
            if (timeThisFrame >= 1) {
                final int MILLIS_IN_SECOND = 1000;
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // Handle the player's input here
        // But in a new way
        for (InputObserver o : inputObservers) {
            o.handleInput(motionEvent, mGameState, mHUD.getControls());
        }

        

        return true;
    }

    public void stopThread() {
        // New code here soon
        mGameState.stopEverything();

        try {
            mThread.join();
        } catch (InterruptedException e) {
            Log.e("Exception","stopThread()" + e.getMessage());
        }
    }

    public void startThread() {
        // New code here soon
        mGameState.startThread();

        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public boolean spawnPlayerLaser(Transform transform) {
        ArrayList<GameObject> objects = mLevel.getGameObjects();
        if (objects.get(Level.mNextPlayerLaser).spawn(transform)) {
            Level.mNextPlayerLaser++;
            mSoundEngine.playShoot();
            if (Level.mNextPlayerLaser == Level.LAST_PLAYER_LASER + 1) {
                // Just used the last laser
                Level.mNextPlayerLaser = Level.FIRST_PLAYER_LASER;

            }
        }
        return true;
    }

    public void spawnAlienLaser(Transform transform) {
        ArrayList<GameObject> objects = mLevel.getGameObjects();
        // Shoot laser IF AVAILABLE
        // Pass in the transform of the ship
        // that requested the shot to be fired
        if (objects.get(Level.mNextAlienLaser).spawn(transform)) {
            Level.mNextAlienLaser++;
            mSoundEngine.playShoot();
            if (Level.mNextAlienLaser == Level.LAST_ALIEN_LASER + 1) {
                // Just used the last laser
                Level.mNextAlienLaser = Level.FIRST_ALIEN_LASER;
            }
        }
    }
}
