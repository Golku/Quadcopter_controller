package com.example.quadcoptercontroller;

public interface WebsocketCallback {

    void connectionEstablished();

    void connectionClosed();

    void serverResponse(String json);
}
