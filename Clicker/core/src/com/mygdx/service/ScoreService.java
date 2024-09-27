package com.mygdx.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;

public class ScoreService {

    public final String GAME_PREFS = "com.mygdx.game.prefs";
    public final String GAME_SCORE = "com.mygdx.game.score";
    public final String GAME_PASSIVE_INCOME = "com.mygdx.game.passiveincome";
    public final String GAME_SAVED_TIMESTAMP = "com.mygdx.game.savedtumestamp";
    private Preferences prefs; // saving our scores
    private int points;
    private int passiveIncome;

    public ScoreService() {
        init();
    }

    private void init() {
        prefs = Gdx.app.getPreferences(GAME_PREFS);
        loadScores();
        loadPassiveIncome();
    }

    public void addPoints(int pointsToAdd) {
        points += pointsToAdd;
    }

    public void loadScores() {
        points = prefs.getInteger(GAME_SCORE);
    }

    private void loadPassiveIncome() { // loading passive points
        passiveIncome = prefs.getInteger(GAME_PASSIVE_INCOME);
    }

    public void addPoint() {
        points++;
    }

    public int getPassiveIncome() {
        return passiveIncome;
    }

    public void addPassiveIncome() {
        passiveIncome++;
    }

    public void resetGameScore() {
        points = 0;
        passiveIncome = 0;
    }

    public long getSavedTimestamp() {
        return prefs.getLong(GAME_SAVED_TIMESTAMP);
    }

    public void saveCurrentTimestamp() {
        prefs.putLong(GAME_SAVED_TIMESTAMP, TimeUtils.millis());
        prefs.flush(); // flush is saving
    }

    public void saveCurrentGamestate() {
        prefs.putLong(GAME_SAVED_TIMESTAMP, TimeUtils.millis());
        prefs.putInteger(GAME_SCORE, points); // saving
        prefs.putInteger(GAME_PASSIVE_INCOME, passiveIncome);
        prefs.flush();
    }

    public int getPoints() {
        return points;
    }
}
