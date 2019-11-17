package com.zoraw.fractal;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FractalInputProcessor implements InputProcessor {

    private final FractalView fractalView;
    private int initX;
    private int initY;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                this.fractalView.move(0.05, 0);
                break;
            case Input.Keys.RIGHT:
                this.fractalView.move(-0.05, 0);
                break;
            case Input.Keys.UP:
                this.fractalView.move(0, 0.05);
                break;
            case Input.Keys.DOWN:
                this.fractalView.move(0, -0.05);
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
        this.fractalView.zoom(amount);
        return false;
    }
}
