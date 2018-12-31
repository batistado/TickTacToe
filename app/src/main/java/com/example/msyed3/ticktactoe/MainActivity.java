package com.example.msyed3.ticktactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean isRedsTurn;
    private boolean hasWinner = false;

    // 0 is red, 1 is yellow, 2 is empty
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] winningCombination = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 4, 8}, {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRedsTurn = true;
        setContentView(R.layout.activity_main);
    }

    public void tick(View view){
        ImageView imageView = (ImageView) view;

        if (imageView.getDrawable() == null && !hasWinner){

            gameState[Integer.parseInt(imageView.getTag().toString())] = isRedsTurn ? 0 : 1;

            if (isRedsTurn){
                imageView.setImageResource(R.drawable.red);
                isRedsTurn = false;
            } else {
                imageView.setImageResource(R.drawable.yellow);
                isRedsTurn = true;
            }
            imageView.setTranslationY(-15000);
            imageView.animate().translationYBy(15000).setDuration(200);

            hasWinner = checkWinner();

            if (hasWinner){
                TextView winnerText = findViewById(R.id.winnerText);
                Button playAgainButton = findViewById(R.id.playAgainButton);
                String winner = isRedsTurn ? "Yello" : "Red";
                winnerText.setText(winner + " wins!");
                winnerText.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
            }

        }
    }

    public boolean checkWinner(){
        for (int r[] : winningCombination){
            if (gameState[r[0]] == gameState[r[1]] && gameState[r[2]] == gameState[r[1]] && gameState[r[0]] != 2) {
                return true;
            }
        }

        return false;
    }

    public void resetGame(View view) {
        Button playAgain = (Button) view;
        playAgain.setVisibility(View.INVISIBLE);
        TextView winnerText = findViewById(R.id.winnerText);
        winnerText.setVisibility(View.INVISIBLE);

        for (int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }
        isRedsTurn = true;
        hasWinner = false;

        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        for(int i=0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
        }
    }
}
