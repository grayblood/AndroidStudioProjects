package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.MyGdxGame;

public class FlyingObject extends Image {

    public enum FlyingObjectType {
        MONEY
    }

    public final static String MONEY = "GCookie.png";

    private  int WIDHT = 70;
    private  int HEIGHT = 70;
    private  int soize;
    private final static int STARTING_X_1 = 0;
    private final static int STARTING_X_2 = MyGdxGame.WIDTH;
    private final static int STARTING_Y = -100; // outside the window
    private int startingX;

    private FlyingObjectType type;
    private MyGdxGame game;

    public FlyingObject(FlyingObjectType type, MyGdxGame game) {
        super(new Texture(getTextireString(type)));

        this.type = type;
        this.game = game;
        soize = MathUtils.random(20, 100);

        this.setOrigin(WIDHT / 2, HEIGHT / 2);
        this.setSize(soize, soize);


        // starting position
        startingX = MathUtils.randomBoolean() ? STARTING_X_1 : STARTING_X_2; // if else
        this.setPosition(startingX, STARTING_Y);

        this.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                reactOnClick();

                FlyingObject.this.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }


    private void reactOnClick() {

            game.getScoreService().addPoints(50);

        FlyingObject.this.remove();
    }

    private static String getTextireString(FlyingObjectType type) {

            return MONEY;


    }

    public void fly() {

        int xSign = 0;
        if (startingX == STARTING_X_1) {
            xSign = 1;
        } else {
            xSign = -1;
        }

        int time1 = MathUtils.random(1, 6);
        int time2 = MathUtils.random(1, 6);
        int randomYEffect = MathUtils.random(-100, 500);

        Action a = Actions.parallel(
                Actions.moveBy(xSign * 300 + (MathUtils.random(-200, 200)), 200 + randomYEffect, time1),
                Actions.rotateBy(360, time1)
        );
        Action b = Actions.parallel(
                Actions.moveBy(xSign * -500 + (MathUtils.random(-200, 200)), 900, time2),
                Actions.rotateBy(360, time2)
        );
        Action c = Actions.run(new Runnable() {
            @Override
            public void run() {
                FlyingObject.this.remove(); // removing object, when action done
            }
        });

        this.addAction(Actions.sequence(a, b, c));
    }
}

