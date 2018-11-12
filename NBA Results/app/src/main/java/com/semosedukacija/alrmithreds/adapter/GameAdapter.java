package com.semosedukacija.alrmithreds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.semosedukacija.alrmithreds.R;
import com.semosedukacija.alrmithreds.models.Game;

import java.util.List;

public class GameAdapter extends BaseAdapter {

    Context context;
    List<Game> listOfGames;

    public GameAdapter(Context context) {
        this.context = context;
    }

    public GameAdapter(Context context, List<Game> listOfGames) {
        this.context = context;
        this.listOfGames = listOfGames;
    }

    @Override
    public int getCount() {
        return listOfGames.size();
    }

    @Override
    public Object getItem(int i) {
        return listOfGames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = view;
        if(rowView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_game, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.textViewT1       = rowView.findViewById(R.id.text_hteam_name);
            viewHolder.textViewT2       = rowView.findViewById(R.id.text_ateam_name);
            viewHolder.textViewScoreT1  = rowView.findViewById(R.id.text_teamH_score);
            viewHolder.textViewScoreT2  = rowView.findViewById(R.id.text_teamA_score);
            viewHolder.textViewDateStart= rowView.findViewById(R.id.text_game_start);
            viewHolder.imageTeamLogo= rowView.findViewById(R.id.image_logo);

            rowView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) rowView.getTag();

        Game game = (Game) getItem(position);

        int rowNumber = position;
        viewHolder.textViewT1.setText(game.getHomeTeam());
        viewHolder.textViewT2.setText(game.getAwayTeam());
        viewHolder.textViewScoreT1.setText(game.getPointH().toString());
        viewHolder.textViewScoreT2.setText(game.getPointA() + "");
        viewHolder.textViewDateStart.setText(game.getDateStart().toString());

        Glide.with(context)
                .load("https://i.pinimg.com/originals/e6/9a/68/e69a6812dc00537ac204b0e47e507f01.png")

                .into(viewHolder.imageTeamLogo);
        return rowView;
    }

    public void updateItems(Context context, List<Game> listOfGames) {

        this.context = context;
        this.listOfGames = listOfGames;
        notifyDataSetChanged();
    }


    class ViewHolder{
        TextView textViewT1, textViewT2, textViewScoreT1, textViewScoreT2, textViewDateStart;
        ImageView imageTeamLogo;
    }
}
