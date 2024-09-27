package com.mygdx.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Buybutton1 extends Button {



    public Buybutton1(final IClickCallBack callback , int cube, int newx, int newy) {
        super(prepareResetButtonStyle());
        init(callback,cube,newx,newy);

    }

    private void init(final IClickCallBack callback , int m_cube, int XX, int YY) {
        this.setWidth(m_cube);
        this.setHeight(m_cube);
        this.setX(XX);
        this.setY(YY);

        this.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                callback.onClick();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private static Button.ButtonStyle prepareResetButtonStyle() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui-red.atlas"));
        Skin skin = new Skin(atlas);
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = skin.getDrawable("button_01");
        buttonStyle.down = skin.getDrawable("button_02");
        return buttonStyle;
    }
}
