package com.mygdx.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image {


    int width = Gdx.graphics.getWidth(); //Obtenemos el ancho de la pantalla.
    int height = Gdx.graphics.getHeight(); //Obtenemos el alto de la pantalla
    int redonda = 120;

    public Player() {
        super(new Texture("Cookie.png"));

        
        this.setSize(redonda, redonda);

        // starting position
        this.setPosition(100, 400);


    }

    public void reactOnClick() {

        int growAmmountX = 20;
        int growAmmountY = 20;
        float growActionTimer = 0.2f;
        Action growAction = Actions.sequence(
                Actions.sizeBy(growAmmountX, growAmmountY, growActionTimer, Interpolation.circleOut),
                Actions.sizeBy(-growAmmountX, -growAmmountY, growActionTimer, Interpolation.circle));

        this.addAction(growAction);


    }
}