// Name: Werner Ordonez
// Date: 11/10/19
// File: MainActivity.java

package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView plainText;
    TextView textView;
    MediaPlayer mediaPlayer;

    public void select(View view){
        plainText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        mediaPlayer = MediaPlayer.create(this,R.raw.alarm);

        try{
            int time = Integer.parseInt(plainText.getText().toString());
            final int milliSecond = time * 1000;
            // convert seconds to milliseconds
            new CountDownTimer(milliSecond,1000){ //1000 is equal to 1 second
                @Override
                // timer visual
                public void onTick(long millisUntilFinished) {
                    textView.setText("00.0" +String.valueOf(millisUntilFinished/1000));
                }
                @Override
                // when the time is reached play alarm
                public void onFinish() {
                    textView.setText("Alarm");
                    mediaPlayer.start();
                }
            }.start();
        }catch (NumberFormatException e){
            // int type checking
            Toast.makeText(this,"Enter Integers Only",Toast.LENGTH_LONG).show();
        }
    }

    public void stop(View view){
        mediaPlayer.stop();
    } // stop alarm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}