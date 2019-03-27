package com.disgrow.www.tictactoebypablof;

import android.widget.ImageView;

public class Position {

    private ImageView image;
    private int position;
    private String state;

    public Position(ImageView image, int position, String state) {
        this.image = image;
        this.position = position;
        this.state = state;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
