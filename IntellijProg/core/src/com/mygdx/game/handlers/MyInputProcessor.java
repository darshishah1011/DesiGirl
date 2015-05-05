package com.mygdx.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by Darshi on 5/1/2015.
 */
public class MyInputProcessor extends InputAdapter {
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP)//to jump
        {
            MyInput.setKey(MyInput.JUMP, true);
        }
        if (keycode == Input.Keys.RIGHT)//to move to right
        {
            MyInput.setKey(MyInput.RIGHT, true);
        }
        if (keycode == Input.Keys.LEFT)//to move to left
        {
            MyInput.setKey(MyInput.LEFT, true);
        }


        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.UP) {
            MyInput.setKey(MyInput.JUMP, false);
        }
        if (keycode == Input.Keys.RIGHT) {
            MyInput.setKey(MyInput.RIGHT, false);
        }
        if (keycode == Input.Keys.LEFT) {
            MyInput.setKey(MyInput.LEFT, false);
        }
        return true;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (button == Input.Buttons.LEFT) {
            MyInput.setMouseX = screenX;
            MyInput.setMouseY = screenY;
            Gdx.app.log("Event", "x:" + screenX + " y:" + screenY);
            return true;
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            MyInput.setMouseX = -1;
            MyInput.setMouseY = -1;
//            Gdx.app.log("Event", "x:" + screenX + " y:" + screenY);
            return true;
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }
}
