package cukes_codes.tictacdoom;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class StartScreen extends AppCompatActivity {
    MediaPlayer menu = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
     //  menu = MediaPlayer.create(this, R.raw.mainmenu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        menu = MediaPlayer.create(this, R.raw.mainmenu);
        menu.start();
    }

    public void starter (View view) {

        switch (view.getId()) {
            case R.id.human:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.ai: {
                intent = new Intent(this, ArtificalDoom.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        menu.stop();
    }
}
