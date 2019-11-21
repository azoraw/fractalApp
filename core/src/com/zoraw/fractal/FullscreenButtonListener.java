package com.zoraw.fractal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class FullscreenButtonListener extends ClickListener {

    private final TextButton fullscreenButton;

    public FullscreenButtonListener(TextButton fullscreenButton) {
        this.fullscreenButton = fullscreenButton;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        boolean isFullScreen = Gdx.graphics.isFullscreen();
        Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
        if (isFullScreen) {
            Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
            fullscreenButton.setText("fullscreen mode");
        } else {
            Gdx.graphics.setFullscreenMode(currentMode);
            fullscreenButton.setText("windowed mode");
        }
    }
}
