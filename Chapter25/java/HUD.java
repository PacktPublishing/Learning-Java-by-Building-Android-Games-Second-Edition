package com.gamecodeschool.c25platformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

class HUD {
    private Bitmap mMenuBitmap;

    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    final float ONE_THIRD = .33f;
    final float TWO_THIRDS = .66f;

    private ArrayList<Rect> mControls;

    static int LEFT = 0;
    static int RIGHT = 1;
    static int JUMP = 2;

    HUD(Context context, Point size){
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 25;

        prepareControls();

        // Create and scale the bitmaps
        mMenuBitmap = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.menu);

        mMenuBitmap = Bitmap
                .createScaledBitmap(mMenuBitmap,
                        size.x, size.y, false);

    }

    private void prepareControls(){
        int buttonWidth = mScreenWidth / 14;
        int buttonHeight = mScreenHeight / 12;
        int buttonPadding = mScreenWidth / 90;


        Rect left = new Rect(
                buttonPadding,
                mScreenHeight - buttonHeight - buttonPadding,
                buttonWidth + buttonPadding,
                mScreenHeight - buttonPadding);

        Rect right = new Rect(
                (buttonPadding * 2) + buttonWidth,
                mScreenHeight - buttonHeight - buttonPadding,
                (buttonPadding * 2) + (buttonWidth * 2),
                mScreenHeight - buttonPadding);

        Rect jump = new Rect(mScreenWidth - buttonPadding - buttonWidth,
                mScreenHeight - buttonHeight - buttonPadding,
                mScreenWidth - buttonPadding,
                mScreenHeight - buttonPadding);


        mControls = new ArrayList<>();
        mControls.add(LEFT,left);
        mControls.add(RIGHT,right);
        mControls.add(JUMP, jump);
    }

    void draw(Canvas c, Paint p, GameState gs){

        if(gs.getGameOver()){

            // Draw the mMenuBitmap screen
            c.drawBitmap(mMenuBitmap, 0,0, p);

            // draw a rectangle to highlight the text
            p.setColor(Color.argb (100, 26, 128, 182));
            c.drawRect(0,0, mScreenWidth,
                    mTextFormatting * 4, p);

            // Draw the level names
            p.setColor(Color.argb(255, 255, 255, 255));
            p.setTextSize(mTextFormatting);
            c.drawText("Underground",
                    mTextFormatting,
                    mTextFormatting * 2,
                    p);

            c.drawText("Mountains",
                    mScreenWidth * ONE_THIRD + (mTextFormatting),
                    mTextFormatting * 2,
                    p);

            c.drawText("City",
                    mScreenWidth * TWO_THIRDS + (mTextFormatting),
                    mTextFormatting * 2,
                    p);

            // Draw the fastest times
            p.setTextSize(mTextFormatting/1.8f);

            c.drawText("BEST:" + gs.getFastestUnderground()
                    +" seconds",
                    mTextFormatting,
                    mTextFormatting*3,
                    p);

            c.drawText("BEST:" + gs.getFastestMountains()
                    +" seconds", mScreenWidth * ONE_THIRD
                    + mTextFormatting,
                    mTextFormatting * 3,
                    p);

            c.drawText("BEST:" + gs.getFastestCity()
                    + " seconds",
                    mScreenWidth * TWO_THIRDS + mTextFormatting,
                    mTextFormatting * 3,
                    p);

            // draw a rectangle to highlight the large text
            p.setColor(Color.argb (100, 26, 128, 182));
            c.drawRect(0,mScreenHeight - mTextFormatting * 2,
                    mScreenWidth,
                    mScreenHeight,
                    p);

            p.setColor(Color.argb(255, 255, 255, 255));
            p.setTextSize(mTextFormatting * 1.5f);
            c.drawText("DOUBLE TAP A LEVEL TO PLAY",
                    ONE_THIRD + mTextFormatting * 2,
                    mScreenHeight - mTextFormatting/2,
                    p);
        }
        else {
            // draw a rectangle to highlight the text
            p.setColor(Color.argb (100, 0, 0, 0));
            c.drawRect(0,0, mScreenWidth,
                    mTextFormatting,
                    p);

            // Draw the HUD text
            p.setTextSize(mTextFormatting/1.5f);
            p.setColor(Color.argb(255, 255, 255, 255));
            c.drawText("Time:" + gs.getCurrentTime()
                    + "+" + gs.getCoinsRemaining() * 10,
                    mTextFormatting / 4,
                    mTextFormatting / 1.5f,
                    p);


            drawControls(c, p);
        }

    }

    private void drawControls(Canvas c, Paint p){
        p.setColor(Color.argb(100,255,255,255));

        for(Rect r : mControls){
            c.drawRect(r.left, r.top, r.right, r.bottom, p);
        }

        // Set the colors back
        p.setColor(Color.argb(255,255,255,255));
    }


    ArrayList<Rect> getControls(){
        return mControls;
    }
}
