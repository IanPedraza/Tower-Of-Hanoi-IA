package smai.framework.hanoi;

public class Disk {
    
    private final int numberOfDisk;

    public Disk(int numberOfDisk) {
        this.numberOfDisk = numberOfDisk;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj instanceof Disk) {
            Disk disk = (Disk) obj;
            return this.numberOfDisk == disk.numberOfDisk;
        }
        
        return false;
    }

    @Override
    public String toString() {
        return "" + this.numberOfDisk;
    }
    
    public boolean greaterThan(Disk disk) {
        return numberOfDisk > disk.numberOfDisk;
    }
    
    public boolean lowerThan(Disk disk) {
        return numberOfDisk < disk.numberOfDisk;
    }
    
    public boolean greaterEqualsThan(Disk disk) {
        return numberOfDisk >= disk.numberOfDisk;
    }
    
    public boolean lowerEqualsThan(Disk disk) {
        return numberOfDisk <= disk.numberOfDisk;
    }
    
}
