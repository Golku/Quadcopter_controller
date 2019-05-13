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

    Controller(ViewCallback view) {
        this.view = view;
        gson = new Gson();
        powerOn = false;
        pidSwitch = false;
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

        if(powerOn){
            powerOn = false;
        }else{
            powerOn = true;
        }

        DataOut dataOut = new DataOut(1, powerOn);
        view.changePowerBtn(powerOn);
        sendData(dataOut);
    }

    void onPidSwitch(boolean switchState){
        pidSwitch = switchState;

        DataOut dataOut = new DataOut(2, switchState);

        sendData(dataOut);
    }

    void onMotorOutputChange(int motorIndex, int motorVal){

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

        DataIn dataIn = gson.fromJson(jsonIn, DataIn.class);

        switch (dataIn.getOutputType()){
            case 1:
                view.changeMotorBar(dataIn.getMotorFLVal(),
                        dataIn.getMotorFRVal(),
                        dataIn.getMotorBLVal(),
                        dataIn.getMotorBRVal());
                break;
            case 2:
                break;
            default:
                break;
        }
    }
}
