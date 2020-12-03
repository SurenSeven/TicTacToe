package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    GridLayout.LayoutParams gridLayoutParams;
    TextView[][] textViews = new TextView[3][3];
    MyGridLayout board;
    TextView headerText, gameOverMessage;
    Button playButton, playAgainButton;
    float textY = 0;
    DrawLine drawLine;
    ViewGroup boardContainer;
    final int PLAYER1 = 1;
    final int PLAYER2 = 2;
    int ACTIVE_PLAYER;
    private int SETUP_BOARD = 0;
    int[] boardState;
    boolean gameOver;
    final int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    int[] clickCounter;
    final Map<int[], int[]> mapPositions = new HashMap<>();

    private void init() {
        boardState = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gameOver = false;
        clickCounter = new int[]{0};
        board.setVisibility(View.GONE);
        board.setInterceptTouchEvents(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headerText = findViewById(R.id.header);
        playButton = findViewById(R.id.play_button);
        board = findViewById(R.id.board);
        playAgainButton = findViewById(R.id.play_again_button);
        textY = headerText.getY();
        drawLine = new DrawLine(MainActivity.this);
        boardContainer = findViewById(R.id.board_container);
        init();
        mapPositions.put(winningPositions[0], new int[]{0, 0, 2, 0});
        mapPositions.put(winningPositions[1], new int[]{0, 1, 2, 1});
        mapPositions.put(winningPositions[2], new int[]{0, 2, 2, 2});
        mapPositions.put(winningPositions[3], new int[]{0, 0, 0, 2});
        mapPositions.put(winningPositions[4], new int[]{1, 0, 1, 2});
        mapPositions.put(winningPositions[5], new int[]{2, 0, 2, 2});
        mapPositions.put(winningPositions[6], new int[]{0, 0, 2, 2});
        mapPositions.put(winningPositions[7], new int[]{2, 0, 0, 2});


        playButton.setOnClickListener(v -> {
            headerText.animate().translationY(-450f).setDuration(500);
            playButton.animate().alpha(0f).setDuration(400);
            setUpBoard();
            board.animate().alpha(1f).setDuration(450);
            board.setVisibility(View.VISIBLE);
            gameOverMessage = findViewById(R.id.game_over_message);
            ACTIVE_PLAYER = 1;

        });

        playAgainButton.setOnClickListener(v -> resetBoard());


    }

    private void setUpBoard() {
        if (SETUP_BOARD == 0) {
            int tagCounter = -1;
            for (int i = 0; i < 3; i++) {

                for (int j = 0; j < 3; j++) {
                    tagCounter++;
                    textViews[i][j] = new TextView(this);
                    gridLayoutParams = new GridLayout.LayoutParams();
                    gridLayoutParams.height = 400;
                    gridLayoutParams.width = 400;
                    if (i == 1 && j == 1) {
                        gridLayoutParams.rightMargin = 5;
                        gridLayoutParams.leftMargin = 5;
                        gridLayoutParams.topMargin = 5;
                        gridLayoutParams.bottomMargin = 5;
                    }
                    gridLayoutParams.columnSpec = GridLayout.spec(i);
                    gridLayoutParams.rowSpec = GridLayout.spec(j);
                    gridLayoutParams.setGravity(Gravity.CENTER);
                    textViews[i][j].setLayoutParams(gridLayoutParams);
                    textViews[i][j].setBackgroundColor(Color.parseColor("#212845"));
                    textViews[i][j].setGravity(Gravity.CENTER);
                    textViews[i][j].setTextSize(40f);
                    textViews[i][j].setTextColor(Color.WHITE);
                    textViews[i][j].setTag(tagCounter);
                    board.addView(textViews[i][j]);
                    int finalTagCounter = tagCounter;

                    textViews[i][j].setOnClickListener(v -> {
                        clickCounter[0]++;
                        if (ACTIVE_PLAYER == 1) {
                            ((TextView) v).setText("O");
                            boardState[finalTagCounter] = ACTIVE_PLAYER;
//                                Log.d("TAG", "boardState "+finalTagCounter+": "+boardState[finalTagCounter]);
                            ACTIVE_PLAYER = PLAYER2;
                        } else {
                            ((TextView) v).setText("X");
                            boardState[finalTagCounter] = ACTIVE_PLAYER;
//                                Log.d("TAG", "boardState "+finalTagCounter+": "+boardState[finalTagCounter]);

                            ACTIVE_PLAYER = PLAYER1;
                        }
                        v.setEnabled(false);
                        for (int[] winningPosition : winningPositions) {
//                                Log.d("TAG", "winningPositions: " + boardState[winningPosition[0]] + " " + boardState[winningPosition[1]] + " " + boardState[winningPosition[2]]);
                            if (boardState[winningPosition[0]] == boardState[winningPosition[1]] &&
                                    boardState[winningPosition[1]] == boardState[winningPosition[2]] &&
                                    boardState[winningPosition[0]] != 0) {

                                gameOver = true;
                                int[] viewPos = mapPositions.get(winningPosition);

//                                    Log.d("TAG", "onClick: "+viewPos[0]+""+viewPos[1]+" "+viewPos[2]+""+viewPos[3]);

                                drawLine.setPointsRelativeToParent(textViews[viewPos[1]][viewPos[0]], textViews[viewPos[3]][viewPos[2]], boardContainer);

                                if (drawLine.getParent() == null) {
                                    boardContainer.addView(drawLine);
                                }
                                gameOverMessage.setVisibility(View.VISIBLE);
                                if (boardState[winningPosition[0]] == PLAYER1) {

                                    gameOverMessage.setText(R.string.player_O_won);
                                } else {
                                    gameOverMessage.setText(R.string.player_X_won);

                                }
                                gameOverMessage.animate().alpha(1f).setDuration(500);
                                break;
                            }
                        }
                        if (clickCounter[0] == 9) {
                            gameOver = true;
                            gameOverMessage.setText(R.string.game_tied);
                            gameOverMessage.animate().alpha(1f).setDuration(500);

                        }

                        if (gameOver) {
                            board.setInterceptTouchEvents(true);
                            playAgainButton.animate().alpha(1f).setDuration(400);
                            playAgainButton.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        }
        SETUP_BOARD = 1;
    }

    private void resetBoard() {
        SETUP_BOARD = 0;
        playAgainButton.animate().alpha(0f).setDuration(300);
        playAgainButton.setVisibility(View.INVISIBLE);
        headerText.animate().translationY(textY).setDuration(500);
        playButton.animate().alpha(1f).setDuration(400);
        boardContainer.removeView(drawLine);
        board.animate().alpha(0f).setDuration(350);
        init();
        gameOverMessage.animate().alpha(0f).setDuration(300);

    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpBoard();
    }

}