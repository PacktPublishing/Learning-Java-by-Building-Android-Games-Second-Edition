package com.gamecodeschool.c20scrollingshooter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

class HUD {
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;

    private ArrayList<Rect> controls;

    static int UP = 0;
    static int DOWN = 1;
    static int FLIP = 2;
    static int SHOOT = 3;
    static int PAUSE = 4;

    HUD(Point size){
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;

        prepareControls();
    }

    private void prepareControls(){
        int buttonWidth = mScreenWidth / 14;
        int buttonHeight = mScreenHeight / 12;
        int buttonPadding = mScreenWidth / 90;

        Rect up = new Rect(
                buttonPadding,
                mScreenHeight - (buttonHeight * 2) - (buttonPadding * 2),
                buttonWidth + buttonPadding,
                mScreenHeight - buttonHeight - (buttonPadding *2) );

        Rect down = new Rect(
                buttonPadding,
                mScreenHeight - buttonHeight - buttonPadding,
                buttonWidth + buttonPadding,
                mScreenHeight - buttonPadding);

        Rect flip = new Rect(mScreenWidth - buttonPadding - buttonWidth,
                mScreenHeight - buttonHeight - buttonPadding,
                mScreenWidth - buttonPadding,
                mScreenHeight - buttonPadding

        );

        Rect shoot = new Rect(mScreenWidth - buttonPadding - buttonWidth,
                mScreenHeight - (buttonHeight * 2) - (buttonPadding * 2),
                mScreenWidth - buttonPadding,
                mScreenHeight - buttonHeight - (buttonPadding *2)
                );

        Rect pause = new Rect(
                mScreenWidth - buttonPadding - buttonWidth,
                buttonPadding,
                mScreenWidth - buttonPadding,
                buttonPadding + buttonHeight);

        controls = new ArrayList<>();
        controls.add(UP,up);
        controls.add(DOWN,down);
        controls.add(FLIP, flip);
        controls.add(SHOOT, shoot);
        controls.add(PAUSE, pause);
    }

    void draw(Canvas c, Paint p, GameState gs){

        // Draw the HUD
        p.setColor(Color.argb(255,255,255,255));
        p.setTextSize(mTextFormatting);
        c.drawText("Hi: " + gs.getHighScore(), mTextFormatting,mTextFormatting,p);
        c.drawText("Scrore: " + gs.getScore(), mTextFormatting,mTextFormatting * 2,p);
        c.drawText("Lives: " + gs.getNumShips(), mTextFormatting,mTextFormatting * 3,p);

        if(gs.getGameOver()){
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PRESS PLAY", mScreenWidth /4, mScreenHeight /2 ,p);
        }

        if(gs.getPaused() && !gs.getGameOver()){
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PAUSED", mScreenWidth /3, mScreenHeight /2 ,p);
        }
        drawControls(c, p);
    }

    private void drawControls(Canvas c, Paint p){
        p.setColor(Color.argb(100,255,255,255));

        for(Rect r : controls){
            c.drawRect(r.left, r.top, r.right, r.bottom, p);
        }

        // Set the colors back
        p.setColor(Color.argb(255,255,255,255));
    }
    ArrayList<Rect> getControls(){
        return controls;
    }
}
