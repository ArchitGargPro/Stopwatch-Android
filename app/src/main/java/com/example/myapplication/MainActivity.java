package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView minutesText;
    TextView secondsText;
    TextView mSecondsText;
    TextView startButton;
    TextView resetButton;
    TextView lapButton;
    ListView lapList ;
    int Seconds, Minutes, MilliSeconds ;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    String[] ListElements = new String[] {};
    ArrayList<String> ListElementsArrayList ;
    ItemAdapter adapter ;
    Boolean start = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        minutesText = findViewById(R.id.minutes);
        secondsText = findViewById(R.id.seconds);
        mSecondsText = findViewById(R.id.m_seconds);
        startButton = findViewById(R.id.start);
        resetButton = findViewById(R.id.reset);
        lapButton = findViewById(R.id.lap);
        lapList = findViewById(R.id.lap_list);
        handler = new Handler();

        ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));
        adapter = new ItemAdapter(MainActivity.this, ListElementsArrayList);
        lapList.setAdapter(adapter);

        // OnClickListeners
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start) {
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    startButton.setText("PAUSE");
                    resetButton.setEnabled(false);
                    resetButton.setBackgroundColor(getResources().getColor(R.color.buttonDisabled));
                    lapButton.setEnabled(true);
                    lapButton.setBackgroundColor(getResources().getColor(R.color.buttonEnabled));

                    start = false;
                } else {
                    // Pause Pressed
                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);
                    startButton.setText("START");
                    resetButton.setEnabled(true);
                    resetButton.setBackgroundColor(getResources().getColor(R.color.buttonEnabled));
                    lapButton.setEnabled(false);
                    lapButton.setBackgroundColor(getResources().getColor(R.color.buttonDisabled));
                    start = true;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MillisecondTime = 0L;
                UpdateTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                Minutes = 0;
                Seconds = 0;
                MilliSeconds = 0;

                minutesText.setText(String.format("%02d", Minutes));
                secondsText.setText(String.format("%02d", Seconds));
                mSecondsText.setText(String.format("%03d", MilliSeconds));

                ListElementsArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(">>>>>",String.format("%02d", Minutes)+":"+String.format("%02d", Seconds)+":"+String.format("%03d", MilliSeconds));
                ListElementsArrayList.add(String.format("%02d", Minutes)+":"+String.format("%02d", Seconds)+":"+String.format("%03d", MilliSeconds));
                Log.i(">>>>>?", ListElementsArrayList.toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run(){
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) UpdateTime/1000;
            Minutes = Seconds/60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) UpdateTime%1000;
            minutesText.setText(String.format("%02d", Minutes));
            secondsText.setText(String.format("%02d", Seconds));
            mSecondsText.setText(String.format("%03d", MilliSeconds));
            handler.postDelayed(this, 0);
        }
    };

}
