package com.example.quadcoptercontroller;

public class DataIn {

    private int outputType;
    private int motorFLVal;
    private int motorFRVal;
    private int motorBLVal;
    private int motorBRVal;

    public DataIn(int outputType, int motorFLVal, int motorFRVal, int motorBLVal, int motorBRVal) {
        this.outputType = outputType;
        this.motorFLVal = motorFLVal;
        this.motorFRVal = motorFRVal;
        this.motorBLVal = motorBLVal;
        this.motorBRVal = motorBRVal;
    }

    public int getOutputType() {
        return outputType;
    }

    public int getMotorFLVal() {
        return motorFLVal;
    }

    public int getMotorFRVal() {
        return motorFRVal;
    }

    public int getMotorBLVal() {
        return motorBLVal;
    }

    public int getMotorBRVal() {
        return motorBRVal;
    }
}
