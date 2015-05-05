package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.FPSLogger;
import com.mygdx.game.DesiGirlGame;
import com.mygdx.game.handlers.GameStateManager;

/**
 * Created by Darshi on 5/4/2015.
 */
public class Level1 extends BaseScreen {
    private static final String TAG = "Level1";
    FPSLogger logger = new FPSLogger();

    public static Level1 newInstance(DesiGirlGame desiGirlGame) {
        Level1 level1 = new Level1();
        level1.parent = desiGirlGame;
        return level1;

    }

    @Override
    public void show() {
        super.show();
        createTileMap("maps/Level1.tmx");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        spriteBatch.setProjectionMatrix(mainCamera.combined);
        logger.log();
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
