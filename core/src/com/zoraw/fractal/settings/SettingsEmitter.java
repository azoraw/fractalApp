package com.zoraw.fractal.settings;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Builder;

@Builder
public class SettingsEmitter extends ClickListener {

    private final TextField re;
    private final TextField im;
    private final TextField moveDelta;
    private final TextField numberOfIteration;
    private final TextField r;
    private final TextField g;
    private final TextField b;
    private final TextField xOffset;
    private final TextField yOffset;
    private final TextField zoom;
    private final TextField zoomMultiplier;

    private final SettingsTable settingsTable;

    @Override
    public void clicked(InputEvent event, float x, float y) {
        updateSettings();
    }

    public void updateSettings() {
        try {
            settingsTable.fire(new SettingsChangeEvent(
                    Settings.builder()
                            .complexNumber(new ComplexNumber(Double.parseDouble(re.getText()),
                                    Double.parseDouble(im.getText())))
                            .moveDelta(Double.parseDouble(moveDelta.getText()))
                            .rMultiplier(Integer.parseInt(r.getText()))
                            .gMultiplier(Integer.parseInt(g.getText()))
                            .bMultiplier(Integer.parseInt(b.getText()))
                            .numberOfIteration(Integer.parseInt(numberOfIteration.getText()))
                            .xOffset(Double.parseDouble(xOffset.getText()))
                            .yOffset(Double.parseDouble(yOffset.getText()))
                            .zoom(Double.parseDouble(zoom.getText()))
                            .zoomMultiplier(Double.parseDouble(zoomMultiplier.getText()))
                            .build()));
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateTextFields(Settings settings) {
            re.setText(String.valueOf(settings.getComplexNumber().getRe()));
            im.setText(String.valueOf(settings.getComplexNumber().getIm()));
            moveDelta.setText(String.valueOf(settings.getMoveDelta()));
            numberOfIteration.setText(String.valueOf(settings.getNumberOfIteration()));
            r.setText(String.valueOf(settings.getRMultiplier()));
            g.setText(String.valueOf(settings.getGMultiplier()));
            b.setText(String.valueOf(settings.getBMultiplier()));
            xOffset.setText(String.valueOf(settings.getXOffset()));
            yOffset.setText(String.valueOf(settings.getYOffset()));
            zoom.setText(String.valueOf(settings.getZoom()));
            zoomMultiplier.setText(String.valueOf(settings.getZoomMultiplier()));
    }
}
