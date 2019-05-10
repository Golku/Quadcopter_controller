package com.example.quadcoptercontroller;

import android.graphics.Color;
import android.util.Log;

public class Controller implements WebsocketCallback{

    private WebsocketClient websocketClient;
    private ViewCallback view;

    Controller(ViewCallback view) {
        this.view = view;
        websocketClient = new WebsocketClient(this);
    }

    void connect(){
        websocketClient.openConnection();
    }

    void disconnect(){
        websocketClient.closeConnection();
    }

    void sendData(){
        websocketClient.sendData();
    }

    @Override
    public void connectionEstablished() {
        view.changeConnectionText("connected", Color.GREEN);
    }

    @Override
    public void connectionClosed() {
        view.changeConnectionText("disconnected", Color.RED);
    }

    @Override
    public void serverResponse(String json) {
        Log.d("logTag", "message from server: "+json);
    }

}
