package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.Level1;
import com.mygdx.game.screens.Level2;
import com.mygdx.game.screens.SplashScreen;

/**
 * Created by Darshi on 5/3/2015.
 */
public class DesiGirlGame extends Game {

    @Override
    public void create() {
        setScreen(new SplashScreen());
    }
}
