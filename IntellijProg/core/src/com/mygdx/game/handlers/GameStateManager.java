package com.mygdx.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.screens.BaseScreen;

/**
 * Created by Darshi on 5/4/2015.
 */
public class GameStateManager {


    public static float playerPositionX;
    public static float playerPositionY;
    public static int numCoinsCollected;
    public static float camPositionX;
    public static float camPositionY;
    public static float hudCamPositionX;
    public static float hudCamPositionY;
    public static float box2DCamX;
    public static float box2DCamY;
    public static float tiledMapX;
    public static float tiledMapY;

    public static void init() {
        playerPositionX = 50;
        playerPositionY = 50;
        numCoinsCollected = 0;
        camPositionX = BaseScreen.V_WIDTH / 2;
        camPositionY = BaseScreen.V_HEIGHT / 2;
        box2DCamX = camPositionX / B2DVariables.PPM;
        box2DCamY = camPositionY / B2DVariables.PPM;
        //fill hudCam
    }

    public static void resume() {
        // read from file all the values

    }


    public static void render(float delta) {
        //update postion of player
    }

    public static void toJson() {
        Json json = new Json();
        Gdx.files.local("./save.json").writeString(json.toJson(new GameStateInstance(playerPositionX, playerPositionY, numCoinsCollected,
                camPositionX, camPositionY, hudCamPositionX, hudCamPositionY, box2DCamX, box2DCamY,
                tiledMapX, tiledMapY)).toString(), false, null);
    }


    public static class GameStateInstance {
        public float playerPositionX;
        public float playerPositionY;
        public int numCoinsCollected;
        public float camPositionX;
        public float camPositionY;
        public float hudCamPositionX;
        public float hudCamPositionY;
        public float box2DCamX;
        public float box2DCamY;
        public float tiledMapX;
        public float tiledMapY;

        public GameStateInstance(float playerPositionX, float playerPositionY, int numCoinsCollected, float camPositionX, float camPositionY, float hudCamPositionX, float hudCamPositionY, float box2DCamX, float box2DCamY, float tiledMapX, float tiledMapY) {
            this.playerPositionX = playerPositionX;
            this.playerPositionY = playerPositionY;
            this.numCoinsCollected = numCoinsCollected;
            this.camPositionX = camPositionX;
            this.camPositionY = camPositionY;
            this.hudCamPositionX = hudCamPositionX;
            this.hudCamPositionY = hudCamPositionY;
            this.box2DCamX = box2DCamX;
            this.box2DCamY = box2DCamY;
            this.tiledMapX = tiledMapX;
            this.tiledMapY = tiledMapY;
        }
    }
}