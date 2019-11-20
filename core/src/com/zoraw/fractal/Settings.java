package com.zoraw.fractal;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class Settings {

    private final int numberOfIteration;
    private final int rMultiplier;
    private final int gMultiplier;
    private final int bMultiplier;

    private ComplexNumber complexNumber;
    private final double moveDelta;
    private double xOffset;
    private double yOffset;
    private double zoom;
    private double zoomMultiplier;

    public static Settings getInitialSettings() {
        return Settings.builder()
                .numberOfIteration(3000)
                .rMultiplier(0)
                .gMultiplier(1)
                .bMultiplier(13)
                .complexNumber(new ComplexNumber(-0.7, 0.27015))
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

    public void moveJulia(Direction direction) {
        switch (direction) {
            case UP:
                complexNumber = this.complexNumber.move(0, moveDelta);
                break;
            case DOWN:
                complexNumber = this.complexNumber.move(0, -moveDelta);
                break;
            case LEFT:
                complexNumber = this.complexNumber.move(-moveDelta, 0);
                break;
            case RIGHT:
                complexNumber = this.complexNumber.move(moveDelta, 0);
                break;
        }
    }
}
