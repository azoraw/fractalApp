package com.zoraw.fractal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zoraw.fractal.settings.SettingsTable;

public class FractalApp extends ApplicationAdapter {

    private Stage stage;

    @Override
    public void create() {
        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);
        stage.getRoot().addCaptureListener(getSettingListener());
        FractalView fractalView = new FractalView(viewport);
        SettingsTable settingsTable = new SettingsTable(viewport);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new FractalInputProcessor(fractalView)));
        fractalView.addActor(settingsTable);
        stage.addActor(fractalView);
        stage.setKeyboardFocus(fractalView);
    }

    private InputListener getSettingListener() {
        return new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                textFieldFocusRelease(event);
                return false;
            }
        };
    }

    private void textFieldFocusRelease(InputEvent event) {
        if (!(event.getTarget() instanceof TextField)) stage.setKeyboardFocus(null);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
