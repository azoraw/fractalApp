package com.zoraw.fractal.juliaset.settings.animation;

import com.badlogic.gdx.scenes.scene2d.Event;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
@Builder
public class AnimateButtonEvent extends Event {

    private final int width;
    private final int height;
    private final double numberOfFrames;

}
