package com.zoraw.fractal.mandelbrotset.settings;

import com.zoraw.fractal.common.Direction;
import com.zoraw.fractal.common.Zoom;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class Settings {

    private final int numberOfIteration;
    private final int rMultiplier;
    private final int gMultiplier;
    private final int bMultiplier;

    private final double moveDelta;
    private double xOffset;
    private double yOffset;
    private double zoom;
    private double zoomMultiplier;

    public static Settings getInitialSettings() {
        return Settings.builder()
                .numberOfIteration(30)
                .rMultiplier(0)
                .gMultiplier(1)
                .bMultiplier(13)
                .moveDelta(0.02)
                .xOffset(0)
                .yOffset(0)
                .zoom(1)
                .zoomMultiplier(2)
                .build();
    }

    public Settings copy () {
        return this.toBuilder().build();
    }

    public void zoom(Zoom zoom) {
        if (zoom == Zoom.IN) {
            this.zoom *= zoomMultiplier;
        } else
            this.zoom /= zoomMultiplier;
    }


    public void addOffset(Direction direction) {
        double offsetDelta = 0.2 / zoom;
        switch (direction) {
            case UP:
                yOffset += offsetDelta;
                break;
            case DOWN:
                yOffset -= offsetDelta;
                break;
            case LEFT:
                xOffset += offsetDelta;
                break;
            case RIGHT:
                xOffset -= offsetDelta;
                break;
        }
    }

    public void move(int screenX, int screenY, int fractalWidth, int fractalHeight) {
        double x = 1.5 * (((double) screenX * 2 / fractalWidth) - 1) / this.zoom;
        double y = (((double) screenY * 2 / fractalHeight) - 1) / this.zoom;
        xOffset -= x;
        yOffset -= y;
    }
}
