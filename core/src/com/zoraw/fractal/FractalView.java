package com.zoraw.fractal;

import com.badlogic.gdx.Gdx;
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
    private ProgressBarActor progressBar = new ProgressBarActor();

    public FractalView(ScreenViewport viewport) {
        this.addListener(this);
        this.FRACTAL_WIDTH = viewport.getScreenWidth();
        this.FRACTAL_HEIGHT = viewport.getScreenHeight();
        settings = Settings.getInitialSettings();
        updatePixMap(FRACTAL_WIDTH, FRACTAL_HEIGHT);
        sprite = new Sprite(new Texture(pixmap));
        setBounds(sprite.getX(), sprite.getY(), FRACTAL_WIDTH, FRACTAL_HEIGHT);
        this.setWidth(FRACTAL_WIDTH);
        this.setHeight(FRACTAL_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        this.drawChildren(batch, parentAlpha);
    }

    private void updatePixMap(int width, int height) {
        Settings tmpSettings = settings.copy();
        Pixmap tmpPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        tmpPixmap.setColor(Color.BLACK);
        tmpPixmap.fill();
        tmpPixmap.setBlending(Pixmap.Blending.None);

        double cRe = tmpSettings.getComplexNumber().getRe();
        double cIm = tmpSettings.getComplexNumber().getIm();

        double prevRe = 0;
        double prevIm = 0;
        double xOffset = tmpSettings.getXOffset();
        double yOffset = tmpSettings.getYOffset();
        double zoom = tmpSettings.getZoom();
        float percent = width * (float) height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                progressBar.getProgressBar().setValue((x * height + y) / percent);
                double nextRe = 1.5 * (x - (double) width / 2) / (width * 0.5 * zoom) - xOffset;
                double nextIm = (y - (double) height / 2) / (height * 0.5 * zoom) - yOffset;
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
        drawDebugCircle(tmpPixmap);
        this.pixmap = tmpPixmap;
    }

    private void drawDebugCircle(Pixmap tmpPixmap) {
        tmpPixmap.setColor(Color.RED);
        tmpPixmap.drawCircle(FRACTAL_WIDTH / 2, FRACTAL_HEIGHT / 2, 50);
        tmpPixmap.drawCircle(FRACTAL_WIDTH / 2, FRACTAL_HEIGHT / 2, 5);
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
            SaveButtonEvent saveButtonEvent = (SaveButtonEvent) event;
            saveFractal(saveButtonEvent.getWidth(), saveButtonEvent.getHeight());
            return true;
        }
        return false;
    }

    private void updateFractal() {
        this.addActor(progressBar.getProgressBar());
        new Thread(() -> {
            updatePixMap(FRACTAL_WIDTH, FRACTAL_HEIGHT);
            Gdx.app.postRunnable(() -> {
                this.removeActor(progressBar.getProgressBar());
                progressBar.getProgressBar().setValue(0f);
                sprite = new Sprite(new Texture(pixmap));
            });
        }).start();
    }

    private void saveFractal(int width, int height) {
        this.addActor(progressBar.getProgressBar());
        new Thread(() -> {
            updatePixMap(width, height);
            Gdx.app.postRunnable(() -> {
                this.removeActor(progressBar.getProgressBar());
                progressBar.getProgressBar().setValue(0f);
                saveToFile();
            });
        }).start();
    }

    public void zoom(Zoom zoom) {
        this.settings.zoom(zoom);
        updateSettingTable();
        updateFractal();
    }

    public void moveAndZoom(int screenX, int screenY, Zoom zoom) {
        settings.move(screenX, screenY, FRACTAL_WIDTH, FRACTAL_HEIGHT);
        settings.zoom(zoom);
        updateSettingTable();
        updateFractal();
    }

    private void updateSettingTable() {
        SettingsTable settingsTable = (SettingsTable) this.getChild(0);
        settingsTable.updateTextFields(settings);
    }

    public void move(Direction direction) {
        settings.addOffset(direction);
        updateSettingTable();
        updateFractal();
    }

    public void move(int screenX, int screenY) {
        settings.move(screenX, screenY, FRACTAL_WIDTH, FRACTAL_HEIGHT);
        updateSettingTable();
        updateFractal();
    }

    public void moveJulia(Direction left) {
        settings.moveJulia(left);
        updateSettingTable();
        updateFractal();
    }
}
