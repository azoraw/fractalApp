package com.zoraw.fractal;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FractalInputProcessor implements InputProcessor {

    private final FractalView fractalView;
    private boolean touched = false;
    private int initX;
    private int initY;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                this.fractalView.move(-50, 0);
                break;
            case Input.Keys.RIGHT:
                this.fractalView.move(50, 0);
                break;
            case Input.Keys.UP:
                this.fractalView.move(0, -50);
                break;
            case Input.Keys.DOWN:
                this.fractalView.move(0, 50);
                break;
            default:
        }
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
        this.touched = true;
        this.initX = screenX;
        this.initY = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.touched = false;
        int x = screenX - this.initX;
        int y = screenY - this.initY;
        this.fractalView.move(x, y);
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
        this.fractalView.zoom(amount);
        return false;
    }
}
