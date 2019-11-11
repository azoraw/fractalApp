package com.zoraw.fractal;

public class Settings {

    private final ComplexNumber initNumber;
    private final int numberOfIteration;
    private final int rMultiplier;
    private final int gMultiplier;
    private final int bMultiplier;

    public Settings(ComplexNumber initNumber, int numberOfIteration, int rMultiplier, int gMultiplier, int bMultiplier) {
        this.initNumber = initNumber;
        this.numberOfIteration = numberOfIteration;
        this.rMultiplier = rMultiplier;
        this.gMultiplier = gMultiplier;
        this.bMultiplier = bMultiplier;
    }

    public ComplexNumber getInitNumber() {
        return initNumber;
    }

    public int getNumberOfIteration() {
        return numberOfIteration;
    }

    public int getrMultiplier() {
        return rMultiplier;
    }

    public int getgMultiplier() {
        return gMultiplier;
    }

    public int getbMultiplier() {
        return bMultiplier;
    }


}
