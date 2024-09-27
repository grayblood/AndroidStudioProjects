package com.mygdx.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameLabel extends Label {

    public GameLabel(int x, int y) {
        super("", prepareLabelStyle());
        init(x, y);
    }

    private void init(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    private static LabelStyle prepareLabelStyle() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        return labelStyle;
    }
}
