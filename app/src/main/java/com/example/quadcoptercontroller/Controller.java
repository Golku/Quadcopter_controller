package com.example.quadcoptercontroller;

import android.graphics.Color;
import android.util.Log;

import com.google.gson.Gson;

public class Controller implements ControllerCallback {

    private final String logTag = "logTag";

    private WebsocketClient websocketClient;
    private ViewCallback view;
    private Gson gson;

    private boolean powerOn;
    private boolean pidSwitch;
    private boolean settingTargetVal;

    private int targetVal;

    private long previousMessageTime;
    private long messagesInterval;
    private int messageCount;

    Controller(ViewCallback view) {
        this.view = view;
        gson = new Gson();
        powerOn = false;
        pidSwitch = false;
        settingTargetVal = false;
        previousMessageTime = 0;
        messagesInterval = 0;
        messageCount = 0;
        targetVal = 0;
        websocketClient = new WebsocketClient(this);
    }

    void onConnectionBtn(){
        if(websocketClient.isConnected()){
            websocketClient.closeConnection();
        }else{
            websocketClient.openConnection();
        }
    }

    void onPowerBtn() {
        powerOn = !powerOn;
        view.changePowerBtn(powerOn);
        DataOut dataOut = new DataOut(1, powerOn);
        sendData(dataOut);
    }

    void onPidSwitch(boolean switchState){
        pidSwitch = switchState;
        DataOut dataOut = new DataOut(2, switchState);
        sendData(dataOut);
    }

    void setTargetVal(int val){
        targetVal = val;
        if(!pidSwitch) {
            settingTargetVal = true;
            view.changeMotorBar(val, val, val, val);
        }
        settingTargetVal = false;
        DataOut dataOut = new DataOut(3, 4, val);
        sendData(dataOut);
    }

    void onMotorOutputChange(int motorIndex, int motorVal){
        if(settingTargetVal){
            return;
        }
        DataOut dataOut = new DataOut(3, motorIndex, motorVal);
        sendData(dataOut);
    }

    private void sendData(DataOut dataOut){
        if(!websocketClient.isConnected()){
            view.makeToast("Not connected");
            return;
        }
        String json = gson.toJson(dataOut);
        //Log.d(logTag, "json send: " + json);
        websocketClient.sendData(json);
    }

    boolean getPidSwitchState() {
        return pidSwitch;
    }

    private void getResponseTime(){

        messageCount++;

        if(previousMessageTime == 0){
            previousMessageTime = System.currentTimeMillis();
        }else{
            messagesInterval = System.currentTimeMillis() - previousMessageTime;
            previousMessageTime = System.currentTimeMillis();
            Log.d(logTag,"Message count: "+ messageCount+" Milliseconds since last message: "+ messagesInterval);
        }
    }

    @Override
    public void connectionEstablished() {
        view.changeConnectionText("connected", Color.GREEN);
        view.changeConnectionBtn(websocketClient.isConnected());
    }

    @Override
    public void connectionClosed() {
        view.changeConnectionText("disconnected", Color.RED);
        view.changeConnectionBtn(websocketClient.isConnected());
    }

    @Override
    public void onConnectionError() {
        view.makeToast("Connection error");
    }

    @Override
    public void serverResponse(String jsonIn) {
        //Log.d("logTag", "message from server: "+jsonIn);

        getResponseTime();

        DataIn dataIn = gson.fromJson(jsonIn, DataIn.class);

        switch (dataIn.getOutputType()){
            case 1:
                view.changeMotorBar(
                        dataIn.getMotorFLVal(),
                        dataIn.getMotorFRVal(),
                        dataIn.getMotorBLVal(),
                        dataIn.getMotorBRVal()
                );
                break;
            case 2:
                break;
            default:
                break;
        }
    }
}
