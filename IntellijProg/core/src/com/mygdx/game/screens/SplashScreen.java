package com.mygdx.game.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.tweens.SpriteAccessor;

/**
 * Created by Darshi on 4/19/2015.
 */
public class SplashScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private Texture img;
    private Sprite sprite;
    private TweenManager tweenManager;
    private Game parent;

    public static SplashScreen newInstance(Game parent) {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.parent = parent;
        return splashScreen;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("Desigirl.png");
        sprite = new Sprite(img);
        tweenManager = new TweenManager();

        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.set(sprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);// target 0 means transperent
        Tween.to(sprite, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 0.5f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int i, BaseTween<?> baseTween) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        }).start(tweenManager); //target 1 means not transperent

    }

    @Override
    public void dispose() {
        batch.dispose();
        sprite.getTexture().dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batch.begin();
        sprite.draw(batch);
        batch.end();
    }


}
