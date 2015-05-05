package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.handlers.Animation;
import com.mygdx.game.handlers.B2DVariables;

/**
 * Created by Darshi on 5/2/2015.
 */

public class BaseSprite {

    protected Body body;
    protected Animation animation;
    protected float width;
    protected float height;

    public BaseSprite(Body body) {
        this.body = body;
        animation = new Animation();
    }

    public void setAnimation(TextureRegion[] reg, float delay) {
        animation.setFrames(reg, delay);
        width = reg[0].getRegionWidth();
        height = reg[0].getRegionHeight();
    }

    public void update(float dt) {
        animation.update(dt);
    }

    public void render(SpriteBatch spriteBatch) {
        update(1 / 60f);
        spriteBatch.begin();
        spriteBatch.draw(
                animation.getFrame(),
                body.getPosition().x * B2DVariables.PPM - width / 2, //set it to original game size from Box2D
                body.getPosition().y * B2DVariables.PPM - height / 2 //set it to original game size from Box2D
        );
        spriteBatch.end();
    }

    //getters
    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


}

