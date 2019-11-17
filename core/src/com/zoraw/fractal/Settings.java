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

    public void addZoom(int amount) {
        if (amount > 0) {
            this.zoom /= 2;
        } else {
            this.zoom *= 2;
        }
    }

    public void addOffset(Direction direction) {
        double offsetDelta = 0.2 / zoom;
        switch (direction) {
            case UP:
                this.yOffset += offsetDelta;
                break;
            case DOWN:
                this.yOffset -= offsetDelta;
                break;
            case LEFT:
                xOffset += offsetDelta;
                break;
            case RIGHT:
                xOffset -= offsetDelta;
                break;
        }
    }
}
