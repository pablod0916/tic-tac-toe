package com.disgrow.www.tictactoebypablof;

import android.widget.ImageView;

public class Player {

    private int id;
    private String name;
    private String exOrCircle;
    private boolean currentTurn;

    public Player(int id, String name, String exOrCircle, boolean currentTurn) {
        this.id = id;
        this.name = name;
        this.exOrCircle = exOrCircle;
        this.currentTurn = currentTurn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExOrCircle() {
        return exOrCircle;
    }

    public void setExOrCircle(String exOrCircle) {
        this.exOrCircle = exOrCircle;
    }

    public boolean isCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(boolean currentTurn) {
        this.currentTurn = currentTurn;
    }
}
