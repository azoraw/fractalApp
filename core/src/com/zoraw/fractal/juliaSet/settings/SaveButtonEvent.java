package com.zoraw.fractal.juliaSet.settings;

import com.badlogic.gdx.scenes.scene2d.Event;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
@Builder
public class SaveButtonEvent extends Event {

    private final int width;
    private final int height;

}
