package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.screens.BaseScreen;
//import com.mygdx.game.screens.MyGdxGame;

/**
 * Created by Darshi on 5/2/2015.
 */
public class Coin extends BaseSprite {

    public Coin(Body body) {
        super(body);
        Texture tex = BaseScreen.res.getTexture("crystal");
        TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
        setAnimation(sprites, 1 / 12f);
    }
}
