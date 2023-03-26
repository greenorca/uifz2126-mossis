package ch.wiss.m335_uifz2126_mossigame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements Runnable {

    FrameLayout playground;
    Random random;

    TextView txtRound, txtScore, txtRemainingTime, txtMossisCatched;

    Handler handler;
    private Date tStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        playground = findViewById(R.id.spielfeld);
        random = new Random();
        handler = new Handler();
        txtRound = findViewById(R.id.txtRunde);
        txtScore = findViewById(R.id.txtPunkte);

        startNextLevel();
        handler.postDelayed(this, 1000);

    }

    private void generateMossi(){
        //create mossi imageview
        ImageView mossi = new ImageView(this);
        mossi.setBackgroundResource(R.drawable.mosquito);

        // find random position in playground
        int width = playground.getWidth();
        int height = playground.getHeight();

        float density = getResources().getDisplayMetrics().density;
        int mossiWidth = Math.round(density * 50);
        int mossiHeight = Math.round(density * 42);

        int xPos = random.nextInt(width - mossiWidth);
        int yPos = random.nextInt(height - mossiHeight);

        // set layout params
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            mossiWidth, mossiHeight
        );
        params.gravity = Gravity.TOP+Gravity.LEFT;
        params.leftMargin = xPos;
        params.topMargin = yPos;
        // add to playground
        playground.addView(mossi, params);
        // set onclick lister

        mossi.setOnClickListener(v -> removeMossi(v));
    }

    int score;
    int mossisCatched;
    private int level;
    private int mossisToCatch;
    static final private int LEVEL_TIME = 30;

    private void removeMossi(View killedMossi) {
        score++;
        mossisCatched++;
        playground.removeView(killedMossi);
        txtScore.setText("Score: "+ score);
    }

    /**
     * increments level, resets mossiscatched and some other vars
     */
    private void startNextLevel(){
        level++;
        mossisToCatch = 10 * level;
        mossisCatched = 0;
        tStart = new Date();
        txtRound.setText("Level "+ level);

    }

    public void decrementTime(){
        Log.d("MOSSI", "decrement time");
        if (!gameFinished()){
            if (!levelFinished()){
                generateMossi();
            } else {
                Log.d("MOSSI", "next level");
                startNextLevel();
            }
        } else {
            //Log.d("MOSSI", "game over");
            TextView nigmar = findViewById(R.id.textViewNigmar);
            nigmar.setText("GAME OVER");
            // remove all mossis
            for (int i = 0; i < playground.getChildCount(); i++){
                View v = playground.getChildAt(i);
                if (v.getClass().equals(ImageView.class)){
                    playground.removeView(v);
                }
            }
            //Show Game over
        }
    }

    private boolean levelFinished() {
        return mossisCatched >= mossisToCatch;
    }

    private boolean gameFinished() {
        long timeRemaining = LEVEL_TIME * 1000 - (new Date().getTime() - tStart.getTime());
        return timeRemaining <= 0 && mossisCatched < mossisToCatch;
    }

    @Override
    public void run() {
        decrementTime();
        handler.postDelayed(this,1000);
    }
}