package com.zoraw.fractal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FractalView extends Group implements EventListener {

    private final int FRACTAL_WIDTH;
    private final int FRACTAL_HEIGHT;

    Sprite sprite;

    public FractalView(ScreenViewport viewport) {
        this.addListener(this);
        this.FRACTAL_WIDTH = viewport.getScreenWidth();
        this.FRACTAL_HEIGHT = viewport.getScreenHeight();
        Settings initSettings = new Settings(new ComplexNumber(-0.7, 0.27015), 300, 1, 11, 13);
        sprite = new Sprite(new Texture(createPixelMap(initSettings)));
        setBounds(sprite.getX(), sprite.getY(), FRACTAL_WIDTH, FRACTAL_HEIGHT);
        this.setWidth(FRACTAL_WIDTH);
        this.setHeight(FRACTAL_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        this.drawChildren(batch, parentAlpha);
    }


    private Pixmap createPixelMap(Settings settings) {
        Pixmap pmap = new Pixmap(FRACTAL_WIDTH, FRACTAL_HEIGHT, Pixmap.Format.RGBA8888);
        pmap.setColor(Color.WHITE);
        pmap.fill();
        pmap.setBlending(Pixmap.Blending.None);

        double cRe = settings.getInitNumber().getRe();
        double cIm = settings.getInitNumber().getIm();

        double prevRe = 0;
        double prevIm = 0;

        for (int x = 0; x < FRACTAL_WIDTH; x++) {
            for (int y = 0; y < FRACTAL_HEIGHT; y++) {
                double nextRe = 1.5 * (x - (double) FRACTAL_WIDTH / 2) / (FRACTAL_WIDTH * 0.5);
                double nextIm = (y - (double) FRACTAL_HEIGHT / 2) / (FRACTAL_HEIGHT * 0.5);
                int p;
                for (p = 0; p < settings.getNumberOfIteration(); p++) {
                    prevRe = nextRe;
                    prevIm = nextIm;
                    nextRe = prevRe * prevRe - prevIm * prevIm + cRe;
                    nextIm = 2 * prevRe * prevIm + cIm;
                    if ((nextRe * nextRe + nextIm * nextIm) > 4) {
                        break;
                    }

                }
                pmap.drawPixel(x, y, Color.toIntBits(p * settings.getRMultiplier() % 255, p * settings.getGMultiplier() % 255, p * settings.getBMultiplier() % 255, p == settings.getNumberOfIteration() ? 0 : 255));
            }
        }
        return pmap;
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof SettingsChangeEvent) {
            SettingsChangeEvent settingsEvent = (SettingsChangeEvent) event;
            sprite = new Sprite(new Texture(createPixelMap(settingsEvent.getSettings())));
            return true;
        }
        return false;
    }
}
