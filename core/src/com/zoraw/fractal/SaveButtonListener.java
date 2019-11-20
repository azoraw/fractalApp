package com.zoraw.fractal;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Builder;

@Builder
public class SaveButtonListener extends ClickListener {

    private final SettingsTable settingsTable;
    private final TextField width;
    private final TextField height;

    @Override
    public void clicked(InputEvent event, float x, float y) {
        settingsTable.fire(SaveButtonEvent.builder()
                .width(Integer.parseInt(width.getText()))
                .height(Integer.parseInt(height.getText()))
                .build());
    }
}
