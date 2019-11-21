package com.zoraw.fractal.common;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class FractalActor extends Group {
    public abstract InputProcessor getInputProcessor();
}
