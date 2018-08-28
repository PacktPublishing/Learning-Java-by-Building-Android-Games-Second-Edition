package com.gamecodeschool.basicclasses;

import android.util.Log;

/**
 * Created by johnh on 08/12/2017.
 */

public class Soldier {
    int health;
    String soldierType;

    void shootEnemy(){
        //let's print which type of soldier is shooting
        Log.d(soldierType, " is shooting");
    }

}
