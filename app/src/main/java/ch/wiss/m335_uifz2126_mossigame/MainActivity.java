package ch.wiss.m335_uifz2126_mossigame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Runnable{

    private static final int
            GAME_REQUEST = 42;
    Handler handy;
    TextView texty;
    private int score;

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

        startActivityForResult(i, GAME_REQUEST);
    }

    public void saveHighscore(View v){
        EditText editTextName = findViewById(R.id.editTextName);

        SharedPreferences pref = getSharedPreferences("GAME",0);
        SharedPreferences.Editor edi = pref.edit();

        edi.putString("NAME", editTextName.getText().toString());
        edi.putInt("SCORE", score);
        edi.commit();
        enableForm(false);
    }

    private void enableForm(boolean enabled){
        LinearLayout formName = findViewById(R.id.inputForm);
        formName.setVisibility(enabled? View.VISIBLE: View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int request, int result, Intent returnIntent) {

        super.onActivityResult(request, result, returnIntent);

        if (request == GAME_REQUEST && result==RESULT_OK){
            Log.d(getClass().getSimpleName(),
                    "GAME ACTIVITY FINISHED: "
                            + returnIntent.getIntExtra("SCORE",-1));
            score = returnIntent.getIntExtra("SCORE",-1);
            enableForm(true);
        }
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