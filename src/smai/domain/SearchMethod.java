package smai.domain;

import java.util.ArrayList;

public class SearchMethod {
    
    public ArrayList<State> getSuccessors(State state, Operator[] operators) {
        ArrayList<State> successors = new ArrayList();
        
        for (Operator operator : operators) {
            State successor = operator.apply(state);
            
            if (successor != null) {
                successors.add(successor);
            }
        }
        
        return successors;
    }
    
    public long getElapsedTime(long initialTime) {
        long endTime = System.currentTimeMillis();
        return endTime - initialTime;
    }
    
}
