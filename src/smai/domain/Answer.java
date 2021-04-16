package smai.domain;

import java.util.ArrayList;

public class Answer {
    private boolean hasAnswer;
    private Instance instance;
    private ArrayList<State> way;
    private long elapsedTime;

    public Answer(boolean hasAnswer, Instance instance, ArrayList<State> way) {
        this.hasAnswer = hasAnswer;
        this.instance = instance;
        this.way = way;
        this.elapsedTime = 0L;
    }
    
    public Answer(Instance instance) {
        this.hasAnswer = false;
        this.way = new ArrayList();
        this.instance = instance;
        this.elapsedTime = 0L;
    }
    
    public Answer() {
        this.hasAnswer = false;
        this.way = new ArrayList();
        this.instance = null;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
    
     public long getElapsedTimeSeconds() {
        return (elapsedTime / 1000) % 60;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public boolean hasAnswer() {
        return hasAnswer;
    }

    public void setHasAnswer(boolean hasAnswer) {
        this.hasAnswer = hasAnswer;
    }

    public ArrayList<State> getWay() {
        return way;
    }

    public void addWay(State way) {
        this.way.add(way);
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }
    
}
