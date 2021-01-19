package com.zoraw.fractal.mandelbrotset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zoraw.fractal.common.Direction;
import com.zoraw.fractal.common.FractalActor;
import com.zoraw.fractal.common.ProgressBarActor;
import com.zoraw.fractal.common.Zoom;
import com.zoraw.fractal.mandelbrotset.settings.SaveButtonEvent;
import com.zoraw.fractal.mandelbrotset.settings.Settings;
import com.zoraw.fractal.mandelbrotset.settings.SettingsChangeEvent;
import com.zoraw.fractal.mandelbrotset.settings.SettingsTable;

import java.time.LocalDateTime;
import java.util.zip.Deflater;

public class MandelbrotSet extends FractalActor implements EventListener {

    private final int FRACTAL_WIDTH;
    private final int FRACTAL_HEIGHT;
    private Pixmap pixmap;
    private Settings settings;
    private Sprite sprite;
    private ProgressBarActor progressBar = new ProgressBarActor(10, 10, 1000, 200);

    public MandelbrotSet(Viewport viewport) {
        this.addListener(this);
        this.FRACTAL_WIDTH = viewport.getScreenWidth();
        this.FRACTAL_HEIGHT = viewport.getScreenHeight();
        settings = Settings.getInitialSettings();
        updateFractal();
        setBounds(0, 0, FRACTAL_WIDTH, FRACTAL_HEIGHT);
        this.setWidth(FRACTAL_WIDTH);
        this.setHeight(FRACTAL_HEIGHT);
        this.addActor(new SettingsTable(viewport));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (sprite != null) {
            sprite.draw(batch);
        }
        this.drawChildren(batch, parentAlpha);
    }

    private void updatePixMap(int width, int height) {
        Settings tmpSettings = settings.copy();
        Pixmap tmpPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        tmpPixmap.setColor(Color.BLACK);
        tmpPixmap.fill();
        tmpPixmap.setBlending(Pixmap.Blending.None);

        double xOffset = tmpSettings.getXOffset();
        double yOffset = tmpSettings.getYOffset();
        double zoom = tmpSettings.getZoom();
        float percent = width * (float) height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                progressBar.getProgressBar().setValue((x * height + y) / percent);
                double re = width / (float) height * (x - (double) width / 2) / (width * 0.5 * zoom) - xOffset;
                double im = (y - (double) height / 2) / (height * 0.5 * zoom) - yOffset;
                double prevRe = 0;
                double prevIm = 0;
                double nextRe, nextIm;
                int p;
                for (p = 0; p < tmpSettings.getNumberOfIteration(); p++) {
                    nextRe = prevRe * prevRe - prevIm * prevIm + re;
                    nextIm = 2 * prevRe * prevIm + im;
                    if (nextRe * nextRe + nextIm * nextIm > 4) {
                        break;
                    }
                    prevRe = nextRe;
                    prevIm = nextIm;
                }
                int color = Color.rgba8888(getRgbPart(tmpSettings, p, tmpSettings.getRMultiplier()),
                        getRgbPart(tmpSettings, p, tmpSettings.getGMultiplier()),
                        getRgbPart(tmpSettings, p, tmpSettings.getBMultiplier()),
                        1);
                if (p == 0) {
                    color = Color.rgba8888(Color.BLACK);
                }
                tmpPixmap.drawPixel(x, y, color);
            }
        }
        this.pixmap = tmpPixmap;
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

    @Override
    public boolean handle(Event event) {
        if (event instanceof SettingsChangeEvent) {
            settings = ((SettingsChangeEvent) event).getSettings();
            updateFractal();
            return true;
        }
        if (event instanceof SaveButtonEvent) {
            SaveButtonEvent saveButtonEvent = (SaveButtonEvent) event;
            saveFractal(saveButtonEvent.getWidth(), saveButtonEvent.getHeight());
            return true;
        }
        return false;
    }

    public void zoom(Zoom zoom) {
        this.settings.zoom(zoom);
        updateSettingTable();
        updateFractal();
    }

    public void moveCameraAndZoom(int screenX, int screenY, Zoom zoom) {
        settings.move(screenX, screenY, FRACTAL_WIDTH, FRACTAL_HEIGHT);
        settings.zoom(zoom);
        updateSettingTable();
        updateFractal();
    }

    public void moveCamera(Direction direction) {
        settings.addOffset(direction);
        updateSettingTable();
        updateFractal();
    }

    public void moveCamera(int screenX, int screenY) {
        settings.move(screenX, screenY, FRACTAL_WIDTH, FRACTAL_HEIGHT);
        updateSettingTable();
        updateFractal();
    }

    private void updateFractal() {
        if (!progressBar.isShown()) {
            progressBar.setShown(true);
            this.addActor(progressBar.getProgressBar());
            new Thread(() -> {
                updatePixMap(FRACTAL_WIDTH, FRACTAL_HEIGHT);
                Gdx.app.postRunnable(() -> {
                    this.removeActor(progressBar.getProgressBar());
                    progressBar.getProgressBar().setValue(0f);
                    sprite = new Sprite(new Texture(pixmap));
                    pixmap.dispose();
                    progressBar.setShown(false);
                });
            }).start();
        }
    }

    private void saveFractal(int width, int height) {
        if (!progressBar.isShown()) {
            progressBar.setShown(true);
            this.addActor(progressBar.getProgressBar());
            new Thread(() -> {
                updatePixMap(width, height);
                Gdx.app.postRunnable(() -> {
                    this.removeActor(progressBar.getProgressBar());
                    progressBar.getProgressBar().setValue(0f);
                    saveToFile();
                    progressBar.setShown(true);
                });
            }).start();
        }
    }

    private void updateSettingTable() {
        SettingsTable settingsTable = (SettingsTable) this.getChild(0);
        settingsTable.updateTextFields(settings);
    }

    private String getFileName() {
        return LocalDateTime.now().toString().replace(":", "_") +
                "iteration" + settings.getNumberOfIteration() +
                "r" + settings.getRMultiplier() +
                "g" + settings.getGMultiplier() +
                "b" + settings.getBMultiplier() +
                ".png";
    }

    @Override
    public InputProcessor getInputProcessor() {
        return new MandelbrotSetInputProcessor(this);
    }
}
