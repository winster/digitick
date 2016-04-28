package com.winster.bo.vo;

/**
 * Created by wjose on 28/03/2016.
 */
public class StopDetails {

    private int position; // for positioning in recyclerview
    private int stop;
    private String description;

    public StopDetails(){
    }

    public StopDetails(int stop, String description){
        this.stop = stop;
        this.description = description;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
