package com.zoraw.fractal;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FractalInputProcessor implements InputProcessor {

    private final FractalView fractalView;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                this.fractalView.move(Direction.LEFT);
                break;
            case Input.Keys.RIGHT:
                this.fractalView.move(Direction.RIGHT);
                break;
            case Input.Keys.UP:
                this.fractalView.move(Direction.UP);
                break;
            case Input.Keys.DOWN:
                this.fractalView.move(Direction.DOWN);
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
        if (button == Input.Buttons.LEFT) {
            this.fractalView.moveAndZoom(screenX, screenY, Zoom.IN);
        } else if (button == Input.Buttons.RIGHT) {
            this.fractalView.moveAndZoom(screenX, screenY, Zoom.OUT);
        } else if(button == Input.Buttons.MIDDLE) {
            this.fractalView.move(screenX, screenY);
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
        this.fractalView.zoom(amount < 0 ? Zoom.IN : Zoom.OUT);
        return false;
    }
}
