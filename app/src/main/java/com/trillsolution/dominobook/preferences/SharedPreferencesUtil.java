package com.trillsolution.dominobook.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.trillsolution.dominobook.model.Game;
import com.trillsolution.dominobook.model.Games;

import java.util.List;


public class SharedPreferencesUtil {

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
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("game", games);
        editor.commit();
    }

    public Games getGames(){
        String games = getData();
        return gson.fromJson(games, Games.class);
    }

    public String getData(){
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getString("game", "");
    }
}
