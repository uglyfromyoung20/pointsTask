package org.example;

import javax.persistence.*;


public class PairPoints {
    int id;

    double firstX;

    double firstY;

    double secondX;

    double secondY;

    double firstZ;

    double secondZ;

    double distance;

    public PairPoints(int id , double firstX, double firstY, double secondX, double secondY , double distance) {
        this.id = id;
        this.firstX = firstX;
        this.firstY = firstY;
        this.secondX = secondX;
        this.secondY = secondY;
        this.distance = distance;
    }

    public PairPoints() {

    }

    public double getFirstX() {
        return firstX;
    }

    public void setFirstX(double firstX) {
        this.firstX = firstX;
    }

    public double getFirstY() {
        return firstY;
    }

    public void setFirstY(double firstY) {
        this.firstY = firstY;
    }

    public double getSecondX() {
        return secondX;
    }

    public void setSecondX(double secondX) {
        this.secondX = secondX;
    }

    public double getSecondY() {
        return secondY;
    }

    public void setSecondY(double secondY) {
        this.secondY = secondY;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFirstZ() {
        return firstZ;
    }

    public void setFirstZ(double firstZ) {
        this.firstZ = firstZ;
    }

    public double getSecondZ() {
        return secondZ;
    }

    public void setSecondZ(double secondZ) {
        this.secondZ = secondZ;
    }

    @Override
    public String toString() {
        return "PairPoints{" +
                "id=" + id +
                ", firstX=" + firstX +
                ", firstY=" + firstY +
                ", secondX=" + secondX +
                ", secondY=" + secondY +
                ", firstZ=" + firstZ +
                ", secondZ=" + secondZ +
                ", distance=" + distance +
                '}';
    }
}

