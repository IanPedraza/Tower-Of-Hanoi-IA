package smai.framework.hanoi;

import java.util.ArrayList;

public class Tower {
    
    private ArrayList<Disk> disks;
    
    public Tower() {
        this.disks = new ArrayList();
    }

    public ArrayList<Disk> getDisks() {
        return disks;
    }

    public void setDisks(ArrayList<Disk> disks) {
        this.disks = disks;
    }
    
    public void addDisk(Disk disk) {
        this.disks.add(disk);
    }
    
    public void removeDisk(Disk disk) {
        this.disks.remove(disk);
    }
    
    public void fill(int numberOfDisks) {
        disks.clear();
                
        for (int index = 1; index <= numberOfDisks; index++) {
            this.addDisk(new Disk(index));
        }
    }
    
}
