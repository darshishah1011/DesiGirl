package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.FPSLogger;
import com.mygdx.game.DesiGirlGame;
import com.mygdx.game.handlers.GameStateManager;


/**
 * Created by Darshi on 5/4/2015.
 */
public class Level2 extends BaseScreen {
    private static final String TAG = "Level2";
    FPSLogger logger = new FPSLogger();


    public static Level2 newInstance(DesiGirlGame desiGirlGame) {
        Level2 level2 = new Level2();
        level2.parent = desiGirlGame;
        return level2;

    }

    @Override
    public void show() {
        super.show();
        createTileMap("maps/Level2.tmx");
    }

    @Override
    public void render(float delta) {
        spriteBatch.setProjectionMatrix(mainCamera.combined);
        logger.log();
        super.render(delta);
        orthogonalTiledMapRenderer.setView(mainCamera);
        orthogonalTiledMapRenderer.render();

        mainCamera.update();

        GameStateManager.render(delta);

        for (int i = 0; i < coins.size; i++) {
            coins.get(i).render(spriteBatch);
        }

        player.render(spriteBatch);

        spriteBatch.setProjectionMatrix(hudCam.combined);
        hudManager.render();

    }


}
