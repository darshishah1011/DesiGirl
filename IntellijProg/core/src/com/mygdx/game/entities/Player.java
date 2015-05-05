package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.BaseScreen;

/**
 * Created by Darshi on 5/2/2015.
 */
public class Player extends BaseSprite {

    private int numCoins;
    private int totalCoins;

    public Player(Body body) {
        super(body);
        Texture tex = BaseScreen.res.getTexture("player"); //player sprite sheet
        TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[2]; //giving 3rd row of image to frame
        setAnimation(sprites, 1 / 12f);
    }

    //getters and setters for coins
    public void collectCoins() {
        numCoins++;
    }

    public int getNumCoins() {
        return numCoins;
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(int i) {
        totalCoins = i;
    }
}
