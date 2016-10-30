package cukes_codes.tictacdoom;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static cukes_codes.tictacdoom.R.layout.game_over_screen;


public class ArtificalDoom extends AppCompatActivity{

    private boolean isGameOver = false;
    private Integer randomPosition;
    private Random generator = new Random();
    private Integer player1wins = 0;
    private Integer computerwins = 0;
    MediaPlayer mplayer, playerx, artificalO;
    int winningPlayer = 0;
    ImageView doomLogo;
    private boolean player1 = true;
    private ArrayList<Integer> positionsLeft = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    int[][] winCond = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private ArrayList<Integer> player1_moves = new ArrayList<>();
    private ArrayList<Integer> computer_moves = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerx = MediaPlayer.create(this, R.raw.x_noise);
        artificalO = MediaPlayer.create(this, R.raw.o_noise);
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
        winningPlayer = 0;
        if (!positionsLeft.contains(placeTappedTag)) {
            MediaPlayer wrongMove = MediaPlayer.create(this, R.raw.imove);
            wrongMove.setVolume(1, 1);
            wrongMove.start();
        } else {
            player1_moves.add(placeTappedTag);
            ImageView position = (ImageView) view;
            position.setImageResource(R.drawable.x);

            playerx.setVolume(.4f, .4f);
            playerx.start();

            Integer positionTapped = Integer.parseInt((String) position.getTag());
            positionsLeft.remove(positionTapped);

            for (int i = 0; i < 8; i++) {
                int[] temp = winCond[i];
                if (player1_moves.contains(temp[0]) && player1_moves.contains(temp[1]) && player1_moves.contains(temp[2])) {
                    endGame(1);
                }
            }
            //AI code here
            if(positionsLeft.size() > 1) {
                new CountDownTimer(500, 500) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        aiMove();
                    }
                }.start();
            }
            else {
                endGame(0);
            }
            }





              //  Button rainButt = (Button) findViewById(R.id.makeitrain);
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

    public void restartGame(View view){
        Button choice = (Button) view;
        if(choice.getText().toString().equals("Yes")) {
            setContentView(R.layout.activity_main);
            positionsLeft = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
            player1_moves.clear();
            computer_moves.clear();
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

    public void endGame(int winner){

        MediaPlayer gover = MediaPlayer.create(this, R.raw.gameover);
        gover.setVolume(1, 1);
        gover.start();



        setContentView(game_over_screen);
        TextView player1score, player2score;
        player1score = (TextView) findViewById(R.id.player1_score);
        player2score = (TextView) findViewById(R.id.player2_score);

        TextView playerWhoWon = (TextView) findViewById(R.id.player_who_won);
        playerWhoWon.setText("Player " + String.valueOf(winner) + "  Won!\nPlay again?");

        switch (winner) {
            case 0:
                playerWhoWon.setText("Draw. \nPlay again?");
                break;

            case 1:
                player1wins++;
                player1score.setText("Player 1: " + String.valueOf(player1wins));
                break;

            case 2:
                computerwins++;
                player2score.setText("Player 2: " + String.valueOf(computerwins));
                break;
        }



    }
    public void aiMove(){
        randomPosition = generator.nextInt(9);
        boolean computerHasMoved = false;
        ImageView compPosition = null;
        while (!computerHasMoved) {
            if (positionsLeft.contains(randomPosition)) {
                computerHasMoved = true;
                switch (randomPosition) {
                    case 0:
                        compPosition = (ImageView) findViewById(R.id.pos0);
                        break;
                    case 1:
                        compPosition = (ImageView) findViewById(R.id.pos1);
                        break;
                    case 2:
                        compPosition = (ImageView) findViewById(R.id.pos2);
                        break;
                    case 3:
                        compPosition = (ImageView) findViewById(R.id.pos3);
                        break;
                    case 4:
                        compPosition = (ImageView) findViewById(R.id.pos4);
                        break;
                    case 5:
                        compPosition = (ImageView) findViewById(R.id.pos5);
                        break;
                    case 6:
                        compPosition = (ImageView) findViewById(R.id.pos6);
                        break;
                    case 7:
                        compPosition = (ImageView) findViewById(R.id.pos7);
                        break;
                    case 8:
                        compPosition = (ImageView) findViewById(R.id.pos8);
                        break;
                    default:
                        break;
                }
            } else randomPosition = generator.nextInt(9);
        }//end while
        if (compPosition != null) {
            compPosition.setImageResource(R.drawable.o);
            artificalO.setVolume(.4f,.4f);
            artificalO.start();
            computer_moves.add(randomPosition);
            positionsLeft.remove(randomPosition);
        }
        for (int i = 0; i < 8; i++) {
            int[] temp = winCond[i];
            if (computer_moves.contains(temp[0]) && computer_moves.contains(temp[1]) && computer_moves.contains(temp[2])) {
                endGame(2);

            }
        }
    }
}
