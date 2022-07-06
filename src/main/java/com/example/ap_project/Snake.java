package com.example.ap_project;

import javafx.util.Pair;

import java.util.ArrayList;

public class Snake{
    private ArrayList<Pair<Integer, Integer>> path;

    public ArrayList<Pair<Integer, Integer>> getPath() {
        return path;
    }

    public void setPath(ArrayList<Pair<Integer, Integer>> path) {
        this.path = path;
    }

    public Snake(ArrayList<Pair<Integer, Integer>> path){
        this.path = path;
    }



}
