package com.gamecodeschool.c21scrollingshooter;

interface MovementComponent {

    boolean move(long fps,
                 Transform t,
                 Transform playerTransform);
}
