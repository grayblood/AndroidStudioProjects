package com.mygdx.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.controllers.FlyingObjectController;
import com.mygdx.entities.Player;
import com.mygdx.game.MyGdxGame;
import com.mygdx.service.PassiveIncomeService;
import com.mygdx.ui.*;

public class GameplayScreen extends AbstractScreen {
    private Image bgImage;
    private Player player;
    private PlayerButton playerButton;
    private Buybutton1 BuyButton;
    private Buybutton1 BuyButton2;
    private Buybutton1 BuyButton3;
    private GameLabel gameLabel;
    private GameLabel gameLabel2;
    private GameLabel gameLabel3;
    private GameLabel gameLabel4;
    private GameLabel gameLabel5;
    private FlyingObjectController flyingObjectController;
    private PassiveIncomeService passiveIncomeService;
    private int pricebutton;
    private int pricebutton2;
    private int pricebutton3;
    boolean got1 = false;
    boolean got2 = false;
    boolean got3 = false;
    boolean got4 = false;

    public GameplayScreen(MyGdxGame game) {
        super(game);
    }

    protected void init() {

        bgInit();
        initPlayer();
        initPlayerButton();

        initFlyingStuffObjects();//golden cookies
        initBuy();
        initScoreLabel1();
        initpriceLabel2();
        initpriceLabel3();
        initpriceLabel5();
        WinLabel();
        startTheMusic(); // nu se
        initPassiveIncomeService();

        //reset
        //game.getScoreService().resetGameScore();
        setPricebutton();

    }

    private void setPricebutton() {
        pricebutton = 10;
        pricebutton2 = 1000;
        pricebutton3 = 100000;

    }

    private void AddPricebutton() {
        pricebutton += (pricebutton / 0.5);

    }

    private void AddPricebutton2() {

        pricebutton2 += (pricebutton2 / 0.5);
    }
    private void AddPricebutton3() {

        pricebutton3 += (pricebutton3 / 0.5);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        update();

        //System.out.printf("Points: " + game.getScoreService().getPoints());

        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();
    }

    @Override
    public void pause() {
       /* super.pause();
        game.getScoreService().saveCurrentTimestamp();
        game.getScoreService().saveCurrentGamestate();

        */
    }


    private void update() {
        gameLabel.setText("GALLETAS: " + game.getScoreService().getPoints());
        gameLabel2.setText("Precio: " + pricebutton);
        gameLabel3.setText("Precio: " + pricebutton2);
        gameLabel5.setText("Precio: " + pricebutton3);

        stage.act();
        if (game.getScoreService().getPoints() > 100 && game.getScoreService().getPoints() < 150) {

            if(!got1) {
                gameLabel4.setText("You got your fist 100, KEEP UP");
                got1 = true;
            }
        } else if (game.getScoreService().getPoints() >= 1000 && game.getScoreService().getPoints() <= 1100) {


            if(!got2) {
            gameLabel4.setText("1000 milestone");
                got2 = true;
            }
        } else if (game.getScoreService().getPoints() >= 50000 && game.getScoreService().getPoints() <= 51000) {


            if(!got3) {
            gameLabel4.setText("50000 milestone");
                got3 = true;

            }
        } else if (game.getScoreService().getPoints() >= 1000000000 && game.getScoreService().getPoints() <= 1000500000) {


            if(!got4) {
                gameLabel4.setText("You Win, but you can continue playing");
                got4 = true;

            }

        } else {

            gameLabel4.setText(" ");
        }

    }

    private void initPassiveIncomeService() {
        passiveIncomeService = new PassiveIncomeService(game.getScoreService());
        passiveIncomeService.start();
    }

    private void startTheMusic() {
        game.getSoundService().startPlayingMusic(true);
    }

    private void initFlyingStuffObjects() {
        flyingObjectController = new FlyingObjectController(game, stage);
    }

    private void bgInit() {
        bgImage = new Image(new Texture("wallpaper.jpg"));
        stage.addActor(bgImage);
    }

    private void initBuy() {
        BuyButton = new Buybutton1(new IClickCallBack() {

            @Override
            public void onClick() {
                int points = game.getScoreService().getPoints();


                if (points >= pricebutton) {

                    game.getSoundService().playbuySound();
                    game.getScoreService().addPoints(-pricebutton);
                    game.getScoreService().addPassiveIncome();
                    game.getScoreService().addPassiveIncome();
                    System.out.print(pricebutton);
                    System.out.printf(" / ");
                    System.out.print(pricebutton / 1.5);
                    System.out.printf(" / ");
                    AddPricebutton();
                    System.out.print(pricebutton);
                    System.out.printf(" /n ");
                }
            }
        }, 100, 330, 560);

        BuyButton2 = new Buybutton1(new IClickCallBack() {

            @Override
            public void onClick() {
                int points = game.getScoreService().getPoints();


                if (points >= pricebutton2) {

                    game.getSoundService().playbuySound();
                    game.getScoreService().addPoints(-pricebutton2);
                    for (int i = 0; i < 250; i++) {
                        game.getScoreService().addPassiveIncome();
                    }

                    AddPricebutton2();
                }
            }
        }, 100, 330, 360);
        BuyButton3 = new Buybutton1(new IClickCallBack() {

            @Override
            public void onClick() {
                int points = game.getScoreService().getPoints();


                if (points >= pricebutton3) {

                    game.getSoundService().playbuySound();
                    game.getScoreService().addPoints(-pricebutton3);
                    for (int i = 0; i < 50000; i++) {
                        game.getScoreService().addPassiveIncome();

                    }
                    AddPricebutton3();
                }
            }
        }, 100, 330, 130);

        stage.addActor(BuyButton);
        stage.addActor(BuyButton2);
        stage.addActor(BuyButton3);
    }

    private void initScoreLabel1() {
        gameLabel = new GameLabel(70, 650);
        stage.addActor(gameLabel);
    }

    private void initpriceLabel2() {
        gameLabel2 = new GameLabel(350, 600);
        stage.addActor(gameLabel2);
    }

    private void initpriceLabel3() {
        gameLabel3 = new GameLabel(340, 385);
        stage.addActor(gameLabel3);
    }
    private void initpriceLabel5() {
        gameLabel5 = new GameLabel(350, 155);
        stage.addActor(gameLabel5);
    }

    private void WinLabel() {
        gameLabel4 = new GameLabel(100, 400);
        stage.addActor(gameLabel4);
    }

    private void initPlayerButton() {
        playerButton = new PlayerButton(new IClickCallBack() {
            @Override
            public void onClick() {
                player.reactOnClick();
                game.getScoreService().addPoint();
                game.getSoundService().playcookieSound();
            }
        });
        stage.addActor(playerButton);
    }

    private void initPlayer() {
        player = new Player();
        stage.addActor(player);
    }


}