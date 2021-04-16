package smai.framework.hanoi;

import smai.domain.State;

public class HanoiState implements State {

    private Tower aTower, bTower, cTower;

    public HanoiState() {
        this.aTower = new Tower();
        this.bTower = new Tower();
        this.cTower = new Tower();
    }
    
    public HanoiState(HanoiState hanoiState) {
        this.aTower = new Tower(hanoiState.getATower());
        this.bTower = new Tower(hanoiState.getBTower());
        this.cTower = new Tower(hanoiState.getCTower());
    }


    public HanoiState(int disksOnA, int disksOnB, int disksOnC) {
        this.aTower = new Tower(disksOnA);
        this.bTower = new Tower(disksOnB);
        this.cTower = new Tower(disksOnC);
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

    public Tower getATower() {
        return aTower;
    }

    public void setATower(Tower aTower) {
        this.aTower = aTower;
    }

    public Tower getBTower() {
        return bTower;
    }

    public void setBTower(Tower bTower) {
        this.bTower = bTower;
    }

    public Tower getCTower() {
        return cTower;
    }

    public void setCTower(Tower cTower) {
        this.cTower = cTower;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HanoiState) {
            HanoiState hanoiState = (HanoiState) obj;
            
            return this.aTower.equals(hanoiState.aTower) && 
                    this.bTower.equals(hanoiState.bTower) && 
                    this.cTower.equals(hanoiState.cTower);
        }
        
        return false;
    }    
    

    @Override
    public String toString() {
        return "(" + this.aTower + "), " +
                "(" + this.bTower + "), " +
                "(" + this.cTower + ")";
    }

    @Override
    public State clone()  {
        return new HanoiState(this);
    }
    
    
    
}
