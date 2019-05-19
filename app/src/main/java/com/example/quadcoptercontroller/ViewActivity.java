package com.example.quadcoptercontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    @BindView(R.id.connectionStatus)
    TextView connectionStatusTv;
    @BindView(R.id.connectionBtn)
    Button connectionBtn;
    @BindView(R.id.powerBtn)
    Button powerBtn;
    @BindView(R.id.pidSwitch)
    Switch pidSwitch;

    @BindView(R.id.motorFLBar)
    SeekBar motorFLBar;
    @BindView(R.id.motorFRBar)
    SeekBar motorFRBar;
    @BindView(R.id.motorBLBar)
    SeekBar motorBLBar;
    @BindView(R.id.motorBRBar)
    SeekBar motorBRBar;
    @BindView(R.id.motorFLVal)
    TextView motorFLValTv;
    @BindView(R.id.motorFRVal)
    TextView motorFRValTv;
    @BindView(R.id.motorBLVal)
    TextView motorBLValTv;
    @BindView(R.id.motorBRVal)
    TextView motorBRValTv;
    @BindView(R.id.motorTargetBar)
    SeekBar motorTargetBar;
    @BindView(R.id.motorTargetVal)
    TextView motorTargetTv;

    private final String logTag = "logTag";

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setupUi();
        controller = new Controller(this);
    }

    private void setupUi() {

        pidSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                controller.onPidSwitch(isChecked);
            }
        });

        motorTargetBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                motorTargetTv.setText(progress + "%");
                controller.setTargetVal(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        motorFLBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                motorFLValTv.setText(progress + "%");
                controller.onMotorOutputChange(0, progress);
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
                motorFRValTv.setText(progress + "%");
                controller.onMotorOutputChange(1, progress);
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
                motorBLValTv.setText(progress + "%");
                controller.onMotorOutputChange(2, progress);
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
                motorBRValTv.setText(progress + "%");
                controller.onMotorOutputChange(3, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        motorFLBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return controller.getPidSwitchState();
            }
        });

        motorFRBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return controller.getPidSwitchState();
            }
        });

        motorBLBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return controller.getPidSwitchState();
            }
        });

        motorBRBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return controller.getPidSwitchState();
            }
        });
    }

    @OnClick(R.id.connectionBtn)
    public void connectionBtn() {
        controller.onConnectionBtn();
    }

    @OnClick(R.id.powerBtn)
    public void powerBtn() {
        controller.onPowerBtn();
    }

    @Override
    public void changeMotorBar(int motorFLVal, int motorFRVal, int motorBLVal, int motorBRVal) {
        motorFLBar.setProgress(motorFLVal);
        motorFRBar.setProgress(motorFRVal);
        motorBLBar.setProgress(motorBLVal);
        motorBRBar.setProgress(motorBRVal);
    }

    @Override
    public void changeConnectionText(String text, int color) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectionStatusTv.setText(text);
                connectionStatusTv.setTextColor(color);
            }
        });
    }

    @Override
    public void changeConnectionBtn(boolean connected) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (connected) {
                    connectionBtn.setText("Disconnect");
                } else {
                    connectionBtn.setText("Connect");
                }
            }
        });
    }

    @Override
    public void changePowerBtn(boolean powerOn) {
        if (powerOn) {
            powerBtn.setText("Power On");
        } else {
            powerBtn.setText("Power Off");
        }
    }

    @Override
    public void makeToast(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ViewActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
