package com.mygdx.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundService {

    private Sound cookieSound;
    private Music music;
    private Sound buy;


    public SoundService() {
        init();
    }

    private void init() {
        cookieSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Music.mp3"));
        buy = Gdx.audio.newSound(Gdx.files.internal("sounds/click.mp3"));


    }

    public void playcookieSound() {
        cookieSound.play();
    }
    public void playbuySound() {
        buy.play();
    }

    public void startPlayingMusic(boolean looped) {
        music.setVolume(0.1f);
        music.play();
        music.setLooping(looped);
    }
}
