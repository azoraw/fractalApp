package com.zoraw.fractal.mandelbrotset;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.zoraw.fractal.common.Direction;
import com.zoraw.fractal.common.Zoom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MandelbrotSetInputProcessor implements InputProcessor {

    private final MandelbrotSet mandelbrotset;

    private boolean ctrlPressed = false;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                if (ctrlPressed) {
                } else {
                    this.mandelbrotset.moveCamera(Direction.LEFT);
                }
                break;
            case Input.Keys.RIGHT:
                if (ctrlPressed) {
                } else {
                    this.mandelbrotset.moveCamera(Direction.RIGHT);
                }
                break;
            case Input.Keys.UP:
                if (ctrlPressed) {
                } else {
                    this.mandelbrotset.moveCamera(Direction.UP);
                }
                break;
            case Input.Keys.DOWN:
                if (ctrlPressed) {
                } else {
                    this.mandelbrotset.moveCamera(Direction.DOWN);
                }
                break;
            case Input.Keys.CONTROL_LEFT:
            case Input.Keys.CONTROL_RIGHT:
                ctrlPressed = true;
            default:
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT) {
            ctrlPressed = false;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            this.mandelbrotset.moveCameraAndZoom(screenX, screenY, Zoom.IN);
        } else if (button == Input.Buttons.RIGHT) {
            this.mandelbrotset.moveCameraAndZoom(screenX, screenY, Zoom.OUT);
        } else if (button == Input.Buttons.MIDDLE) {
            this.mandelbrotset.moveCamera(screenX, screenY);
        }
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
        this.mandelbrotset.zoom(amount < 0 ? Zoom.IN : Zoom.OUT);
        return false;
    }
}
