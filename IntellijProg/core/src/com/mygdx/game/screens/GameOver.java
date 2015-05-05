package com.mygdx.game.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.tweens.ActorAccessor;

/**
 * Created by Darshi on 4/24/2015.
 */
public class GameOver implements Screen {

    private Stage stage;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont white,black;
    private Label heading;
    private TweenManager tweenManager;


    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // allows to click button
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //creating fonts
        white = new BitmapFont(Gdx.files.internal("font/white.fnt"),false);
        black = new BitmapFont(Gdx.files.internal("font/black.fnt"),false);

        //creating  heading
        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);
        heading = new Label("Game Over",headingStyle);
        heading.setFontScale(3);

        //putting all in table
        table.add(heading);
        table.row();//go to next row

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

        //fade heading
        Timeline.createSequence().beginSequence()   //to create a sequence of animation one after the other
                .push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
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
