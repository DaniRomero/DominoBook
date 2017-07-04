package com.trillsolution.dominobook;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trillsolution.dominobook.model.Domino;
import com.trillsolution.dominobook.model.Game;
import com.trillsolution.dominobook.model.Games;
import com.trillsolution.dominobook.preferences.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    Activity activity;
    Context context;

    Gson gson;
    Domino domino;
    Games games;
    Game game;

    List<String> players;
    int currentPlayer;
    Intent intent;

    LinearLayout team1;
    LinearLayout team2;

    RelativeLayout activityGameLayout;

    Keyboard keyboard;
    KeyboardView keyboardView;

    TextView score1;
    TextView score2;

    TextView player1;
    TextView player2;

    TextView newScore;
    TextView playerTurn;
    TextView playerWinner;

    TextView totalScore1;
    TextView totalScore2;

    int intTotalScore1;
    int intTotalScore2;

    Button saveGame;

    SharedPreferencesUtil sharedPreferencesUtil;

    int currentScoreBoard;

    StringBuilder breakLines;

    boolean initialized;

    private void updateTurn() {
        int pos = currentPlayer % players.size();
        String turn = getResources().getString(R.string.player_turn)
                                    .concat(" ")
                                    .concat(players.get(pos));
        playerTurn.setText(turn);
        currentPlayer++;
    }

    private void updateTotal(){
        totalScore1.setText(getResources().getString(R.string.player_total)
                                          .concat(" ")
                                          .concat(String.valueOf(intTotalScore1)));
        totalScore2.setText(getResources().getString(R.string.player_total)
                                          .concat(" ")
                                          .concat(String.valueOf(intTotalScore2)));

        if (intTotalScore1 >= 100) {
            playerWinner.setText(getResources().getString(R.string.player_winner)
                    .concat(" ")
                    .concat(player1.getText().toString()));

            game.setWinner(player1.getText().toString());
            game.setLooser(player2.getText().toString());
            game.setWinner_score(intTotalScore1);
            game.setLooser_score(intTotalScore2);

            saveGame.setEnabled(true);
        }

        if (intTotalScore2 >= 100) {
            playerWinner.setText(getResources().getString(R.string.player_winner)
                    .concat(" ")
                    .concat(player2.getText().toString()));

            game.setWinner(player2.getText().toString());
            game.setLooser(player1.getText().toString());
            game.setWinner_score(intTotalScore2);
            game.setLooser_score(intTotalScore1);

            saveGame.setEnabled(true);
        }

    }

    private void loadPlayers(String data) {
        game = new Game();
        currentPlayer = 0;
        breakLines = new StringBuilder("\r\n");
        gson = new Gson();
        domino = gson.fromJson(data, Domino.class);
        players = new ArrayList<>();
        intTotalScore1 = intTotalScore2 = 0;
        sharedPreferencesUtil = new SharedPreferencesUtil(activity, context);

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
        keyboardView.setPreviewEnabled(false);
        if (v != null)
            ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
        newScore.setText("");
        newScore.setVisibility(View.VISIBLE);

        saveGame.setVisibility(View.GONE);
        totalScore1.setVisibility(View.GONE);
        totalScore2.setVisibility(View.GONE);
    }

    public void hideKeyboard(){
        keyboardView.setVisibility(View.GONE);
        keyboardView.setEnabled(false);
        newScore.setVisibility(View.GONE);

        saveGame.setVisibility(View.VISIBLE);
        totalScore1.setVisibility(View.VISIBLE);
        totalScore2.setVisibility(View.VISIBLE);
    }

    public boolean isKeyboardOpen(){
        return keyboardView.getVisibility() == View.VISIBLE;
    }

    public void setListeners() {
        team1.setOnClickListener(new team1Listener());
        team2.setOnClickListener(new team2Listener());
        keyboardView.setOnKeyboardActionListener(new customKeyboardActionListener());
        saveGame.setOnClickListener(new saveGameListener());
    }

    public void initUI(){
        loadPlayers(intent.getStringExtra("domino"));

        team1 = (LinearLayout) findViewById(R.id.ll_score1);
        team2 = (LinearLayout) findViewById(R.id.ll_score2);

        activityGameLayout = (RelativeLayout) findViewById(R.id.rl_layout_activity_game);

        score1 = (TextView) findViewById(R.id.score_player1);
        score2 = (TextView) findViewById(R.id.score_player2);

        totalScore1 = (TextView) findViewById(R.id.total_player1);
        totalScore2 = (TextView) findViewById(R.id.total_player2);

        newScore = (TextView) findViewById(R.id.new_score);

        player1 = (TextView) findViewById(R.id.player1);
        player2 = (TextView) findViewById(R.id.player2);

        player1.setText(domino.getPlayer1());
        player2.setText(domino.getPlayer2());

        playerTurn = (TextView) findViewById(R.id.player_turn_name);
        playerWinner = (TextView) findViewById(R.id.player_winner);

        keyboard = new Keyboard(this, R.xml.custom_keyboard);
        keyboardView = (KeyboardView) findViewById(R.id.custom_keyboard);
        keyboardView.setKeyboard(keyboard);

        saveGame = (Button) findViewById(R.id.btn_save_game);
        saveGame.setEnabled(false);

        setListeners();

        updateTurn();

        initialized = true;
    }

    public class saveGameListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            games = sharedPreferencesUtil.getGames();
            if (games != null)
                games.getGames().add(game);
            else {
                List<Game> list = new ArrayList<>();
                list.add(game);
                games = new Games();
                games.setGames(list);
            }
            sharedPreferencesUtil.saveGames(gson.toJson(games));
            finish();
        }
    }

    public class team1Listener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            currentScoreBoard = 1;
            openKeyboard(view);
        }
    }

    public class team2Listener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            currentScoreBoard = 2;
            openKeyboard(view);
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
                if (!score.isEmpty())
                    newScore.setText(score.substring(0, score.length() - 1));
            } else if (i == 100){
                hideKeyboard();
                String addScore = newScore.getText().toString();
                if (addScore.length() > 2){
                    hideKeyboard();
                    return;
                }
                String previousScore = null;
                if (currentScoreBoard == 1) {
                    previousScore = score1.getText().toString();
                    score1.setText(previousScore.concat(breakLines.toString()).concat(addScore));
                    intTotalScore1 += Integer.valueOf(addScore);
                } else if (currentScoreBoard == 2) {
                    previousScore = score2.getText().toString();
                    score2.setText(previousScore.concat(breakLines.toString()).concat(addScore));
                    intTotalScore2 += Integer.valueOf(addScore);
                }
                updateTotal();
                updateTurn();
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
    public void onBackPressed(){
        if (isKeyboardOpen())
            hideKeyboard();
        else
            finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        intent = getIntent();
        activity = GameActivity.this;
        context = activity.getApplicationContext();
        initUI();
    }

}
