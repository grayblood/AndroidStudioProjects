package com.mygdx.controllers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.entities.FlyingObject;
import com.mygdx.entities.FlyingObject.FlyingObjectType;
import com.mygdx.game.MyGdxGame;

public class FlyingObjectController {

    private int spawnTime;

    public FlyingObjectController(MyGdxGame game, Stage stage) {
        init(game, stage);
    }

    private void init(final MyGdxGame game, final Stage stage) {
        randomizeSpawnTime();

        Timer.schedule(new Timer.Task() {

            @Override
            public void run() {

                Timer.schedule(new Timer.Task() {

                    @Override
                    public void run() {
                        addRandomFlyingObjectToStage(game, stage);
                        randomizeSpawnTime();
                    }
                }, spawnTime);
            }
        }, 0, 5);
    }

    private void randomizeSpawnTime() {
        spawnTime = MathUtils.random(5, 10); // start 5 end 10 sec
    }

    private void addRandomFlyingObjectToStage(MyGdxGame game, Stage stage) {
        FlyingObject flyingObject = null;

            flyingObject = new FlyingObject(FlyingObjectType.MONEY, game);


        stage.addActor(flyingObject);
        flyingObject.fly();
    }
}