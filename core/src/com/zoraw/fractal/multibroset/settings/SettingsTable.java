package com.zoraw.fractal.multibroset.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SettingsTable extends Table {

    private final SettingsEmitter settingsEmitter;

    public SettingsTable(Viewport viewport) {
        this.setWidth(50);
        this.align(Align.right | Align.top);
        this.setPosition(viewport.getScreenWidth() - 50, viewport.getScreenHeight());
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Label multibrotPowerLabel = new Label("multibrot power: ", skin);
        Label moveDeltaLabel = new Label("move delta: ", skin);
        Label numberOfIterationLabel = new Label("no. iteration: ", skin);
        Label rLabel = new Label("r: ", skin);
        Label gLabel = new Label("g: ", skin);
        Label bLabel = new Label("b: ", skin);
        Label widthLabel = new Label("width: ", skin);
        Label heightLabel = new Label("height: ", skin);

        Label xOffsetLabel = new Label("x offset: ", skin);
        Label yOffsetLabel = new Label("y offset: ", skin);
        Label zoomLabel = new Label("zoom: ", skin);
        Label zoomMultiplierLabel = new Label("zoom multiplier: ", skin);
        Label emptyLabel = new Label("", skin);

        Settings initSettings = Settings.getInitialSettings();
        TextField multibrotPower = new TextField(String.valueOf(initSettings.getMultibrotPower().getRe()), skin);
        TextField moveDeltaTextField = new TextField(String.valueOf(initSettings.getMoveDelta()), skin);
        TextField numberOfIteration = new TextField(String.valueOf(initSettings.getNumberOfIteration()), skin);
        TextField rTextField = new TextField(String.valueOf(initSettings.getRMultiplier()), skin);
        TextField gTextField = new TextField(String.valueOf(initSettings.getGMultiplier()), skin);
        TextField bTextField = new TextField(String.valueOf(initSettings.getBMultiplier()), skin);
        TextField widthTextField = new TextField(String.valueOf(viewport.getScreenWidth()), skin);
        TextField heightTextField = new TextField(String.valueOf(viewport.getScreenHeight()), skin);

        TextField xOffsetTextField = new TextField(String.valueOf(initSettings.getXOffset()), skin);
        TextField yOffsetTextField = new TextField(String.valueOf(initSettings.getYOffset()), skin);
        TextField zoomTextField = new TextField(String.valueOf(initSettings.getZoom()), skin);
        TextField zoomMultiplierTextField = new TextField(String.valueOf(initSettings.getZoomMultiplier()), skin);
        Button saveButton = new TextButton("save to png", skin);
        saveButton.addListener(new SaveButtonListener(this, widthTextField, heightTextField));
        Button generateButton = new TextButton("save settings", skin);
        settingsEmitter = SettingsEmitter.builder()
                .multibrotPower(multibrotPower)
                .moveDelta(moveDeltaTextField)
                .numberOfIteration(numberOfIteration)
                .r(rTextField)
                .g(gTextField)
                .b(bTextField)
                .settingsTable(this)
                .xOffset(xOffsetTextField)
                .yOffset(yOffsetTextField)
                .zoom(zoomTextField)
                .zoomMultiplier(zoomMultiplierTextField)
                .build();
        generateButton.addListener(settingsEmitter);
        this.add(multibrotPowerLabel);
        this.add(multibrotPower);
        this.row();
        this.add(moveDeltaLabel);
        this.add(moveDeltaTextField);
        this.row();
        this.add(numberOfIterationLabel);
        this.add(numberOfIteration);
        this.row();
        this.add(rLabel);
        this.add(rTextField);
        this.row();
        this.add(gLabel);
        this.add(gTextField);
        this.row();
        this.add(bLabel);
        this.add(bTextField);
        this.row();
        this.add(xOffsetLabel);
        this.add(xOffsetTextField);
        this.row();
        this.add(yOffsetLabel);
        this.add(yOffsetTextField);
        this.row();
        this.add(zoomLabel);
        this.add(zoomTextField);
        this.row();
        this.add(zoomMultiplierLabel);
        this.add(zoomMultiplierTextField);
        this.row();
        this.add(emptyLabel);
        this.add(generateButton);
        this.row();
        this.add(emptyLabel);
        this.row();
        this.add(widthLabel);
        this.add(widthTextField);
        this.row();
        this.add(heightLabel);
        this.add(heightTextField);
        this.row();
        this.add(emptyLabel);
        this.add(saveButton);
    }

    public void updateTextFields(Settings settings) {
        this.settingsEmitter.updateTextFields(settings);
    }

}
