package com.example.quadcoptercontroller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Websocket extends AsyncTask<String, Integer, String>{

    Socket s;
    DataOutputStream dos;
    PrintWriter pw;

    @Override
    protected String doInBackground(String... strings) {

        String message = strings[0];

        //debug message
        Log.d("logTag", "message: "+message);

        try {
            s = new Socket("192.168.0.22", 80);

            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            //pw.close();
            //s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "It works";
    }
}
