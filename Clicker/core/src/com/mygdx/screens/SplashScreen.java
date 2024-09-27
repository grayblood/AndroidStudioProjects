package com.mygdx.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.MyGdxGame;


public class SplashScreen extends AbstractScreen {

    private Texture splashImg;

    public SplashScreen(final MyGdxGame game) {
        super(game);


        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new GameplayScreen(game));
            }
        }, 4);
    }

    protected void init() {
        // TODO implement better assests loading when game grows
        splashImg = new Texture("badlogic.jpg");
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        spriteBatch.draw(splashImg, 0, 0);
        spriteBatch.end();
    }
}
