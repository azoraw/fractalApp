package com.zoraw.fractal.juliaset;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.zoraw.fractal.common.Direction;
import com.zoraw.fractal.common.Zoom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JuliaSetInputProcessor implements InputProcessor {

    private final JuliaSet juliaSet;

    private boolean ctrlPressed = false;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                if (ctrlPressed) {
                    this.juliaSet.moveSet(Direction.LEFT);
                } else {
                    this.juliaSet.moveCamera(Direction.LEFT);
                }
                break;
            case Input.Keys.RIGHT:
                if (ctrlPressed) {
                    this.juliaSet.moveSet(Direction.RIGHT);
                } else {
                    this.juliaSet.moveCamera(Direction.RIGHT);
                }
                break;
            case Input.Keys.UP:
                if (ctrlPressed) {
                    this.juliaSet.moveSet(Direction.UP);
                } else {
                    this.juliaSet.moveCamera(Direction.UP);
                }
                break;
            case Input.Keys.DOWN:
                if (ctrlPressed) {
                    this.juliaSet.moveSet(Direction.DOWN);
                } else {
                    this.juliaSet.moveCamera(Direction.DOWN);
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
            this.juliaSet.moveCameraAndZoom(screenX, screenY, Zoom.IN);
        } else if (button == Input.Buttons.RIGHT) {
            this.juliaSet.moveCameraAndZoom(screenX, screenY, Zoom.OUT);
        } else if (button == Input.Buttons.MIDDLE) {
            this.juliaSet.moveCamera(screenX, screenY);
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
        this.juliaSet.zoom(amount < 0 ? Zoom.IN : Zoom.OUT);
        return false;
    }
}
