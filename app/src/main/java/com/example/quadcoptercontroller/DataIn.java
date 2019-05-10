package com.example.quadcoptercontroller;

public class DataIn {

    private int outputType;
    private boolean powerOn;
    private int speed;

    public DataIn(int outputType, boolean powerOn, int speed) {
        this.outputType = outputType;
        this.powerOn = powerOn;
        this.speed = speed;
    }

    int getOutputType() {
        return outputType;
    }

    boolean isPowerOn() {
        return powerOn;
    }

    public int getSpeed() {
        return speed;
    }
}
