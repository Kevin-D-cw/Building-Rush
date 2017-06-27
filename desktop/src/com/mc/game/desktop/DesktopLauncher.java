package com.mc.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mc.game.BuildingRush;
import com.badlogic.gdx.Files;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
        config.height = 832;
        config.fullscreen = false;
        config.resizable = false;
        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		config.addIcon("windowLogo.png", Files.FileType.Internal);
		new LwjglApplication(new BuildingRush(), config);
	}
}
