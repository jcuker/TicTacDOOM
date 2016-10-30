package cukes_codes.tictacdoom;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import static cukes_codes.tictacdoom.R.layout.game_over_screen;

public class MainActivity extends AppCompatActivity {

    private Integer player1wins = 0;
    private Integer player2wins = 0;
    MediaPlayer mplayer;
    int winningPlayer = 0;
    ImageView doomLogo;
    private boolean player1 = true;
    private ArrayList<Integer> positionsLeft = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    int[][] winCond = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private ArrayList<Integer> player1_moves = new ArrayList<>();
    private ArrayList<Integer> player2_moves = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doomLogo = (ImageView)findViewById(R.id.logo);
        new CountDownTimer(6200, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                MediaPlayer go = MediaPlayer.create(getApplicationContext(), R.raw.go);
                go.setVolume(1,1);
                ImageView goview = (ImageView)findViewById(R.id.go);
                goview.animate().alphaBy(100).scaleXBy(10f).scaleYBy(10f).setDuration(6000);
                goview.animate().translationYBy(10000f).setDuration(5000);
                go.start();
            }
        }.start();
        doomLogo.animate().alphaBy(100).rotation((20 * 360)).scaleXBy(1f).scaleYBy(1f).scaleXBy(-1f).scaleYBy(-1f).setDuration(6200);

        mplayer = MediaPlayer.create(this, R.raw.gore_nest);
        mplayer.setLooping(true);
        mplayer.setVolume(.3f,.3f);
        mplayer.start();
    }

    public void dropInAI(View view) {
        Integer placeTappedTag = Integer.parseInt((String) view.getTag());
        boolean isGameOver = false;
        winningPlayer = 0;
        if(!positionsLeft.contains(placeTappedTag)){
            MediaPlayer wrongMove = MediaPlayer.create(this, R.raw.imove);
            wrongMove.setVolume(1,1);
            wrongMove.start();
        }
        else if (player1){
            player1_moves.add(placeTappedTag);
            for(int i = 0; i < 8; i++){
                int[] temp = winCond[i];
                if(player1_moves.contains(temp[0]) && player1_moves.contains(temp[1]) && player1_moves.contains(temp[2])) {
                    isGameOver = true;
                    winningPlayer = 1;
                }
            }
            ImageView position = (ImageView)view;
            position.setImageResource(R.drawable.x);
            MediaPlayer playerx = MediaPlayer.create(this, R.raw.x_noise);
            playerx.setVolume(.4f,.4f);
            playerx.start();
            player1 = !player1;
            Integer positionTapped = Integer.parseInt((String) position.getTag());
            positionsLeft.remove(positionTapped);
        }
        else{
            player2_moves.add(placeTappedTag);
            for(int i = 0; i < 8; i++){
                int[] temp = winCond[i];
                if(player2_moves.contains(temp[0]) && player2_moves.contains(temp[1]) && player2_moves.contains(temp[2])) {
                    isGameOver = true;
                    winningPlayer = 2;
                }
            }
            ImageView position = (ImageView)view;
            position.setImageResource(R.drawable.o);
            MediaPlayer playero = MediaPlayer.create(this, R.raw.o_noise);
            playero.setVolume(.4f,.4f);
            playero.start();
            player1 = !player1;
            Integer positionTapped = Integer.parseInt((String) position.getTag());
            positionsLeft.remove(positionTapped);
        }

        if(isGameOver){
            ImageView gameover = (ImageView)findViewById(R.id.gameover);
            gameover.setAlpha(.8f);
            gameover.bringToFront();
            gameover.setBackgroundColor(Color.BLACK);
            gameover.setTranslationY(-350f);
            MediaPlayer gover = MediaPlayer.create(this, R.raw.gameover);
            gover.setVolume(1,1);
            gover.start();
            GridLayout board = (GridLayout)findViewById(R.id.grid);
            board.setAlpha(.3f);


            setContentView(game_over_screen);
            TextView player1score, player2score;
            player1score = (TextView)findViewById(R.id.player1_score);
            player2score = (TextView)findViewById(R.id.player2_score);

            TextView playerWhoWon = (TextView)findViewById(R.id.player_who_won);
            playerWhoWon.setText("Player " + String.valueOf(winningPlayer) + "  Won!\nPlay again?");
            if(winningPlayer == 1){
                player1wins++;
                player1score.setText("Player 1: " + String.valueOf(player1wins));
            }
            else {
                player2wins++;
                player2score.setText("Player 2: " + String.valueOf(player2wins));
            }




            Button rainButt = (Button) findViewById(R.id.makeitrain);
           /*ViewTreeObserver viewTreeObserver = rainButt.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                    }
                });
            }

            new ParticleSystem(MainActivity.this, 30, R.drawable.blood, 10000)
                    .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                    .setAcceleration(0.5f, 90)
                    .setScaleRange(0.00001f,.09f)
                    .emitWithGravity(findViewById(R.id.makeitrain), Gravity.BOTTOM, 8);
                    */
        }
    }
    public void restartGame(View view){
        Button choice = (Button) view;
        if(choice.getText().toString().equals("Yes")) {
            setContentView(R.layout.activity_main);
            positionsLeft = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
            player1_moves.clear();
            player2_moves.clear();
            player1 = true;

        }
        else this.finishAffinity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mplayer.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mplayer.start();
    }
}
