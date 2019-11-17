package com.zoraw.fractal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.zoraw.fractal.Settings.INITIAL_SETTINGS;

public class SettingsTable extends Table {

    private final SettingsEmitter settingsEmitter;

    public SettingsTable(ScreenViewport viewport) {
        this.setWidth(50);
        this.align(Align.right | Align.top);
        this.setPosition(viewport.getScreenWidth() - 50, viewport.getScreenHeight());
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Label re = new Label("re: ", skin);
        Label im = new Label("im: ", skin);
        Label numberOfIterationLabel = new Label("no. iteration: ", skin);
        Label rLabel = new Label("r: ", skin);
        Label gLabel = new Label("g: ", skin);
        Label bLabel = new Label("b: ", skin);
        Label xOffsetLabel = new Label("x offset: ", skin);
        Label yOffsetLabel = new Label("y offset: ", skin);
        Label zoomLabel = new Label("zoom: ", skin);
        Label emptyLabel = new Label("", skin);

        TextField initRe = new TextField(String.valueOf(INITIAL_SETTINGS.getComplexNumber().getRe()), skin);
        TextField initIm = new TextField(String.valueOf(INITIAL_SETTINGS.getComplexNumber().getIm()), skin);
        TextField numberOfIteration = new TextField(String.valueOf(INITIAL_SETTINGS.getNumberOfIteration()), skin);
        TextField rTextField = new TextField(String.valueOf(INITIAL_SETTINGS.getRMultiplier()), skin);
        TextField gTextField = new TextField(String.valueOf(INITIAL_SETTINGS.getGMultiplier()), skin);
        TextField bTextField = new TextField(String.valueOf(INITIAL_SETTINGS.getBMultiplier()), skin);
        TextField xOffsetTextField = new TextField(String.valueOf(INITIAL_SETTINGS.getXOffset()), skin);
        TextField yOffsetTextField = new TextField(String.valueOf(INITIAL_SETTINGS.getYOffset()), skin);
        TextField zoomTextField = new TextField(String.valueOf(INITIAL_SETTINGS.getZoom()), skin);
        Button saveButton = new TextButton("save", skin);
        saveButton.addListener(new SaveButtonListener(this));
        Button generateButton = new TextButton("generate", skin);
        settingsEmitter = SettingsEmitter.builder()
                .re(initRe)
                .im(initIm)
                .numberOfIteration(numberOfIteration)
                .r(rTextField)
                .g(gTextField)
                .b(bTextField)
                .settingsTable(this)
                .xOffset(xOffsetTextField)
                .yOffset(yOffsetTextField)
                .zoom(zoomTextField)
                .build();
        generateButton.addListener(settingsEmitter);
        this.add(re);
        this.add(initRe);
        this.row();
        this.add(im);
        this.add(initIm);
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
        this.add(emptyLabel);
        this.add(saveButton);
        this.row();
        this.add(emptyLabel);
        this.add(generateButton);
    }

    public void updateTextFields(Settings settings) {
        this.settingsEmitter.updateTextFields(settings);
    }

}
