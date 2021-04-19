package smai.framework.hanoi;

import smai.domain.Heuristic;
import smai.domain.Operator;
import smai.domain.State;

public class HanoiHeuristic implements Heuristic {

    @Override
    public int getHeuristic(State currentState) {
        HanoiState hanoiState = (HanoiState) currentState;
        return hanoiState.getATower().length() + hanoiState.getBTower().length();
    }

    @Override
    public int getCostOfApplyOperator(State currentState, Operator operator) {
        return 1;
    }
    
}
