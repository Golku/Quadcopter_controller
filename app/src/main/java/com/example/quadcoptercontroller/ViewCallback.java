package com.example.quadcoptercontroller;

interface ViewCallback {

    void changeConnectionText(String text, int color);

    void changeMotorBar(int motor, int progress);

    void changeConnectionBtn(boolean connected);

    void changePowerBtn(boolean powerOn);

    void makeToast(String message);
}
