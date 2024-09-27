package com.mygdx.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class BasicDialog extends Image {

    private GameLabel label;
    public final static int WIDTH = 380;
    public final static int HEIGHT = 400;

    public BasicDialog() {
        super(new Texture("dialog.png"));

        this.setOrigin(WIDTH / 2, HEIGHT / 2);
        this.setSize(WIDTH, HEIGHT);

        this.setPosition(60, 200);

        label = new GameLabel(100, 400);
        label.setPosition(100, 400);

        this.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                fadeOutDialog(); // znikniecie

                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void showDialog(Stage stage, String text){
        stage.addActor(this);

        label.setText(text);
        this.getStage().addActor(label);
    }

    private void fadeOutDialog() {
        SequenceAction sequenceAction = Actions.sequence();
        sequenceAction.addAction(Actions.fadeOut(1.5f));
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                BasicDialog.this.remove();
                label.remove();
                return false;
            }
        });
        this.addAction(sequenceAction);
        label.addAction(Actions.fadeOut(1.5f));
    }
}