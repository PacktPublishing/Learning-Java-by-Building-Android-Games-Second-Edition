package com.packtpub.dynamicarrayexample.dynamicarrayexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaring and allocating in one step
        int[] ourArray = new int[1000];

        //Let's initialize ourArray using a for loop
        //Because more than a few variables is allot of typing!
        for(int i = 0; i < 1000; i++){
            //Put the value of ourValue into our array
            //At the position determined by i.
            ourArray[i] = i*5;

            //Output what is going on
            Log.i("info", "i = " + i);
            Log.i("info", "ourArray[i] = " + ourArray[i]);
        }
    }

}
