package com.zoraw.fractal.juliaset;

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
import com.zoraw.fractal.juliaset.settings.*;
import com.zoraw.fractal.juliaset.settings.animation.AnimateButtonEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Deflater;

public class JuliaSet extends FractalActor implements EventListener {

    private final int FRACTAL_WIDTH;
    private final int FRACTAL_HEIGHT;
    private final JuliaSetDrawer juliaSetDrawer;
    private final ProgressBarActor progressBarFractalActor = new ProgressBarActor(10, 30, 1000, 200);
    private final ProgressBarActor progressBarAnimationActor = new ProgressBarActor(10, 10, 1000, 200);

    private Settings settings;
    private Sprite sprite;
    private int animationCounter = 0;
    private float animationProgress;

    public JuliaSet(Viewport viewport) {
        this.addListener(this);
        FRACTAL_WIDTH = viewport.getScreenWidth();
        FRACTAL_HEIGHT = viewport.getScreenHeight();

        juliaSetDrawer = JuliaSetDrawer.builder()
                .FRACTAL_WIDTH(FRACTAL_WIDTH)
                .FRACTAL_HEIGHT(FRACTAL_HEIGHT)
                .build();

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
        if (progressBarFractalActor.isShown()) {
            progressBarFractalActor.getProgressBar().setValue(juliaSetDrawer.progress);
        }
        if (progressBarAnimationActor.isShown()) {
            progressBarAnimationActor.getProgressBar().setValue(animationProgress);
        }
        this.drawChildren(batch, parentAlpha);
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof SettingsChangeEvent) {
            settings = ((SettingsChangeEvent) event).getSettings();
            updateFractal();
            return true;
        }
        if (event instanceof SaveButtonEvent) {
            saveFractal();
            return true;
        }
        if (event instanceof AnimateButtonEvent) {
            AnimateButtonEvent animateButtonEvent = (AnimateButtonEvent) event;
            animateFractal(animateButtonEvent);
            return true;
        }
        return false;
    }

    private void animateFractal(AnimateButtonEvent animateButtonEvent) {
        if (!progressBarAnimationActor.isShown()) {
            animationCounter = 0;
            addProgressBarActor(progressBarAnimationActor);
            addProgressBarActor(progressBarFractalActor);
            new Thread(() -> {
                String animationDir = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date());
                animateInDirection(animateButtonEvent, Direction.RIGHT, animationDir);
                animateInDirection(animateButtonEvent, Direction.DOWN, animationDir);
                animateInDirection(animateButtonEvent, Direction.LEFT, animationDir);
                animateInDirection(animateButtonEvent, Direction.UP, animationDir);
                Gdx.app.postRunnable(() -> {
                    removeProgressBarActor(progressBarAnimationActor);
                    removeProgressBarActor(progressBarFractalActor);
                });
            }).start();
        }
    }

    private void animateInDirection(AnimateButtonEvent animateButtonEvent, Direction direction, String animationDir) {
        for (int i = 0; i < animateButtonEvent.getNumberOfFrames(); i++) {
            animationProgress = animationCounter / (float) (animateButtonEvent.getNumberOfFrames() * 4);
            Pixmap pixmap = juliaSetDrawer.getPixMap(settings.copy());
            saveToFile(pixmap, animationDir);
            pixmap.dispose();
            settings.moveJulia(direction);
        }
    }

    private void saveToFile(Pixmap pixmap, String animationDir) {
        pixmap.setFilter(Pixmap.Filter.NearestNeighbour);
        pixmap.setColor(Color.BLACK);
        PixmapIO.writePNG(new FileHandle(getFileName(animationDir)), pixmap, Deflater.NO_COMPRESSION, false);
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

    public void moveSet(Direction left) {
        settings.moveJulia(left);
        updateSettingTable();
        updateFractal();
    }

    private void updateFractal() {
        if (!progressBarFractalActor.isShown()) {
            addProgressBarActor(progressBarFractalActor);
            new Thread(() -> {
                Pixmap pixmap = juliaSetDrawer.getPixMap(settings.copy());
                Gdx.app.postRunnable(() -> {
                    sprite = new Sprite(new Texture(pixmap));
                    pixmap.dispose();
                    removeProgressBarActor(progressBarFractalActor);
                });
            }).start();
        }
    }

    private void saveFractal() {
        if (!progressBarFractalActor.isShown()) {
            addProgressBarActor(progressBarFractalActor);
            new Thread(() -> {
                Pixmap pixmap = juliaSetDrawer.getPixMap(settings.copy());
                Gdx.app.postRunnable(() -> {
                    saveToFile(pixmap, "out");
                    pixmap.dispose();
                    removeProgressBarActor(progressBarFractalActor);
                });
            }).start();
        }
    }

    private void addProgressBarActor(ProgressBarActor progressBarActor) {
        progressBarActor.setShown(true);
        this.addActor(progressBarActor.getProgressBar());
    }

    private void removeProgressBarActor(ProgressBarActor progressBarActor) {
        progressBarActor.setShown(false);
        this.removeActor(progressBarActor.getProgressBar());
    }

    private void updateSettingTable() {
        SettingsTable settingsTable = (SettingsTable) this.getChild(0);
        settingsTable.updateTextFields(settings);
    }

    private String getFileName(String animationDir) {
        return animationDir + "/" + "img-" + animationCounter++ + ".png";
    }

    @Override
    public InputProcessor getInputProcessor() {
        return new JuliaSetInputProcessor(this);
    }
}
