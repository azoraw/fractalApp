package com.zoraw.fractal;

import lombok.Getter;

@Getter
public class FractalExplorer {

    private int xOffset = 0;
    private int yOffset = 0;
    private double zoom = 1;

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
