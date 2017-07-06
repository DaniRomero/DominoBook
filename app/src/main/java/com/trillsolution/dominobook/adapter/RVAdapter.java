package com.trillsolution.dominobook.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.trillsolution.dominobook.R;
import com.trillsolution.dominobook.model.Game;
import com.trillsolution.dominobook.model.Games;
import com.trillsolution.dominobook.preferences.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DominoViewHolder> {

    private List<Game> gameList;
    SharedPreferencesUtil preferencesUtil;
    Context context;
    Activity activity;
    Gson gson;
    Games games;

    TextView winnerTeam;
    TextView looserTeam;
    TextView winnerScores;
    TextView looserScores;


    public RVAdapter(Activity activity, Context context) {
        this.context = context;
        this.activity = activity;
        gson = new Gson();
        preferencesUtil = new SharedPreferencesUtil(activity, context);
        games = preferencesUtil.getGames();
        if (games != null)
            this.gameList = games.getGames();
        else
            this.gameList = new ArrayList<>();
    }

    @Override
    public DominoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        DominoViewHolder dominoViewHolder = new DominoViewHolder(view);
        return dominoViewHolder;
    }

    @Override
    public void onBindViewHolder(DominoViewHolder dominoViewHolder, int position) {
        String winner_score = Integer.toString(gameList.get(position).getWinner_score());
        String looser_score = Integer.toString(gameList.get(position).getLooser_score());
        dominoViewHolder.winner_score.setText(winner_score);
        dominoViewHolder.looser_score.setText(looser_score);
        dominoViewHolder.winner.setText(gameList.get(position).getWinner());
        dominoViewHolder.looser.setText(gameList.get(position).getLooser());
        dominoViewHolder.btnDelete.setOnClickListener(new deleteOnClickListener(position));
        dominoViewHolder.cardView.setOnClickListener(new OnCardViewClickListener(position));
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateAdapter(){
        games = preferencesUtil.getGames();
        if (games == null)
            games = new Games();
        this.gameList = games.getGames();
        this.notifyDataSetChanged();
    }

    class DominoViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView winner;
        TextView looser;
        TextView winner_score;
        TextView looser_score;
        Button btnDelete;

        public DominoViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            winner = (TextView) itemView.findViewById(R.id.winner);
            looser = (TextView) itemView.findViewById(R.id.looser);
            winner_score = (TextView) itemView.findViewById(R.id.winner_score);
            looser_score = (TextView) itemView.findViewById(R.id.looser_score);
            btnDelete = (Button) itemView.findViewById(R.id.btn_delete);
        }

    }

    public class deleteOnClickListener implements View.OnClickListener {

        int pos;

        deleteOnClickListener(int pos){
            this.pos = pos;
        }

        @Override
        public void onClick(View view) {
            gameList.remove(pos);
            games.setGames(gameList);
            preferencesUtil.saveGames(gson.toJson(games));
            updateAdapter();
        }
    }

    public class OnCardViewClickListener implements View.OnClickListener {

        int pos;

        OnCardViewClickListener(int pos){
            this.pos = pos;
        }

        @Override
        public void onClick(View view) {
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.dialog_game_detail);
            dialog.setTitle("Detail of a domino game...");
            dialog.getWindow().getAttributes().width = RecyclerView.LayoutParams.FILL_PARENT;
            dialog.setCancelable(true);

            winnerTeam = (TextView) dialog.findViewById(R.id.tv_winners_names);
            looserTeam = (TextView) dialog.findViewById(R.id.tv_loosers_names);
            winnerScores = (TextView) dialog.findViewById(R.id.tv_winners_scores);
            looserScores = (TextView) dialog.findViewById(R.id.tv_loosers_scores);

            Game currentGame = gameList.get(pos);
            StringBuilder breakLines = new StringBuilder("\r\n");

            winnerTeam.setText(currentGame.getWinner()
                                .concat(breakLines.toString())
                                .concat(currentGame.getWinner_partner()));

            looserTeam.setText(currentGame.getLooser()
                                .concat(breakLines.toString()
                                .concat(currentGame.getLooser_partner())));

            winnerScores.setText(currentGame.getWinner_scores());

            looserScores.setText(currentGame.getLooser_scores());

            dialog.show();

        }
    }

}
