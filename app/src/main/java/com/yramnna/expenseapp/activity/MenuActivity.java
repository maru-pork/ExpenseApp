package com.yramnna.expenseapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yramnna.expenseapp.R;
import com.yramnna.expenseapp.util.CardModel;
import com.yramnna.expenseapp.util.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rufo on 8/1/2016.
 */
public class MenuActivity extends BaseActivity {

    private RecyclerView rvRecyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        rvRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        rvLayoutManager = new LinearLayoutManager(this);
        rvAdapter = new MenuAdapter(getCardViews());

        rvRecyclerView.setHasFixedSize(true);
        rvRecyclerView.setLayoutManager(rvLayoutManager);
        rvRecyclerView.setAdapter(rvAdapter);

        super.initializeToolbar();
        super.setCurrentContext(this);
    }

    @Override
    public void onBackPressed() {
        // intentionally do nothing
    }

    private List<CardModel> getCardViews() {
        List<CardModel> cardModels = new ArrayList<>();
        cardModels.add(new CardModel("Daily Expense Manager", "Record daily expenses", R.drawable.ic_closed_caption_black_24dp));
        cardModels.add(new CardModel("Credit Card Manager", "Record credit card transactions", R.drawable.ic_closed_caption_black_24dp));
        cardModels.add(new CardModel("Monthly Budget Manager", "Budget and stuffs", R.drawable.ic_closed_caption_black_24dp));
        cardModels.add(new CardModel("Emergency Fund Manager", "Status of Emergency Fund", R.drawable.ic_closed_caption_black_24dp));
        cardModels.add(new CardModel("Investment Manager", "Tracking of investments", R.drawable.ic_closed_caption_black_24dp));
        cardModels.add(new CardModel("Grocery Item Manager", "List of monthly grocery items", R.drawable.ic_closed_caption_black_24dp));
        cardModels.add(new CardModel("Financial Goals Manager", "On-hand financial goals", R.drawable.ic_closed_caption_black_24dp));
        cardModels.add(new CardModel("Excel", "Download to Excel", R.drawable.ic_closed_caption_black_24dp));
        return cardModels;
    }
}
