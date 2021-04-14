package towerofhanoiia.data.domain;


public class State {
    
    private Tower aTower, bTower, cTower;
    
    public State() {
        this.aTower = new Tower();
        this.bTower = new Tower();
        this.cTower = new Tower();
    }

    public State(Tower aTower, Tower bTower, Tower cTower) {
        this.aTower = aTower;
        this.bTower = bTower;
        this.cTower = cTower;
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
    
}
