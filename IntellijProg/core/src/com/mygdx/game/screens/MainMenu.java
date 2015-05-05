package com.mygdx.game.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.tweens.ActorAccessor;

/**
 * Created by Darshi on 4/24/2015.
 */
public class MainMenu implements Screen {

    private Stage stage;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private TextButton buttonPlay, buttonExit, buttonResume;
    private BitmapFont white,black;
    private Label heading;
    private TweenManager tweenManager;


    @Override
    public void show() {
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // allows to click button
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //creating fonts
        white = new BitmapFont(Gdx.files.internal("font/white.fnt"),false);
        black = new BitmapFont(Gdx.files.internal("font/black.fnt"),false);

        // creating buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        buttonExit = new TextButton("EXIT", textButtonStyle);
        buttonExit.pad(20);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonPlay.pad(20);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level1());
            }
        });

        buttonResume = new TextButton("RESUME", textButtonStyle);
        buttonResume.pad(20);

        //creating  heading
        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);
        heading = new Label("Desi Girl",headingStyle);
        heading.setFontScale(3);

        //putting all in table
        table.add(heading);
        table.row();//go to next row
        table.getCell(heading).spaceBottom(80); //add space between heading and button

        table.add(buttonResume);
        table.row();
        table.getCell(buttonResume).spaceBottom(30);

        table.add(buttonPlay);
        table.row();
        table.getCell(buttonPlay).spaceBottom(30);

        table.add(buttonExit);

       // table.debug(); // remove later
        stage.addActor(table);

        // create animation
        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class,new ActorAccessor() );

        //animation for heading color
        Timeline.createSequence().beginSequence()
                .push(Tween.to(heading,ActorAccessor.RGB,.5f).target(0,0,1))
                .push(Tween.to(heading,ActorAccessor.RGB,.5f).target(0,1,0))
                .push(Tween.to(heading,ActorAccessor.RGB,.5f).target(1,0,0))
                .push(Tween.to(heading,ActorAccessor.RGB,.5f).target(1,1,0))
                .push(Tween.to(heading,ActorAccessor.RGB,.5f).target(1,0,1))
                .push(Tween.to(heading,ActorAccessor.RGB,.5f).target(1,1,1))
                .end().repeat(Tween.INFINITY,0 ).start(tweenManager);

        //fade heading and buttons
        Timeline.createSequence().beginSequence()   //to create a sequence of animation one after the other
                .push(Tween.set(buttonResume, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
                .push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
                .push(Tween.to(buttonResume, ActorAccessor.ALPHA, .25f).target(1))
                .push(Tween.to(buttonPlay, ActorAccessor.ALPHA,.25f).target(1))
                .push(Tween.to(buttonExit, ActorAccessor.ALPHA, .25f).target(1))
                .end().start(tweenManager);

        //fade table
        Tween.from(table,ActorAccessor.ALPHA,.5f).target(0);
        Tween.from(table,ActorAccessor.ALPHA,.5f).target(Gdx.graphics.getHeight()/8).start(tweenManager);





    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
        table.invalidateHierarchy();
        table.setSize(width,height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        atlas.dispose();
        black.dispose();
        white.dispose();


    }


}
