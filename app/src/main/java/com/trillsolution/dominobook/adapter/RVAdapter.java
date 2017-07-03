package com.trillsolution.dominobook.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trillsolution.dominobook.R;
import com.trillsolution.dominobook.model.Domino;
import com.trillsolution.dominobook.model.Game;
import com.trillsolution.dominobook.model.Games;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DominoViewHolder> {

    private List<Game> games;

    public RVAdapter(Games games) {
        this.games = games.getGames();
    }

    @Override
    public DominoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        DominoViewHolder dominoViewHolder = new DominoViewHolder(view);
        return dominoViewHolder;
    }

    @Override
    public void onBindViewHolder(DominoViewHolder dominoViewHolder, int position) {
        String winner_score = Integer.toString(games.get(position).getWinner_score());
        String looser_score = Integer.toString(games.get(position).getLooser_score());
        dominoViewHolder.winner_score.setText(winner_score);
        dominoViewHolder.looser_score.setText(looser_score);
        dominoViewHolder.winner.setText(games.get(position).getWinner());
        dominoViewHolder.looser.setText(games.get(position).getLooser());

    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class DominoViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView winner;
        TextView looser;
        TextView winner_score;
        TextView looser_score;

        public DominoViewHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.cv);
            this.winner = (TextView) itemView.findViewById(R.id.winner);
            this.looser = (TextView) itemView.findViewById(R.id.looser);
            this.winner_score = (TextView) itemView.findViewById(R.id.winner_score);
            this.looser_score = (TextView) itemView.findViewById(R.id.looser_score);
        }
    }

}
