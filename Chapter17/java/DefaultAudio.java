package com.gamecodeschoolc17.c17snake;
import android.media.SoundPool;

// added the default audio class for strategy implementation
public class DefaultAudio implements Audio {
    private SoundPool soundPool;
    private int eatSoundId;
    private int crashSoundId;

    public DefaultAudio(SoundPool soundPool, int eatSoundId, int crashSoundId) {
        this.soundPool = soundPool;
        this.eatSoundId = eatSoundId;
        this.crashSoundId = crashSoundId;
    }

    @Override
    public void playEatSound() {
        soundPool.play(eatSoundId, 1, 1, 0, 0, 1);
    }

    @Override
    public void playCrashSound() {
        soundPool.play(crashSoundId, 1, 1, 0, 0, 1);
    }
}
