package smai.framework.hanoi;

import smai.domain.Operator;
import smai.domain.State;

public class HanoiOperators {

    private static State move(HanoiState hanoiState, Tower origin, Tower destiny) {

        if (origin.isEmpty()) {
            return null;
        }

        if (destiny.isEmpty()) {
            Disk disk = origin.removeTop();
            destiny.addDisk(0, disk);
            return hanoiState;
        }

        if (origin.getTop().lowerThan(destiny.getTop())) {
            Disk disk = origin.removeTop();
            destiny.addDisk(0, disk);
            return hanoiState;
        } else {
            return null;
        }
    }

    private static Operator MOVE_AB = (State state) -> {
        if (state instanceof HanoiState) {
            HanoiState hanoiState = (HanoiState) state.clone();
            return HanoiOperators.move(hanoiState, hanoiState.getATower(), hanoiState.getBTower());
        }

        return null;
    };

    private static Operator MOVE_BA = (State state) -> {
        if (state instanceof HanoiState) {
            HanoiState hanoiState = (HanoiState) state.clone();
            return HanoiOperators.move(hanoiState, hanoiState.getATower(), hanoiState.getBTower());
        }

        return null;
    };

    private static Operator MOVE_AC = (State state) -> {
        if (state instanceof HanoiState) {
            HanoiState hanoiState = (HanoiState) state.clone();
            return HanoiOperators.move(hanoiState, hanoiState.getCTower(), hanoiState.getATower());
        }

        return null;
    };

    private static Operator MOVE_CA = (State state) -> {
        if (state instanceof HanoiState) {
            HanoiState hanoiState = (HanoiState) state.clone();
            return HanoiOperators.move(hanoiState, hanoiState.getCTower(), hanoiState.getATower());
        }

        return null;
    };

    private static Operator MOVE_BC = (State state) -> {
        if (state instanceof HanoiState) {
            HanoiState hanoiState = (HanoiState) state.clone();
            return HanoiOperators.move(hanoiState, hanoiState.getBTower(), hanoiState.getCTower());
        }

        return null;
    };

    private static Operator MOVE_CB = (State state) -> {
        if (state instanceof HanoiState) {
            HanoiState hanoiState = (HanoiState) state.clone();
            return HanoiOperators.move(hanoiState, hanoiState.getCTower(), hanoiState.getBTower());
        }

        return null;
    };

    public static Operator[] OPERATORS = {
        HanoiOperators.MOVE_AB,
        HanoiOperators.MOVE_BA,
        HanoiOperators.MOVE_AC,
        HanoiOperators.MOVE_CA,
        HanoiOperators.MOVE_BC,
        HanoiOperators.MOVE_CB
    };
}
