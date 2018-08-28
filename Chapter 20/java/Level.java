package com.gamecodeschool.c20scrollingshooter;

import android.content.Context;
import android.graphics.PointF;

import java.util.ArrayList;

class Level {

    // Keep track of specific types
    public static final int BACKGROUND_INDEX = 0;
    public static final int PLAYER_INDEX = 1;
    public static final int FIRST_PLAYER_LASER = 2;
    public static final int LAST_PLAYER_LASER = 4;
    public static int mNextPlayerLaser;
    public static final int FIRST_ALIEN = 5;
    public static final int SECOND_ALIEN = 6;
    public static final int THIRD_ALIEN = 7;
    public static final int FOURTH_ALIEN = 8;
    public static final int FIFTH_ALIEN = 9;
    public static final int SIXTH_ALIEN = 10;
    public static final int LAST_ALIEN = 10;
    public static final int FIRST_ALIEN_LASER = 11;
    public static final int LAST_ALIEN_LASER = 15;
    public static int mNextAlienLaser;

    // This will hold all the instances of GameObject
    private ArrayList<GameObject> objects;

    public Level(Context context,
                 PointF mScreenSize,
                 GameEngine ge){

        objects = new ArrayList<>();
        GameObjectFactory factory = new GameObjectFactory(
                context, mScreenSize, ge);

        buildGameObjects(factory);
    }

    ArrayList<GameObject> buildGameObjects(
            GameObjectFactory factory){

        objects.clear();
        objects.add(BACKGROUND_INDEX, factory.create(new BackgroundSpec()));
        objects.add(PLAYER_INDEX, factory.create(new PlayerSpec()));

        // Spawn the player's lasers
        for (int i = FIRST_PLAYER_LASER; i != LAST_PLAYER_LASER + 1; i++) {
            objects.add(i, factory.create(new PlayerLaserSpec()));
        }

        mNextPlayerLaser = FIRST_PLAYER_LASER;

        // Create some aliens

        // Create some alien lasers

        return objects;
    }

    ArrayList<GameObject> getGameObjects(){
        return objects;
    }


}
