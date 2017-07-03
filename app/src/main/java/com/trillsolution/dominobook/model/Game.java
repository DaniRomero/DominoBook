package com.trillsolution.dominobook.model;

/**
 * Created by danielromero on 2/7/17.
 */

public class Game {

    String winner;
    String looser;
    int winner_score;
    int looser_score;

    public Game() {
    }

    public Game(String winner, String looser, int winner_score, int looser_score) {
        this.winner = winner;
        this.looser = looser;
        this.winner_score = winner_score;
        this.looser_score = looser_score;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLooser() {
        return looser;
    }

    public void setLooser(String looser) {
        this.looser = looser;
    }

    public int getWinner_score() {
        return winner_score;
    }

    public void setWinner_score(int winner_score) {
        this.winner_score = winner_score;
    }

    public int getLooser_score() {
        return looser_score;
    }

    public void setLooser_score(int looser_score) {
        this.looser_score = looser_score;
    }
}
