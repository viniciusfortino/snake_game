package br.com.vinicius.teste.gazeus;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class SnakeActivity extends Activity {
    SnakeGame mSnakeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        mSnakeGame = new SnakeGame(this, size);

        setContentView(mSnakeGame);
    }

    /**
     * Start the game when it can be done e Restart the game when player come back from
     * another application
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSnakeGame.resume();
    }

    /**
     * Pause the game when player open another application
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSnakeGame.pause();
    }
}