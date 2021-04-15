package smai.domain;

import java.util.ArrayList;

public class Answer {
    private boolean hasAnswer;
    private Instance instance;
    private ArrayList<State> way;

    public Answer(boolean hasAnswer, Instance instance, ArrayList<State> way) {
        this.hasAnswer = hasAnswer;
        this.instance = instance;
        this.way = way;
    }
    
    public Answer() {
        this.hasAnswer = false;
        this.way = new ArrayList();
        this.instance = null;
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

    public void setWay(ArrayList<State> way) {
        this.way = way;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }
    
}
