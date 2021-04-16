package smai.common.utils;

public class TicToc {
    
    private static TicToc sharedInstance;
    private long startedTime;
    
    private TicToc() {
        this.startedTime = 0L;
    }
    
    public static TicToc getInstance() {
        if (sharedInstance == null) {
            sharedInstance = new TicToc();
        }
        
        return sharedInstance;
    }
    
    public void markStart() {
        this.startedTime = System.currentTimeMillis();
    }
    
    public final long getElapsedTime() {
        return System.currentTimeMillis() - this.startedTime;
    }
    
}
