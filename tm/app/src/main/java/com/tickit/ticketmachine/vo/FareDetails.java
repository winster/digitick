package com.tickit.ticketmachine.vo;

/**
 * Created by wjose on 28/03/2016.
 */
public class FareDetails {
    private int point;
    private double full;
    private double half;
    private double student;
    private double pass;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public double getFull() {
        return full;
    }

    public void setFull(double full) {
        this.full = full;
    }

    public double getHalf() {
        return half;
    }

    public void setHalf(double half) {
        this.half = half;
    }

    public double getStudent() {
        return student;
    }

    public void setStudent(double student) {
        this.student = student;
    }

    public double getPass() {
        return pass;
    }

    public void setPass(double pass) {
        this.pass = pass;
    }
}
