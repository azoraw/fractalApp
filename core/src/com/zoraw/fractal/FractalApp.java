package com.zoraw.fractal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FractalApp extends ApplicationAdapter {

   private Stage stage;

    @Override
    public void create() {
        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        FractalView fractalView = new FractalView(viewport);
        SettingsTable settingsTable = new SettingsTable(viewport);
        stage.addActor(fractalView);
        stage.addActor(settingsTable);
        stage.setKeyboardFocus(fractalView);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
