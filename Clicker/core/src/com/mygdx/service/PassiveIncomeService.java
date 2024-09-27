package com.mygdx.service;


import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.concurrent.TimeUnit;

public class PassiveIncomeService {

    private ScoreService scoreService;
    private int pointsToAdd;





    private final static int INFINITE = 10000000;

    public PassiveIncomeService(ScoreService scoreService) {
        this.scoreService = scoreService;
        calculateGainedPassiveIncome();
    }

    public void start() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                scoreService.addPoints(scoreService.getPassiveIncome());
            }
        }, 1, 1, INFINITE);
    }

    private void calculateGainedPassiveIncome() {
        long savedTimestamp = scoreService.getSavedTimestamp();
        if (savedTimestamp > 0) {
            long millisPassed = TimeUtils.timeSinceMillis(savedTimestamp);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(millisPassed);
            addPointsBasedOnPassedTime(seconds);
        } else {
            // do nothing
        }
    }

    private void addPointsBasedOnPassedTime(long seconds){
        if (seconds > 0) {
            pointsToAdd = (int) (seconds * scoreService.getPassiveIncome());
            pointsToAdd = pointsToAdd /5;
            scoreService.addPoints(pointsToAdd);
        }
    }

    public int getPointsToAdd() {
        return pointsToAdd;
    }
}
