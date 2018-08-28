package com.gamecodeschool.c20scrollingshooter;

interface MovementComponent {

    boolean move(long fps,
                 Transform t,
                 Transform playerTransform);
}
