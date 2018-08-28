package com.gamecodeschool.c20scrollingshooter;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

class UIController implements InputObserver {

    public UIController(GameEngineBroadcaster b){
        b.addObserver(this);
    }

    @Override
    public void handleInput(MotionEvent event, GameState gameState, ArrayList<Rect> buttons) {

        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);

        int eventType = event.getAction() & MotionEvent.ACTION_MASK;

        if(eventType == MotionEvent.ACTION_UP ||
                eventType == MotionEvent.ACTION_POINTER_UP) {


            if (buttons.get(HUD.PAUSE).contains(x, y)){
                // Player pressed the pause button
                // Respond differently depending upon the game's state

                // If the game is not paused
                if (!gameState.getPaused()) {
                    // Pause the game
                    gameState.pause();
                }

                // If game is over start a new game
                else if (gameState.getGameOver()) {

                    gameState.startNewGame();
                }

                // Paused and not game over
                else if (gameState.getPaused()
                        && !gameState.getGameOver()) {

                    gameState.resume();
                }
            }


        }

    }

}
