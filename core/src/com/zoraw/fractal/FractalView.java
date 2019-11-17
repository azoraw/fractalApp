package com.zoraw.fractal;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.time.LocalDateTime;
import java.util.zip.Deflater;

public class FractalView extends Group implements EventListener {

    private final int FRACTAL_WIDTH;
    private final int FRACTAL_HEIGHT;
    private Pixmap pixmap;
    private Settings settings;
    private Sprite sprite;

    public FractalView(ScreenViewport viewport) {
        this.addListener(this);
        this.FRACTAL_WIDTH = viewport.getScreenWidth();
        this.FRACTAL_HEIGHT = viewport.getScreenHeight();
        settings = Settings.INITIAL_SETTINGS;
        pixmap = createPixelMap();
        updateFractal();
        setBounds(sprite.getX(), sprite.getY(), FRACTAL_WIDTH, FRACTAL_HEIGHT);
        this.setWidth(FRACTAL_WIDTH);
        this.setHeight(FRACTAL_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        this.drawChildren(batch, parentAlpha);
    }


    private Pixmap createPixelMap() {
        pixmap = new Pixmap(FRACTAL_WIDTH, FRACTAL_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        pixmap.setBlending(Pixmap.Blending.None);

        double cRe = settings.getComplexNumber().getRe();
        double cIm = settings.getComplexNumber().getIm();

        double prevRe = 0;
        double prevIm = 0;
        int xOffset = settings.getXOffset();
        int yOffset = settings.getYOffset();
        double zoom = settings.getZoom();

        for (int x = 0; x < FRACTAL_WIDTH; x++) {
            for (int y = 0; y < FRACTAL_HEIGHT; y++) {
                double nextRe = 1.5 * ((x - xOffset) - (double) FRACTAL_WIDTH / 2) / (FRACTAL_WIDTH * 0.5 * zoom);
                double nextIm = (y - yOffset - (double) FRACTAL_HEIGHT / 2) / (FRACTAL_HEIGHT * 0.5 * zoom);
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

    private float getRgbPart(Settings settings, int p, int multiplier) {
        float v = (float) (p * multiplier) / settings.getNumberOfIteration();
        return v - (int) v;
    }

    public void saveToFile() {
        pixmap.setFilter(Pixmap.Filter.NearestNeighbour);
        pixmap.setColor(Color.BLACK);
        PixmapIO.writePNG(new FileHandle(getFileName()), pixmap, Deflater.NO_COMPRESSION, false);
    }

    private String getFileName() {
        return LocalDateTime.now().toString().replace(":", "_") +
                "re" + settings.getComplexNumber().getRe() +
                "im" + settings.getComplexNumber().getIm() +
                "iteration" + settings.getNumberOfIteration() +
                "r" + settings.getRMultiplier() +
                "g" + settings.getGMultiplier() +
                "b" + settings.getBMultiplier() +
                ".png";
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof SettingsChangeEvent) {
            settings = ((SettingsChangeEvent) event).getSettings();
            updateFractal();
            return true;
        }
        if (event instanceof SaveButtonEvent) {
            saveToFile();
            return true;
        }
        return false;
    }

    private void updateFractal() {
        sprite = new Sprite(new Texture(createPixelMap()));
    }

    public void move(int x, int y) {
        settings.addOffset(x, y);
        SettingsTable settingsTable = (SettingsTable) this.getChild(0);
        settingsTable.updateTextFields(settings);
        updateFractal();

    }

    public void zoom(int amount) {
        this.settings.addZoom(amount);
        SettingsTable settingsTable = (SettingsTable) this.getChild(0);
        settingsTable.updateTextFields(settings);
        updateFractal();
    }
}
