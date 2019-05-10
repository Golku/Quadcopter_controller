package com.example.quadcoptercontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewActivity extends AppCompatActivity implements ViewCallback {

    @BindView(R.id.connectionStatus) TextView connectionStatusTv;
    @BindView(R.id.connectionBtn) Button connectionBtn;
    @BindView(R.id.powerBtn) Button powerBtn;
    @BindView(R.id.pidSwitch) Switch pidSwitch;
    @BindView(R.id.motorFLBar) SeekBar motorFLBar;
    @BindView(R.id.motorFRBar) SeekBar motorFRBar;
    @BindView(R.id.motorBLBar) SeekBar motorBLBar;
    @BindView(R.id.motorBRBar) SeekBar motorBRBar;
    @BindView(R.id.motorFLVal) TextView motorFLVal;
    @BindView(R.id.motorFRVal) TextView motorFRVal;
    @BindView(R.id.motorBLVal) TextView motorBLVal;
    @BindView(R.id.motorBRVal) TextView motorBRVal;

    private final String logTag = "logTag";

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        setupUi();
        controller = new Controller(this);
    }

    private void setupUi(){

        pidSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                controller.onPidSwitch(isChecked);
            }
        });

        motorFLBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                motorFLVal.setText(String.valueOf(progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        motorFRBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                motorFRVal.setText(String.valueOf(progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        motorBLBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                motorBLVal.setText(String.valueOf(progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        motorBRBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                motorBRVal.setText(String.valueOf(progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        motorFLBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return controller.getPidSwitchState();
            }
        });

        motorFRBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return controller.getPidSwitchState();
            }
        });

        motorBLBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return controller.getPidSwitchState();
            }
        });

        motorBRBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return controller.getPidSwitchState();
            }
        });
    }

    @OnClick(R.id.connectionBtn)
    public void connectionBtn(){
        controller.onConnectionBtn();
    }

    @OnClick(R.id.powerBtn)
    public void powerBtn(){
        controller.onPowerBtn();
    }

    @Override
    public void changeMotorBar(int motor, int progress) {

        switch (motor){
            case 1:
                motorFLBar.setProgress(progress);
                motorFLVal.setText(String.valueOf(progress));
                break;
            case 2:
                motorFRBar.setProgress(progress);
                motorFRVal.setText(String.valueOf(progress));
                break;
            case 3:
                motorBLBar.setProgress(progress);
                motorBLVal.setText(String.valueOf(progress));
                break;
            case 4:
                motorBRBar.setProgress(progress);
                motorBRVal.setText(String.valueOf(progress));
                break;
        }
    }

    @Override
    public void changeConnectionText(String text, int color) {
        connectionStatusTv.setText(text);
        connectionStatusTv.setTextColor(color);
    }

    @Override
    public void changeConnectionBtn(boolean connected) {
        if(connected){
            connectionBtn.setText("Disconnect");
        }else{
            connectionBtn.setText("Connect");
        }
    }

    @Override
    public void changePowerBtn(boolean powerOn) {
        if(powerOn){
            powerBtn.setText("Power On");
        }else{
            powerBtn.setText("Power Off");
        }
    }

    @Override
    public void makeToast(String message) {
        Log.d(logTag, "makeToast1");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.d(logTag, "makeToast2");
    }
}
