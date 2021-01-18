package com.zoraw.fractal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ChangeFractalSettings extends Table {

    private final FractalChanger fractalChanger;

    public ChangeFractalSettings(Viewport viewport, FractalChanger fractalChanger, String fractalName) {
        this.fractalChanger = fractalChanger;
        this.setWidth(50);
        this.align(Align.left | Align.top);
        this.setPosition(0, viewport.getScreenHeight());
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        SelectBox<String> selectBox = new SelectBox<>(skin);
        selectBox.setItems(Fractal.getNames());
        selectBox.setSelected(fractalName);
        selectBox.addListener(new FractalChangeListener(this));

        this.add(selectBox);
    }

    public void changeFractal(Fractal fractal) {
        this.fractalChanger.changeFractal(fractal);
    }
}
