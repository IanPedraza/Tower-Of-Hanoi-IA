package smai.framework.hanoi;

import smai.domain.State;

public class HanoiState implements State {

    private Tower aTower, bTower, cTower;

    public HanoiState() {
        this.aTower = new Tower();
        this.bTower = new Tower();
        this.cTower = new Tower();
    }

    public HanoiState(Tower aTower, Tower bTower, Tower cTower) {
        this.aTower = aTower;
        this.bTower = bTower;
        this.cTower = cTower;
    }

    public boolean instanceTower(String tower, int numberOfDisks) {
        switch (tower.toLowerCase()) {
            case "a":
                this.aTower.fill(numberOfDisks);
                break;

            case "b":
                this.bTower.fill(numberOfDisks);
                break;

            case "c":
                this.cTower.fill(numberOfDisks);
                break;

            default:
                return false;
        }

        return true;
    }

    public Tower getaTower() {
        return aTower;
    }

    public void setaTower(Tower aTower) {
        this.aTower = aTower;
    }

    public Tower getbTower() {
        return bTower;
    }

    public void setbTower(Tower bTower) {
        this.bTower = bTower;
    }

    public Tower getcTower() {
        return cTower;
    }

    public void setcTower(Tower cTower) {
        this.cTower = cTower;
    }

    @Override
    public State nextState() {
        // TODO: Next state
        return null;
    }

}
