package com.ticket.digitick.model;

/**
 * Created by jitin on 02-04-2016.
 */
public class Trip {
    public String tripFrom;
    public String tripTo;
    public String date;

    public Trip() {
    }

    public String getTripFrom() {
        return tripFrom;
    }

    public void setTripFrom(String tripFrom) {
        this.tripFrom = tripFrom;
    }

    public String getTripTo() {
        return tripTo;
    }

    public void setTripTo(String tripTo) {
        this.tripTo = tripTo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
