package com.example.quadcoptercontroller;

import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class WebsocketClient {

    private final String logTag = "logTag";

//    private static final String SERVER = "ws://192.168.0.22";
    private static final String SERVER = "ws://192.168.43.108";

    private static final int TIMEOUT = 5000;

    private ControllerCallback controllerCallback;

    private WebSocket ws;

    private boolean connected = false;

    WebsocketClient(ControllerCallback controllerCallback) {
        this.controllerCallback = controllerCallback;
    }

    void openConnection() {
        setupConnection();
        ws.connectAsynchronously();
    }

    void closeConnection(){
        ws.disconnect();
    }

    void sendData(String data){
        if(ws!=null){
            ws.sendText(data);
        }else{
            Log.d("logTag", "Websocket is null");
        }
    }

    private void setupConnection(){

        WebSocketFactory factory = new WebSocketFactory();
        ws = null;

        try {
            ws = factory.createSocket(SERVER, TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("logTag", "message: "+"ws creation exception");
        }

        if(ws == null){
            Log.d("logTag", "message: "+"ws was null");
            return;
        }

        ws.addListener(new WebSocketAdapter() {

            @Override
            public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
                Log.d("logTag", "message: "+ "connection error");
                controllerCallback.onConnectionError();
            }

            @Override
            public void onConnected(WebSocket websocket, Map<String, List<String>> headers){
                connected = true;
                Log.d("logTag", "message: "+ "connected");
                controllerCallback.connectionEstablished();
            }

            @Override
            public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
                connected = false;
                Log.d("logTag", "message: "+ "disconnected");
                controllerCallback.connectionClosed();
            }

            @Override
            public void onTextMessage(WebSocket websocket, String message) {
                controllerCallback.serverResponse(message);
            }


        });
    }

    boolean isConnected() {
        return connected;
    }
}
