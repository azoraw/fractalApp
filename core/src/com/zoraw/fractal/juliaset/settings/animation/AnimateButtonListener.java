package com.zoraw.fractal.juliaset.settings.animation;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.zoraw.fractal.juliaset.settings.SettingsTable;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public class AnimateButtonListener extends ClickListener {

    private final SettingsTable settingsTable;
    private final TextField width;
    private final TextField height;
    private final TextField numberOfFrames;

    @Override
    public void clicked(InputEvent event, float x, float y) {
        settingsTable.fire(AnimateButtonEvent.builder()
                .width(Integer.parseInt(width.getText()))
                .height(Integer.parseInt(height.getText()))
                .numberOfFrames(Double.parseDouble(numberOfFrames.getText()))
                .build());
    }
}
