package com.zoraw.fractal;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Settings {

    private final ComplexNumber complexNumber;
    private final int numberOfIteration;
    private final int rMultiplier;
    private final int gMultiplier;
    private final int bMultiplier;

    public static final Settings INITIAL_SETTINGS = Settings.builder()
            .complexNumber(new ComplexNumber(-0.7, 0.27015))
            .numberOfIteration(300)
            .rMultiplier(1)
            .gMultiplier(1)
            .bMultiplier(1)
            .build();
}
