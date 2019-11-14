package com.zoraw.fractal;

import lombok.Getter;

@Getter
public class FractalExplorer {

    private int xOffset = 0;
    private int yOffset = 0;
    private int zoom = 1;

    public void addOffset(int xOffset, int yOffset) {
        this.xOffset += xOffset;
        this.yOffset += yOffset;
    }
}
