package com.zoraw.fractal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.zoraw.fractal.common.FractalActor;
import com.zoraw.fractal.juliaSet.JuliaSet;

public class FractalChanger {

    private final Stage stage;
    private InputMultiplexer inputMultiplexer;
    private InputProcessor currentFractalInputProcessor;

    public FractalChanger(Stage stage) {
        this.stage = stage;
        stage.getRoot().addCaptureListener(getSettingListener());
        FractalActor currentFractal = new JuliaSet(stage.getViewport()); //todo: add strategy
        GeneralSettings generalSettings = createGeneralSettings(Fractal.getInitial(), stage);
        currentFractalInputProcessor = currentFractal.getInputProcessor();
        inputMultiplexer = new InputMultiplexer(stage, currentFractalInputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
        stage.addActor(currentFractal);
        stage.setKeyboardFocus(currentFractal);
        stage.addActor(generalSettings);
    }

    public void changeFractal(Fractal fractal) {
        clearStage();
        stage.addActor(createGeneralSettings(fractal, stage));
    }

    private GeneralSettings createGeneralSettings(Fractal fractal, Stage stage) {
        return new GeneralSettings(stage.getViewport(), this, fractal.getFractalName());
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

    private void clearStage() {
        inputMultiplexer.removeProcessor(currentFractalInputProcessor);
        for (Actor actor : stage.getActors()) {
            actor.addAction(Actions.removeActor());
        }
    }
}
