package smai.framework.hanoi;

import smai.domain.Instance;
import smai.domain.State;

public class HanoiInstance extends Instance {

    private int numberOfDisks;

    public HanoiInstance() {
        super(new HanoiState(), new HanoiState());
        this.numberOfDisks = 0;
    }

    public HanoiInstance(HanoiState initialState, HanoiState targetState, int numberOfDisks) {
        super(initialState, targetState);
        this.numberOfDisks = numberOfDisks;
    }

    public int getNumberOfDisks() {
        return numberOfDisks;
    }

    public void setNumberOfDisks(int numberOfDisks) {
        this.numberOfDisks = numberOfDisks;
    }

    @Override
    public void setTargetState(State targetState) {
        if (targetState instanceof HanoiState) {
            super.setTargetState((HanoiState) targetState);
        }
    }

    @Override
    public State getTargetState() {
        return super.getTargetState();
    }

    @Override
    public void setInitialState(State initialState) {
        if (initialState instanceof HanoiState) {
            super.setTargetState((HanoiState) initialState);
        }
    }

    @Override
    public State getInitialState() {
        return super.getInitialState();
    }

}
