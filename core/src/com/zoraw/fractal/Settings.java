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

    private int xOffset;
    private int yOffset;
    private double zoom;

    public static final Settings INITIAL_SETTINGS = Settings.builder()
            .complexNumber(new ComplexNumber(-0.7, 0.27015))
            .numberOfIteration(300)
            .rMultiplier(1)
            .gMultiplier(1)
            .bMultiplier(1)
            .xOffset(0)
            .yOffset(0)
            .zoom(1)
            .build();

    public void addOffset(int xOffset, int yOffset) {
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
