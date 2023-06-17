package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.IActivityRequestHandler;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.model.CompetitionOpponent;

import java.util.Map;

public class DesktopLauncher implements IActivityRequestHandler {

	private static DesktopLauncher application;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.height=1080/2;
		config.width=1920/2;

		if (application == null) {
			application = new DesktopLauncher();
		}

		new LwjglApplication(new MyGdxGame(application, null), config);

	}

	@Override
	public void showAds(boolean show) {

	}

	@Override
	public boolean isAdShown() {
		return false;
	}

	@Override
	public void signOut() {

	}


	@Override
	public void saveData(Map value) {

	}

	@Override
	public Map loadData() {
		return null;
	}



}
