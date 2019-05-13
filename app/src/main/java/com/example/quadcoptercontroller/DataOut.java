package com.example.quadcoptercontroller;

class DataOut {

    private int commandType;
    private boolean powerOn;
    private boolean pidOn;

    private int motorIndex;
    private int motorVal;

    DataOut(int commandType, boolean bool) {
        this.commandType = commandType;

        switch (commandType){
            case 1:
                this.powerOn = bool;
                break;
            case 2:
                this.pidOn = bool;
                break;
        }
    }

    DataOut(int commandType, int motorIndex, int motorVal) {
        this.commandType = commandType;
        this.motorIndex = motorIndex;
        this.motorVal = motorVal;
    }
}
