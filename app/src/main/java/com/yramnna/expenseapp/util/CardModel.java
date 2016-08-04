package com.yramnna.expenseapp.util;

/**
 * Created by Rufo on 8/3/2016.
 */
public class CardModel {

    private String title;
    private String description;
    private int iconId;

    public CardModel(String title, String description, int iconId) {
        this.title = title;
        this.description = description;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
