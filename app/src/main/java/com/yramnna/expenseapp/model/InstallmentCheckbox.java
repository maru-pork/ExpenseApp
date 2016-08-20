package com.yramnna.expenseapp.model;

/**
 * Created by Rufo on 8/20/2016.
 */
public class InstallmentCheckbox {

    private String name;
    private boolean selected;

    public InstallmentCheckbox(String name, boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
