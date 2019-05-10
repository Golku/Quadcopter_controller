package com.example.quadcoptercontroller;

import android.graphics.Color;
import android.util.Log;

import com.google.gson.Gson;

public class Controller implements ControllerCallback {

    private final String logTag = "logTag";

    private WebsocketClient websocketClient;
    private ViewCallback view;
    private Gson gson;

    private boolean pidSwitch;

    Controller(ViewCallback view) {
        this.view = view;
        gson = new Gson();
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

    void onPidSwitch(boolean switchState){
        if(switchState){
            pidSwitch = true;
            Log.d(logTag, "pid on");
        }else{
            pidSwitch = false;
            Log.d(logTag, "pid off");
        }
    }

    void onPowerBtn() {

        //view.changePowerBtn(true);
    }

    void sendData(){

        DataOut dataOut = new DataOut(2, false);

        String json = gson.toJson(dataOut);

        Log.d(logTag, "json send: " + json);

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
        Log.d(logTag, "onConnectionError");
        view.makeToast("Connect error");
    }

    @Override
    public void serverResponse(String jsonIn) {
        //Log.d("logTag", "message from server: "+json);

        DataIn dataIn = gson.fromJson(jsonIn, DataIn.class);

        Log.d(logTag, "outputType: "+dataIn.getOutputType()
        +" power: "+dataIn.isPowerOn()+ " speed: "+dataIn.getSpeed());

        //view.changeMotorBar(dataIn.getSpeed());
    }
}
