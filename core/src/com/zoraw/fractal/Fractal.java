package com.zoraw.fractal;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zoraw.fractal.common.FractalActor;
import com.zoraw.fractal.juliaset.JuliaSet;
import com.zoraw.fractal.mandelbrotset.MandelbrotSet;
import com.zoraw.fractal.multibroset.MultibrotSet;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Function;

@AllArgsConstructor
@Getter
public enum Fractal {
    JULIA_SET("Julia set", JuliaSet::new),
    MANDELBROT_SET("Mandelbrot set", MandelbrotSet::new),
    MULTIBROT_SET("Multibrot set", MultibrotSet::new);

    private final String fractalName;
    private final Function<Viewport, FractalActor> supplier;

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

    public FractalActor createInstance(Viewport viewport) {
        return supplier.apply(viewport);
    }
}
