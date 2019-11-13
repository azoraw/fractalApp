package com.zoraw.fractal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zoraw.fractal.FractalApp;

public class DesktopLauncher {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = WIDTH;
        config.height = HEIGHT;
        new LwjglApplication(new FractalApp(), config);
    }
}
