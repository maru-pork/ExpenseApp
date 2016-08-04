package com.yramnna.expenseapp.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yramnna.expenseapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rufo on 8/3/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.CardViewHolder> {

    private List<CardModel> cardModels = new ArrayList<>();
    private Context context;

    public class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView description;
        ImageView iconId;

        public CardViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cv = (CardView) itemView.findViewById(R.id.cv);

            this.title = (TextView) itemView.findViewById(R.id.card_title);
            this.description = (TextView) itemView.findViewById(R.id.card_description);
            this.iconId = (ImageView) itemView.findViewById(R.id.card_icon);

            cv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            cv.setCardBackgroundColor(Color.parseColor("#228B22")); //TODO use resource
                            break;
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            cv.setCardBackgroundColor(context.getResources().getColor(R.color.cardview_light_background));
                            break;
                    }
                    return false;
                }
            });
        }
    }

    public MenuAdapter(List<CardModel> cardModels) {
        this.cardModels = cardModels;
    }

    @Override
    public int getItemCount() {
        return cardModels.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        cardViewHolder.description.setText(cardModels.get(i).getDescription());
        cardViewHolder.title.setText(cardModels.get(i).getTitle());
        cardViewHolder.iconId.setImageResource(cardModels.get(i).getIconId());

        switch (i) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
