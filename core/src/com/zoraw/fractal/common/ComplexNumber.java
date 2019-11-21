package com.zoraw.fractal.common;

import lombok.Value;

@Value
public class ComplexNumber {

    private final double re;
    private final double im;

    public ComplexNumber move(double re, double im) {
        return new ComplexNumber(this.re + re, this.im + im);
    }

}
