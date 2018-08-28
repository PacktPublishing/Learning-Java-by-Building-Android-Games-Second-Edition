// This is our package
// If you are copy & pasting the code then this line will probably be different to yours
// If so, keep YOUR line- not this one
package com.gamecodeschool.c3subhunter;

// These are all the classes of other people's
// (Android) code that we use in Sub Hunt
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.widget.ImageView;
import java.util.Random;


public class SubHunter extends Activity {

    // These variables can be "seen"
    // throughout the SubHunter class
    int numberHorizontalPixels;
    int numberVerticalPixels;
    int blockSize;
    int gridWidth = 40;
    int gridHeight;
    float horizontalTouched = -100;
    float verticalTouched = -100;
    int subHorizontalPosition;
    int subVerticalPosition;
    boolean hit = false;
    int shotsTaken;
    int distanceFromSub;
    boolean debugging = true;

    /*
        Android runs this code just before
        the app is seen by the player.
        This makes it a good place to add
        the code that is needed for
        the one-time setup.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the current device's screen resolution
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Initialize our size based variables based on the screen resolution
        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
        blockSize = numberHorizontalPixels / gridWidth;
        gridHeight = numberVerticalPixels / blockSize;

        Log.d("Debugging", "In onCreate");
        newGame();
        draw();
    }

    /*
        This code will execute when a new
        game needs to be started. It will
        happen when the app is first started
        and after the player wins a game.
     */
    void newGame(){
        Log.d("Debugging", "In newGame");

    }

    /*
        Here we will do all the drawing.
        The grid lines, the HUD and
        the touch indicator
     */
    void draw() {
        Log.d("Debugging", "In draw");
        printDebuggingText();
    }

    /*
        This part of the code will
        handle detecting that the player
        has tapped the screen
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.d("Debugging", "In onTouchEvent");
        takeShot();

        return true;
    }


    /*
        The code here will execute when
        the player taps the screen It will
        calculate distance from the sub'
        and determine a hit or miss
     */
    void takeShot(){
        Log.d("Debugging", "In takeShot");
        draw();
    }

    // This code says "BOOM!"
    void boom(){

    }

    // This code prints the debugging text
    public void printDebuggingText(){
        Log.d("numberHorizontalPixels",
                "" + numberHorizontalPixels);
        Log.d("numberVerticalPixels",
                "" + numberVerticalPixels);

        Log.d("blockSize", "" + blockSize);
        Log.d("gridWidth", "" + gridWidth);
        Log.d("gridHeight", "" + gridHeight);

        //Log.d("horizontalTouched",
                //"" + horizontalTouched);
        Log.d("verticalTouched",
                "" + verticalTouched);
        Log.d("subHorizontalPosition",
                "" + subHorizontalPosition);
        Log.d("subVerticalPosition",
                "" + subVerticalPosition);

        Log.d("hit", "" + hit);
        Log.d("shotsTaken", "" + shotsTaken);
        Log.d("debugging", "" + debugging);

        Log.d("distanceFromSub",
                "" + distanceFromSub);
    }
}