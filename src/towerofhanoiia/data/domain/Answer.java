package towerofhanoiia.data.domain;

import java.util.ArrayList;

public class Answer {
    private boolean hasAnswer;
    private ArrayList<State> way;

    public Answer(boolean hasAnswer, ArrayList<State> way) {
        this.hasAnswer = hasAnswer;
        this.way = way;
    }
    
    public Answer() {
        this.hasAnswer = false;
        this.way = new ArrayList();
    }

    public boolean isHasAnswer() {
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
    
}
