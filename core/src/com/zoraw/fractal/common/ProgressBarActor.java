package com.zoraw.fractal.common;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.*;

@Getter
public class ProgressBarActor {

    private ProgressBar progressBar = createProgressBar();

    @Setter
    private AtomicBoolean isShown = new AtomicBoolean(false);

    private ProgressBar createProgressBar() {
        ProgressBar progressBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, createSkin());
        progressBar.setValue(0f);
        progressBar.setAnimateDuration(0.5f);
        progressBar.setBounds(10, 10, 1000, 200);
        return progressBar;
    }

    private ProgressBarStyle createSkin() {
        Pixmap pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBarStyle progressBarStyle = new ProgressBarStyle();
        progressBarStyle.background = drawable;

        Pixmap pixmap2 = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.GRAY);
        pixmap2.fill();
        TextureRegionDrawable drawable1 =  new TextureRegionDrawable(new TextureRegion(new Texture(pixmap2)));
        pixmap2.dispose();

        progressBarStyle.knobBefore = drawable1;
        progressBarStyle.knobAfter = drawable;
        return progressBarStyle;
    }
}
