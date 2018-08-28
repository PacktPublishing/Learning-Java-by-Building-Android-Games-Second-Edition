package com.gamecodeschool.c25platformer;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.concurrent.CopyOnWriteArrayList;

class GameEngine extends SurfaceView
        implements Runnable,
        GameEngineBroadcaster,
        EngineController {

    private Thread mThread = null;
    private long mFPS;

    private GameState mGameState;
    UIController mUIController;

    // This ArrayList can be accessed from either thread
    private CopyOnWriteArrayList<InputObserver>
            inputObservers = new CopyOnWriteArrayList();

    HUD mHUD;
    LevelManager mLevelManager;
    PhysicsEngine mPhysicsEngine;
    Renderer mRenderer;

    public GameEngine(Context context, Point size) {
        super(context);
        // Prepare the bitmap store and sound engine
        BitmapStore bs = BitmapStore.getInstance(context);
        SoundEngine se = SoundEngine.getInstance(context);

        // Initialize all the significant classes
        // that make the engine work
        mHUD = new HUD(context, size);
        mGameState = new GameState(this, context);
        mUIController = new UIController(this, size);
        mPhysicsEngine = new PhysicsEngine();
        mRenderer = new Renderer(this, size);
        mLevelManager = new LevelManager(context,
                this, mRenderer.getPixelsPerMetre());

    }

    public void startNewLevel() {
        // Clear the bitmap store
        BitmapStore.clearStore();
        // Clear all the observers and add the UI observer back
        // When we call buildGameObjects the
        // player's observer will be added too
        inputObservers.clear();
        mUIController.addObserver(this);
        mLevelManager.setCurrentLevel(mGameState.getCurrentLevel());
        mLevelManager.buildGameObjects(mGameState);
    }

    // For the game engine broadcaster interface
    public void addObserver(InputObserver o) {
        inputObservers.add(o);
    }

    @Override
    public void run() {
        while (mGameState.getThreadRunning()) {

            long frameStartTime = System.currentTimeMillis();

            if (!mGameState.getPaused()) {
                mPhysicsEngine.update(mFPS,
                        mLevelManager.getGameObjects(),
                        mGameState);
            }

            mRenderer.draw(mLevelManager.getGameObjects(),
                    mGameState,
                    mHUD);

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
        for (InputObserver o : inputObservers) {
            o.handleInput(motionEvent,
                    mGameState,
                    mHUD.getControls());
        }
        return true;
    }

    public void stopThread() {
        mGameState.stopEverything();
        mGameState.stopThread();
        try {
            mThread.join();
        } catch (InterruptedException e) {
            Log.e("Exception",
                    "stopThread()" + e.getMessage());
        }
    }

    public void startThread() {
        mGameState.startThread();
        mThread = new Thread(this);
        mThread.start();
    }
}
