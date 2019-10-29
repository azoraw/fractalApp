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
        FractalActor fractalActor = new FractalActor(viewport);
        //SettingsActor settingsActor = new SettingsActor();
        stage.addActor(fractalActor);
        //stage.addActor(settingsActor);
        stage.setKeyboardFocus(fractalActor);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
