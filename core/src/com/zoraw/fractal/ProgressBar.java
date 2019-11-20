package com.zoraw.fractal;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
public class ProgressBar {

    private volatile float progress = 0;
    private AtomicBoolean visible = new AtomicBoolean(false);
}
