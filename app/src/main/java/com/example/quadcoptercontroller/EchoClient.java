package com.example.quadcoptercontroller;

import android.util.Log;

import com.neovisionaries.ws.client.HostnameUnverifiedException;
import com.neovisionaries.ws.client.OpeningHandshakeException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class EchoClient {

    private static final String SERVER = "ws://192.168.0.22";

    private static final int TIMEOUT = 5000;

    private WebSocket ws;

    void connect() {
        setupConnection();
        ws.connectAsynchronously();
    }

    void sendTextToServer(){
        if(ws!=null){
            ws.sendText("It works");
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
            }

            @Override
            public void onConnected(WebSocket websocket, Map<String, List<String>> headers){
                Log.d("logTag", "message: "+ "connected");
            }

            @Override
            public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
                Log.d("logTag", "message: "+ "disconnected");
            }

            @Override
            public void onTextMessage(WebSocket websocket, String message) {
                Log.d("logTag", "message from server: "+message);
            }
        });
    }
}
