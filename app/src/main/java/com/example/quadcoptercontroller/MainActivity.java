package com.example.quadcoptercontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ViewCallback {

    @BindView(R.id.barValue) TextView barValue;
    @BindView(R.id.seekBar) SeekBar seekBar;
    @BindView(R.id.connectionStatus) TextView connectionStatusTv;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        seekBarSetup();
        controller = new Controller(this);
    }

    @OnClick(R.id.connectBtn)
    public void connect(){
        controller.connect();
    }

    @OnClick(R.id.disconnectBtn)
    public void disconnect(){
        controller.disconnect();
    }

    @OnClick(R.id.startCom)
    public void send(){
        controller.sendData();
    }

    private void seekBarSetup(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                barValue.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void changeConnectionText(String text, int color) {
        connectionStatusTv.setText(text);
        connectionStatusTv.setTextColor(color);
    }
}
