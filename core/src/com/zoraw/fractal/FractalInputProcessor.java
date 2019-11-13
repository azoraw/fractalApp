package com.zoraw.fractal;

import com.badlogic.gdx.InputProcessor;
import lombok.Getter;

@Getter
public class FractalInputProcessor implements InputProcessor {

    private int xOffset = 0;
    private int yOffset = 200;
    private int zoom = 1;

    public void addOffset(int xOffset, int yOffset) {
        this.xOffset += xOffset;
        this.yOffset += yOffset;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
