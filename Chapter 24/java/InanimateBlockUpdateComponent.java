package com.gamecodeschool.c24platformer;

class InanimateBlockUpdateComponent implements UpdateComponent {

    private boolean mColliderNotSet = true;

    @Override
    public void update(long fps,
                       Transform t,
                       Transform playerTransform) {

        // An alternative would be to update
        // the collider just once when it spawns.
        // But this would require spawn components
        // - More code but a bit faster
        if(mColliderNotSet) {
            // Only need to set the collider
            // once because it will never move
            t.updateCollider();
            mColliderNotSet = false;
        }
    }
}
