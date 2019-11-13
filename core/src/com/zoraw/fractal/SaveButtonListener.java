package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Builder;

@Builder
public class SaveButtonListener extends ClickListener {

    private final SettingsTable settingsTable;

    @Override
    public void clicked(InputEvent event, float x, float y) {
        settingsTable.fire(new SaveButtonEvent());
    }

}
