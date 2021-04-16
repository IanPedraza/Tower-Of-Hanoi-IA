package smai.framework.hanoi;

import java.util.LinkedList;

public class HanoiTower {

    private LinkedList<Integer> disks;

    public HanoiTower() {
        this.disks = new LinkedList();
    }

    public HanoiTower(HanoiTower tower) {
        this.disks = new LinkedList();
        this.disks.addAll(tower.disks);
    }

    public HanoiTower(int numberOfDisks) {
        this.disks = new LinkedList();
        this.fill(numberOfDisks);
    }

    public boolean isEmpty() {
        return this.disks.isEmpty();
    }

    public int length() {
        return this.disks.size();
    }

    public LinkedList<Integer> getDisks() {
        return disks;
    }

    public void setDisks(LinkedList<Integer> disks) {
        this.disks = disks;
    }

    public void addDisk(int disk) {
        this.disks.add(disk);
    }
    
    public void addDisk(int index, int disk) {
        this.disks.add(index, disk);
    }

    public int removeDisk(int index) {
        return this.disks.remove(index);
    }

    public boolean removeDisk(Integer disk) {
        return this.disks.remove(disk);
    }

    public int getDisk(int index) {
        return this.disks.get(index);
    }

    public int getTop() {
        return this.disks.get(0);
    }

    public int removeTop() {
        return this.disks.remove(0);
    }

    public void fill(int numberOfDisks) {
        disks.clear();

        for (int index = 1; index <= numberOfDisks; index++) {
            this.addDisk(index);

        }
//
//        for (int index = numberOfDisks; index > 0; index--) {
//            this.addDisk(new Disk(index));
//        }
    }

    @Override
    public boolean equals(Object obj) {
        try {
            HanoiTower tower = (HanoiTower) obj;
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

        for (int item : this.disks) {
            value += item + " ";
        }

        return "" + value.trim();
    }

}
