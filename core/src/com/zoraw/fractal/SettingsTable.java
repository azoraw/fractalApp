package com.zoraw.fractal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsTable extends Table {

    public SettingsTable(ScreenViewport viewport) {
        this.setWidth(50);
        this.align(Align.right | Align.top);
        this.setPosition(viewport.getScreenWidth() - 50, viewport.getScreenHeight());
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextField initRe = new TextField("initRe", skin);
        TextField initIm = new TextField("initIm", skin);
        Button button = new TextButton("button", skin);
        button.addListener(new SettingsListener(initRe, initIm, this));
        this.add(initRe);
        this.row();
        this.add(initIm);
        this.row();
        this.add(button);
    }
}
