package smai.framework.hanoi;

import smai.domain.Instance;
import smai.domain.State;

public class HanoiInstance extends Instance {

    private int numberOfDisks;

    public HanoiInstance() {
        super(new HanoiState(), new HanoiState(), HanoiOperators.OPERATORS);
        this.numberOfDisks = 0;
    }

    public HanoiInstance(int numberOfDisks) {
        super(new HanoiState(numberOfDisks, 0, 0), new HanoiState(0, 0, numberOfDisks), HanoiOperators.OPERATORS);
        this.numberOfDisks = numberOfDisks;
    }

    public int getNumberOfDisks() {
        return numberOfDisks;
    }

    public void setNumberOfDisks(int numberOfDisks) {
        this.numberOfDisks = numberOfDisks;
    }

    @Override
    public State getFinalState() {
        return super.getFinalState();
    }

    @Override
    public State getInitialState() {
        return super.getInitialState();
    }

}
