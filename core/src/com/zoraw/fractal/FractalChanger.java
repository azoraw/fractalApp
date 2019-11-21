package com.zoraw.fractal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.zoraw.fractal.juliaSet.FractalInputProcessor;
import com.zoraw.fractal.juliaSet.JuliaSet;

public class FractalChanger {

    private final Stage stage;
    private InputMultiplexer inputMultiplexer;
    private FractalInputProcessor fractalInputProcessor;

    public FractalChanger(Stage stage) {
        this.stage = stage;
        stage.getRoot().addCaptureListener(getSettingListener());
        Actor currentFractal = new JuliaSet(stage.getViewport()); //todo: add strategy
        GeneralSettings generalSettings = new GeneralSettings(stage.getViewport(), this);
        fractalInputProcessor = new FractalInputProcessor((JuliaSet) currentFractal);
        inputMultiplexer = new InputMultiplexer(stage, fractalInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
        stage.addActor(currentFractal);
        stage.setKeyboardFocus(currentFractal);
        stage.addActor(generalSettings);
    }

    public void changeFractal(Fractal fractal) {
        removeLastFractal();
        stage.addActor(new GeneralSettings(stage.getViewport(), this));
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

    private void removeLastFractal() {
        inputMultiplexer.removeProcessor(fractalInputProcessor);
        for (Actor actor : stage.getActors()) {
            actor.addAction(Actions.removeActor());
        }
    }
}
