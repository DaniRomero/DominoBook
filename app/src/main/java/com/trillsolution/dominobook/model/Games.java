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
        Game game1 = new Game("daniel",
                "yudis",
                100,
                50);
        Game game2 = new Game("daniel",
                "ydis",
                100,
                50);
        Game game3 = new Game("daniel",
                "yuds",
                100,
                50);
        games.add(game1);
        games.add(game2);
        games.add(game3);
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }


}
