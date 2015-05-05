package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.DesiGirlGame;
import com.mygdx.game.screens.BaseScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = BaseScreen.TITLE;
        config.height = BaseScreen.V_HEIGHT * BaseScreen.SCALE;
        config.width = BaseScreen.V_WIDTH * BaseScreen.SCALE;
		new LwjglApplication(new DesiGirlGame(),config);
	}
}
