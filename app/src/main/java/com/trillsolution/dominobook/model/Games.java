package com.trillsolution.dominobook.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielromero on 2/7/17.
 */

public class Games {

    List<Game> games;

    public Games(){
        games = new ArrayList<>();
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }


}
