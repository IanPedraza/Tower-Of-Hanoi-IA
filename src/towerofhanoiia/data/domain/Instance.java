package towerofhanoiia.data.domain;

public class Instance {
    private State initialState;
    private int numberOfDisks;
    private String towerGoal;

    public Instance() {
        this.initialState = new State();
        this.numberOfDisks = 0;
        this.towerGoal = "A";
    }

    public Instance(State initialState, int numberOfDisks, String towerGoal) {
        this.initialState = initialState;
        this.numberOfDisks = numberOfDisks;
        this.towerGoal = towerGoal;
    }

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public int getNumberOfDisks() {
        return numberOfDisks;
    }

    public void setNumberOfDisks(int numberOfDisks) {
        this.numberOfDisks = numberOfDisks;
    }

    public String getTowerGoal() {
        return towerGoal;
    }

    public void setTowerGoal(String towerGoal) {
        this.towerGoal = towerGoal;
    }

}
