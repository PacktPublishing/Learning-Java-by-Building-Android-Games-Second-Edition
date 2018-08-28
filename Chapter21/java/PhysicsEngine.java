package com.gamecodeschool.c21scrollingshooter;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;

class PhysicsEngine {
    // This signature and much more will change later in the project
    boolean update(long fps, ArrayList<GameObject> objects,
                   GameState gs, SoundEngine se,
                   ParticleSystem ps){

        // Update all the game objects
        for (GameObject object : objects) {
            if (object.checkActive()) {
                object.update(fps, objects
                        .get(Level.PLAYER_INDEX).getTransform());
            }
        }

        if(ps.mIsRunning){
            ps.update(fps);
        }

        return detectCollisions(gs, objects, se, ps);
    }


   // Collision detection will go here
   private boolean detectCollisions(GameState mGameState, ArrayList<GameObject> objects, SoundEngine se, ParticleSystem ps ){
       boolean playerHit = false;
       for(GameObject go1 : objects) {

           if(go1.checkActive()){
               // The ist object is active so worth checking

               for(GameObject go2 : objects) {

                   if(go2.checkActive()){

                       // The 2nd object is active so worth checking
                       if(RectF.intersects(go1.getTransform().getCollider(), go2.getTransform().getCollider())){
                           // There has been a collision - but does it matter
                           switch (go1.getTag() + " with " + go2.getTag()){
                               case "Player with Alien Laser":
                                   playerHit = true;
                                   mGameState.loseLife(se);

                                   break;

                               case "Player with Alien":
                                   playerHit = true;
                                   mGameState.loseLife(se);

                                   break;

                               case "Player Laser with Alien":
                                   mGameState.increaseScore();
                                   // Respawn the alien
                                   ps.emitParticles(
                                           new PointF(
                                                   go2.getTransform().getLocation().x,
                                                   go2.getTransform().getLocation().y

                                           )
                                   );
                                   go2.setInactive();
                                   go2.spawn(objects.get(Level.PLAYER_INDEX).getTransform());
                                   go1.setInactive();
                                   se.playAlienExplode();

                                   break;

                               default:
                                   break;
                           }

                       }
                   }
               }
           }
       }
       return playerHit;
   }
}
