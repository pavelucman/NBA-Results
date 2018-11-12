package com.semosedukacija.alrmithreds.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Game implements Parcelable {

    String homeTeam;
    String awayTeam;
    int pointH;
    int pointA;
    String currentResult;
    Date dateStart;

    public Game() {
    }

    public Game(String homeTeam, String awayTeam, int pointH, int pointA, String currentResult, Date dateStart) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.pointH = pointH;
        this.pointA = pointA;
        this.currentResult = currentResult;
        this.dateStart = dateStart;
    }
    public Game(String homeTeam, String awayTeam, int pointH, int pointA, Date dateStart) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.pointH = pointH;
        this.pointA = pointA;
        this.dateStart = dateStart;
    }

    protected Game(Parcel in) {
        homeTeam = in.readString();
        awayTeam = in.readString();
        pointH = in.readInt();
        pointA = in.readInt();
        currentResult = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getPointH() {
        return pointH;
    }

    public void setPointH(int pointH) {
        this.pointH = pointH;
    }

    public int getPointA() {
        return pointA;
    }

    public void setPointA(int pointA) {
        this.pointA = pointA;
    }

    public String getCurrentResult() {
        return currentResult;
    }

    public String getResult() {
        return pointH + " : " + pointA;
    }

    public void setCurrentResult(String currentResult) {
        this.currentResult = currentResult;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(homeTeam);
        dest.writeString(awayTeam);
        dest.writeInt(pointH);
        dest.writeInt(pointA);
        dest.writeString(currentResult);
    }
}
