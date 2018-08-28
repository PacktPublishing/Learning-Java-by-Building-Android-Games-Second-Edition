package com.gamecodeschool.exploringmethodoverloading;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Declare and initialize a String and an int
        int anInt = 10;
        String aString = "I am a string";

        // Now call the different versions of printStuff
        // The name stays the same, only the parameters vary
        printStuff(anInt);
        printStuff(aString);
        printStuff(anInt, aString);

    }

    void printStuff(int myInt){
        Log.i("info", "This is the int only version");
        Log.i("info", "myInt = "+ myInt);
    }

    void printStuff(String myString){
        Log.i("info", "This is the String only version");
        Log.i("info", "myString = "+ myString);
    }

    void printStuff(int myInt, String myString){
        Log.i("info", "This is the combined int and String version");
        Log.i("info", "myInt = "+ myInt);
        Log.i("info", "myString = "+ myString);
    }

}
