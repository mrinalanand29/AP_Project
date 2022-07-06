package com.example.ap_project;

import javafx.util.Pair;

public class Ladder {
    private Pair<Integer, Integer> start;
    private Pair<Integer, Integer> end;

    public Pair<Integer, Integer> getEnd() {
        return end;
    }

    public Pair<Integer, Integer> getStart() {
        return start;
    }

    public void setEnd(Pair<Integer, Integer> end) {
        this.end = end;
    }

    public void setStart(Pair<Integer, Integer> start) {
        this.start = start;
    }
    public Ladder(Pair<Integer, Integer> start, Pair<Integer, Integer> end){
        this.start = start;
        this.end = end;
    }
}
