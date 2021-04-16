package smai.framework.hanoi;

import java.util.ArrayList;

public class Tower {

    private ArrayList<Disk> disks;

    public Tower() {
        this.disks = new ArrayList();
    }

    public Tower(Tower tower) {
        this.disks = new ArrayList();
        this.disks.addAll(tower.disks);
    }

    public Tower(int numberOfDisks) {
        this.disks = new ArrayList();
        this.fill(numberOfDisks);
    }

    public boolean isEmpty() {
        return this.disks.isEmpty();
    }

    public int length() {
        return this.disks.size();
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
    
    public void addDisk(int index, Disk disk) {
        this.disks.add(index, disk);
    }

    public Disk removeDisk(int index) {
        return this.disks.remove(index);
    }

    public boolean removeDisk(Disk disk) {
        return this.disks.remove(disk);
    }

    public Disk getDisk(int index) {
        return this.disks.get(index);
    }

    public Disk getTop() {
        return this.disks.get(0);
    }

    public Disk removeTop() {
        return this.disks.remove(0);
    }

    public void fill(int numberOfDisks) {
        disks.clear();

        for (int index = 1; index <= numberOfDisks; index++) {
            this.addDisk(new Disk(index));

        }
//
//        for (int index = numberOfDisks; index > 0; index--) {
//            this.addDisk(new Disk(index));
//        }
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Tower tower = (Tower) obj;
            boolean response = true;

            for (int index = 0; index < this.disks.size(); index++) {
                if (!this.disks.get(index).equals(tower.disks.get(index))) {
                    response = false;
                    break;
                }
            }

            return response;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        String value = "";

        for (Disk item : this.disks) {
            value += item + " ";
        }

        return "" + value.trim();
    }

}
