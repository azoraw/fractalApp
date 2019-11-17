package com.zoraw.fractal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Settings {

    private final ComplexNumber complexNumber;
    private final int numberOfIteration;
    private final int rMultiplier;
    private final int gMultiplier;
    private final int bMultiplier;

    private double xOffset;
    private double yOffset;
    private double zoom;

    public static final Settings INITIAL_SETTINGS = Settings.builder()
            .complexNumber(new ComplexNumber(-0.7, 0.27015))
            .numberOfIteration(3000)
            .rMultiplier(0)
            .gMultiplier(1)
            .bMultiplier(13)
            .xOffset(0)
            .yOffset(0)
            .zoom(1)
            .build();

    public void addZoom(Zoom zoom) {
        if (zoom == Zoom.IN) {
            this.zoom *= 2;
        } else
            this.zoom /= 2;
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

    public void moveAndZoom(int screenX, int screenY, int FRACTAL_WIDTH, int FRACTAL_HEIGHT, Zoom zoom) {
        double x = 1.5 * (((double) screenX * 2 / FRACTAL_WIDTH) - 1) / this.zoom;
        double y = (((double) screenY * 2 / FRACTAL_HEIGHT) - 1) / this.zoom;
        xOffset -= x;
        yOffset -= y;
        addZoom(zoom);
    }
}
