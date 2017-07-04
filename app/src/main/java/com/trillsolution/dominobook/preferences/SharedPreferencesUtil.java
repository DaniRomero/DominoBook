package com.trillsolution.dominobook.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.trillsolution.dominobook.model.Game;
import com.trillsolution.dominobook.model.Games;

import java.util.List;


public class SharedPreferencesUtil extends AppCompatActivity {

    Activity activity;
    Context context;
    Gson gson;
    SharedPreferences sharedPreferences;

    public SharedPreferencesUtil(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        gson = new Gson();
    }

    public void saveGames(String games){
        sharedPreferences = context.getSharedPreferences("DominoBookData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("games", games);
        editor.commit();
    }

    public Games getGames(){
        String games = getData();
        return gson.fromJson(games, Games.class);
    }

    private String getData(){
        sharedPreferences = context.getSharedPreferences("DominoBookData", Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("games", null);
        return data;
    }
}
