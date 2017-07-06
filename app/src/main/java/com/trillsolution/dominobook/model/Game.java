package com.trillsolution.dominobook.model;

/**
 * Created by danielromero on 2/7/17.
 */

public class Game {

    String winner;
    String winner_partner;
    String looser;
    String looser_partner;
    int winner_score;
    int looser_score;
    String winner_scores;
    String looser_scores;

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

    public String getWinner_partner() {
        return winner_partner;
    }

    public void setWinner_partner(String winner_partner) {
        this.winner_partner = winner_partner;
    }

    public String getLooser() {
        return looser;
    }

    public void setLooser(String looser) {
        this.looser = looser;
    }

    public String getLooser_partner() {
        return looser_partner;
    }

    public void setLooser_partner(String looser_partner) {
        this.looser_partner = looser_partner;
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

    public String getWinner_scores() {
        return winner_scores;
    }

    public void setWinner_scores(String winner_scores) {
        this.winner_scores = winner_scores;
    }

    public String getLooser_scores() {
        return looser_scores;
    }

    public void setLooser_scores(String looser_scores) {
        this.looser_scores = looser_scores;
    }
}
