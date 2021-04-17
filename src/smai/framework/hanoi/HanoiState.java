package smai.framework.hanoi;

import smai.domain.State;

public class HanoiState extends State {

    private HanoiTower aTower, bTower, cTower;

    public HanoiState() {
        this.aTower = new HanoiTower();
        this.bTower = new HanoiTower();
        this.cTower = new HanoiTower();
    }
    
    public HanoiState(HanoiState hanoiState) {
        this.aTower = new HanoiTower(hanoiState.getATower());
        this.bTower = new HanoiTower(hanoiState.getBTower());
        this.cTower = new HanoiTower(hanoiState.getCTower());
    }


    public HanoiState(int disksOnA, int disksOnB, int disksOnC) {
        this.aTower = new HanoiTower(disksOnA);
        this.bTower = new HanoiTower(disksOnB);
        this.cTower = new HanoiTower(disksOnC);
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

    public HanoiTower getATower() {
        return aTower;
    }

    public void setATower(HanoiTower aTower) {
        this.aTower = aTower;
    }

    public HanoiTower getBTower() {
        return bTower;
    }

    public void setBTower(HanoiTower bTower) {
        this.bTower = bTower;
    }

    public HanoiTower getCTower() {
        return cTower;
    }

    public void setCTower(HanoiTower cTower) {
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
    public State copy()  {
        return new HanoiState(this);
    }    
    
}
