package ch.wiss.m335_uifz2126_mossigame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Runnable{

    Handler handy;
    TextView texty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texty = findViewById(R.id.textView);
        handy = new Handler();
        handy.postDelayed( this, 10);
    }

    public void startGame(View v){
        Intent i = new Intent(this, GameActivity.class);

        startActivity(i);
    }

    public void run(){
        Calendar cal = Calendar.getInstance();
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String time = String.valueOf(hour)+":";
        if (minute < 10)  time += "0";
        time += minute+":";
        if (second < 10)  time += "0";
        time +=second;
        texty.setText(time);
        handy.postDelayed(this,1000);
    }
}