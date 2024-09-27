package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.screens.SplashScreen;
import com.mygdx.service.ScoreService;
import com.mygdx.service.SoundService;

public class MyGdxGame extends Game {

    public final static String GAME_NAME = "Clicker";
    public final static int WIDTH = 480;
    public final static int HEIGHT = 700;
    private boolean paused;
    private SoundService soundService;
    private ScoreService scoreService;

    @Override
    public void create() {
        init();
        this.setScreen(new SplashScreen(this));
    }

    public void init() {
        initSoundService();
        initScoreService();
    }

    private void initScoreService() {
        scoreService = new ScoreService();
    }

    private void initSoundService() {
        soundService = new SoundService();
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public SoundService getSoundService() {
        return soundService;
    }

    public ScoreService getScoreService() {
        return scoreService;
    }
}