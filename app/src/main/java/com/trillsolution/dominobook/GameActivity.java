package com.trillsolution.dominobook;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trillsolution.dominobook.model.Domino;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    Gson gson;
    Domino domino;
    List<String> players;
    int currentPlayer;
    Intent intent;

    LinearLayout team1;
    LinearLayout team2;

    Keyboard keyboard;
    KeyboardView keyboardView;

    TextView score1;
    TextView score2;

    TextView player1;
    TextView player2;
    TextView newScore;
    TextView playerTurn;

    int currentScoreBoard;

    String breakLines;

    private void updateTurn() {
        int pos = currentPlayer % players.size();
        String turn = getResources().getString(R.string.player_turn)
                                    .concat(" ")
                                    .concat(players.get(pos));
        playerTurn.setText(turn);
        currentPlayer++;
    }

    private void addBreakLine() {
        breakLines.concat("\n");
    }

    private void loadPlayers(String data) {
        currentPlayer = 0;
        gson = new Gson();
        domino = gson.fromJson(data, Domino.class);
        players = new ArrayList<>();

        if (domino.getPlayer1() != null && !domino.getPlayer1().isEmpty())
            players.add(domino.getPlayer1());

        if (domino.getPlayer2() != null && !domino.getPlayer2().isEmpty())
            players.add(domino.getPlayer2());

        if (domino.getPlayer3() != null && !domino.getPlayer3().isEmpty())
            players.add(domino.getPlayer3());

        if (domino.getPlayer4() != null && !domino.getPlayer4().isEmpty())
            players.add(domino.getPlayer4());

        if (players.size() < 2)
            finish();
    }

    public void openKeyboard(View v){
        keyboardView.setVisibility(View.VISIBLE);
        keyboardView.setEnabled(true);
        if (v != null)
            ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void setListeners() {
        team1.setOnClickListener(new team1Listener());
        team2.setOnClickListener(new team2Listener());
        keyboardView.setOnKeyboardActionListener(new customKeyboardActionListener());
    }

    public void initUI(){
        loadPlayers(intent.getStringExtra("domino"));

        team1 = (LinearLayout) findViewById(R.id.ll_score1);
        team2 = (LinearLayout) findViewById(R.id.ll_score2);

        score1 = (TextView) findViewById(R.id.score_player1);
        score2 = (TextView) findViewById(R.id.score_player2);

        newScore = (TextView) findViewById(R.id.new_score);

        player1 = (TextView) findViewById(R.id.player1);
        player2 = (TextView) findViewById(R.id.player2);

        player1.setText(domino.getPlayer1());
        player2.setText(domino.getPlayer2());

        playerTurn = (TextView) findViewById(R.id.player_turn_name);

        keyboard = new Keyboard(this, R.xml.custom_keyboard);
        keyboardView = (KeyboardView) findViewById(R.id.custom_keyboard);
        keyboardView.setKeyboard(keyboard);

        setListeners();
    }

    public class team1Listener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            currentScoreBoard = 1;
            openKeyboard(view);
            updateTurn();
        }
    }

    public class team2Listener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            currentScoreBoard = 2;
            openKeyboard(view);
            updateTurn();
        }
    }

    public class customKeyboardActionListener implements KeyboardView.OnKeyboardActionListener {

        @Override
        public void onPress(int i) {
            Toast.makeText(getApplicationContext(), "i= " + Integer.toString(i), Toast.LENGTH_SHORT);
        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int i, int[] ints) {
            if (i == -1) {
                String score = newScore.getText().toString();
                newScore.setText(score.substring(0, score.length() - 1));
            } else if (i == 100){
                String addScore = newScore.getText().toString();
                String previousScore = null;
                if (currentScoreBoard == 1) {
                    previousScore = score1.getText().toString();
                    score1.setText(previousScore.concat(breakLines).concat(addScore));
                } else if (currentScoreBoard == 2) {
                    previousScore = score2.getText().toString();
                    score2.setText(previousScore.concat(breakLines).concat(addScore));
                }

            } else
                newScore.setText(newScore.getText().toString().concat(Integer.toString(i)));

        }

        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        intent = getIntent();
        initUI();
    }

}
