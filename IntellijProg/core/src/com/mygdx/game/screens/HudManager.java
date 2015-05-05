package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.handlers.MyInput;

/*
  Created by Darshi on 4/24/2015. */


public class HudManager {

    SpriteBatch batch;
    private Stage stage;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private TextButton buttonPause;
    private BitmapFont white, black;
    private Label heading;
    //    private TweenManager tweenManager;
    private Label scoreLabel;
    private Label scoreValue;

    public void init(FitViewport fitViewport, SpriteBatch batch) {
        this.batch = batch;
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);
        stage = new Stage(fitViewport, batch);
        table = new Table(skin);
        table.setBounds(0, 0, fitViewport.getWorldWidth(), fitViewport.getWorldHeight());

        //creating fonts
        white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);

        // creating buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;
//        textButtonStyle.font.scale(0.001f);
//        textButtonStyle.font.scale(.1f);

        buttonPause = new TextButton("PAUSE", textButtonStyle);
//        buttonPause.pad(20);
//        buttonPause.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                GameStateManager.toJson();
//                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
//            }
//        });

        //creating  Score
        Label.LabelStyle labelStyle = new Label.LabelStyle(white, Color.BLACK);
        labelStyle.font.scale(.1f);
        //TextField.TextFieldStyle  textFieldStyle= new TextField.TextFieldStyle(white, Color.WHITE, )
        scoreLabel = new Label("Score:", labelStyle);
        scoreValue = new Label("", labelStyle);
        //scoreText = new TextField("", skin);

//        heading.setFontScale(1);

        //putting all in table
        table.add(scoreLabel);
        table.add(scoreValue).width(30);
        table.row();//go to next row
        //table.add(buttonPause).width(30);
        //align table
        table.left().top();
        table.debug();
    }

    public void update(float delta, int score) {
//        tweenManager.update(delta);
        scoreValue.setText(String.valueOf(score));
    }


    public void render() {
        batch.begin();
        table.draw(batch, 1);
        scoreLabel.draw(batch,1);
        scoreValue.draw(batch, 1);
  //      buttonPause.draw(batch, 1);
        batch.end();
    }
//    public void render() {
////        batch.setProjectionMatrix(hudCam.combined);
////        batch.begin();
////        table.draw(batch, 1);
////        buttonPause.draw(batch, 1);
////        batch.end();
//    }

    public void resize(int width, int height) {
//        stage.getViewport().update(width, height);
        table.invalidateHierarchy();
        table.setSize(width, height);
    }

    public void dispose() {
//        stage.dispose();
        skin.dispose();
        atlas.dispose();
        black.dispose();
        white.dispose();


    }

    boolean isInButton(int x, int y) {
//        Gdx.app.log("HudManager", buttonPause.getX() + "," + buttonPause.getY() + "::" + buttonPause.getWidth() + "," + buttonPause.getHeight()
//                + "::" + x + "," + y);
        if ((buttonPause.getX()) <= x && (buttonPause.getX() + buttonPause.getWidth()) >= x) {
            if ((buttonPause.getY()) <= y && (buttonPause.getY() + buttonPause.getHeight()) >= y) {
//                Gdx.app.log("HUD", "Yes");
                MyInput.setKey(MyInput.PAUSE, true);
//                GameStateManager.toJson();
                return true;
            }
        }
        MyInput.setKey(MyInput.PAUSE, false);
        return false;
    }

}
