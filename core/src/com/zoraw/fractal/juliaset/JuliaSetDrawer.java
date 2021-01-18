package com.zoraw.fractal.juliaset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.zoraw.fractal.juliaset.settings.Settings;
import lombok.Builder;

@Builder
public class JuliaSetDrawer {

    private final int FRACTAL_WIDTH;
    private final int FRACTAL_HEIGHT;
    public float progress;

    Pixmap getPixMap(Settings settings) {
        Pixmap pixmap = new Pixmap(FRACTAL_WIDTH, FRACTAL_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        pixmap.setBlending(Pixmap.Blending.None);

        double cRe = settings.getComplexNumber().getRe();
        double cIm = settings.getComplexNumber().getIm();

        double prevRe;
        double prevIm;
        double xOffset = settings.getXOffset();
        double yOffset = settings.getYOffset();
        double zoom = settings.getZoom();

        for (int x = 0; x < FRACTAL_WIDTH; x++) {
            for (int y = 0; y < FRACTAL_HEIGHT; y++) {
                updateProgress(x, y);
                double nextRe = FRACTAL_WIDTH / (float) FRACTAL_HEIGHT * (x - (double) FRACTAL_WIDTH / 2) / (FRACTAL_WIDTH * 0.5 * zoom) - xOffset;
                double nextIm = (y - (double) FRACTAL_HEIGHT / 2) / (FRACTAL_HEIGHT * 0.5 * zoom) - yOffset;
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
                int color = Color.rgba8888(getRgbPart(settings, p, settings.getRMultiplier()),
                        getRgbPart(settings, p, settings.getGMultiplier()),
                        getRgbPart(settings, p, settings.getBMultiplier()),
                        1);
                if (p == 0) {
                    color = Color.rgba8888(Color.BLACK);
                }
                pixmap.drawPixel(x, y, color);
            }
        }
        return pixmap;
    }

    private void updateProgress(int x, int y) {
        progress = (x * FRACTAL_HEIGHT + y) / (FRACTAL_WIDTH * (float) FRACTAL_HEIGHT);
    }

    private float getRgbPart(Settings settings, int p, int multiplier) {
        float v = (float) (p * multiplier) / settings.getNumberOfIteration();
        return v - (int) v;
    }
}
