package com.zoraw.fractal;

import com.badlogic.gdx.utils.Array;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Fractal {
    JULIA_SET("Julia set"),
    MANDELBROT_SET("Mandelbrot set");

    private final String fractalName;

    public static Fractal getInitial() {
        return JULIA_SET;
    }

    public static Array<String> getNames() {
        Array<String> output = new Array<>();
        Arrays.stream(Fractal.values())
                .forEach(fractal -> output.add(fractal.getFractalName()));
        return output;

    }

    public static Fractal fromString(String selectedFractal) {
        return Arrays.stream(Fractal.values())
                .filter(fractal -> fractal.getFractalName().equals(selectedFractal))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("no fractal for name: " + selectedFractal));
    }
}
