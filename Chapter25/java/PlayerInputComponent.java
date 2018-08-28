package com.gamecodeschool.c25platformer;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

class PlayerInputComponent implements  InputObserver {

    private Transform mPlayersTransform;
    private PlayerTransform mPlayersPlayerTransform;

    PlayerInputComponent(GameEngine ger) {
        ger.addObserver(this);
    }

    public void setTransform(Transform transform) {
        mPlayersTransform = transform;
        mPlayersPlayerTransform =
                (PlayerTransform) mPlayersTransform;
    }

    // Required method of InputObserver interface
    // called from the onTouchEvent method
    public void handleInput(MotionEvent event,
                            GameState gameState,
                            ArrayList<Rect> buttons) {

        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);

        if(!gameState.getPaused()) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_UP:
                    if (buttons.get(HUD.LEFT).contains(x, y)
                            || buttons.get(HUD.RIGHT)
                            .contains(x, y)) {
                        // Player has released either left or right
                        mPlayersTransform.stopHorizontal();
                    }
                    break;

                case MotionEvent.ACTION_DOWN:
                    if (buttons.get(HUD.LEFT).contains(x, y)) {
                        // Player has pressed left
                        mPlayersTransform.headLeft();
                    } else if (buttons.get(HUD.RIGHT).contains(x, y)) {
                        // Player has pressed right
                        mPlayersTransform.headRight();
                    } else if (buttons.get(HUD.JUMP).contains(x, y)) {
                        // Player has released the jump button
                       mPlayersPlayerTransform.triggerJump();
                    }
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    if (buttons.get(HUD.LEFT).contains(x, y)
                            || buttons.get(HUD.RIGHT).contains(x, y)) {
                        // Player has released either up or down
                        mPlayersTransform.stopHorizontal();
                    }
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    if (buttons.get(HUD.LEFT).contains(x, y)) {
                        // Player has pressed left
                        mPlayersTransform.headLeft();
                    } else if (buttons.get(HUD.RIGHT).contains(x, y)) {
                        // Player has pressed right
                        mPlayersTransform.headRight();
                    } else if (buttons.get(HUD.JUMP).contains(x, y)) {
                        // Player has released the jump button
                        mPlayersPlayerTransform.triggerJump();
                    }
                    break;
            }
        }
    }
}
