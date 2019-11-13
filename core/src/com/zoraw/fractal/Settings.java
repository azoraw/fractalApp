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

}
