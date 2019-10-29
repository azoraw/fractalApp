package com.zoraw.fractal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FractalApp extends ApplicationAdapter {

    private static final int FRACTAL_WIDTH = 1500;
    private static final int FRACTAL_HEIGHT = 900;

    private static final int MAX_ITERATION = 600;

    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
    }

    @Override
    public void render() {

        Pixmap pmap = createPixelMap();
        batch.begin();
        batch.draw(new Texture(pmap), 0, 0);
        batch.end();
    }

    private Pixmap createPixelMap() {
        Pixmap pmap = new Pixmap(FRACTAL_WIDTH, FRACTAL_HEIGHT, Pixmap.Format.RGBA8888);
        pmap.setColor(Color.WHITE);
        pmap.fill();
        pmap.setBlending(Pixmap.Blending.None);

        for (int x = 0; x < FRACTAL_WIDTH; x++) {
            for (int y = 0; y < FRACTAL_HEIGHT; y++) {
                double[] normalizedPosition = normalizePosition(x, y);
                double re = normalizedPosition[0];
                double im = normalizedPosition[1];

                double prevRe = 0;
                double prevIm = 0;
                double nextRe, nextIm;
                int p;
                for (p = 0; p < MAX_ITERATION; p++) {
                    nextRe = prevRe * prevRe - prevIm * prevIm + re;
                    nextIm = 2 * prevRe * prevIm + im;
                    if (nextRe * nextRe + nextIm * nextIm > 4) {
                        break;
                    }
                    prevRe = nextRe;
                    prevIm = nextIm;
                }
                pmap.drawPixel(x, y, Color.rgba8888(p / MAX_ITERATION, p * 11 / MAX_ITERATION, p * 12 / MAX_ITERATION, 1));
            }
        }
        return pmap;
    }

    private double[] normalizePosition(int x, int y) {
        double[] normalizedPosition = new double[2];
        normalizedPosition[0] = (x - (double) FRACTAL_WIDTH / 2) / (double) FRACTAL_WIDTH * 4;
        normalizedPosition[1] = (y - (double) FRACTAL_HEIGHT / 2) / (double) FRACTAL_HEIGHT * 2;
        return normalizedPosition;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
