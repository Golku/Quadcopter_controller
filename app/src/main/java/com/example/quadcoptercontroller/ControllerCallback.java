package com.example.quadcoptercontroller;

public interface ControllerCallback {

    void connectionEstablished();

    void connectionClosed();

    void onConnectionError();

    void serverResponse(String json);
}
