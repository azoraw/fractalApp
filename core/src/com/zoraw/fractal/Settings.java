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

    public void addOffset(double xOffset, double yOffset) {
        this.xOffset += xOffset;
        this.yOffset += yOffset;
    }

    public void addZoom(int amount) {
        if (amount > 0) {
            this.zoom /= 2;
        } else {
            this.zoom *= 2;
        }
    }
}
