package smai.framework.hanoi;

import smai.domain.Operator;
import smai.domain.State;
import smai.domain.Successor;

public class HanoiOperators {

    private static State Factory(HanoiState currentState, HanoiTower origin, HanoiTower destiny) {
        if (origin.isEmpty()) {
            return null;
        }

        if (destiny.isEmpty()) {
            int disk = origin.removeTop();
            destiny.addDisk(0, disk);
            return currentState;
        }

        if (origin.getTop() < destiny.getTop()) {
            int disk = origin.removeTop();
            destiny.addDisk(0, disk);
            return currentState;
        } else {
            return null;
        }
    }

    private static final Operator MOVE_AB = new Operator() {

        @Override
        public Successor apply(State state) {
            if (state instanceof HanoiState) {
                HanoiState hanoiState = (HanoiState) state.copy();
                State resultState =  HanoiOperators.Factory(hanoiState, hanoiState.getATower(), hanoiState.getBTower());
                return new Successor(resultState, this);
            }

            return null;
        }

        @Override
        public String getName() {
            return "MOVE_AB";
        }

    };

    private static final Operator MOVE_BA = new Operator() {

        @Override
        public Successor apply(State state) {
            if (state instanceof HanoiState) {
                HanoiState hanoiState = (HanoiState) state.copy();
                State resultState =  HanoiOperators.Factory(hanoiState, hanoiState.getBTower(), hanoiState.getATower());
                return new Successor(resultState, this);
            }

            return null;
        }

        @Override
        public String getName() {
            return "MOVE_BA";
        }

    };

    private static final Operator MOVE_AC = new Operator() {

        @Override
        public Successor apply(State state) {
            if (state instanceof HanoiState) {
                HanoiState hanoiState = (HanoiState) state.copy();
                State resultState =  HanoiOperators.Factory(hanoiState, hanoiState.getATower(), hanoiState.getCTower());
                return new Successor(resultState, this);
            }

            return null;
        }

        @Override
        public String getName() {
            return "MOVE_AC";
        }

    };

    private static final Operator MOVE_CA = new Operator() {

        @Override
        public Successor apply(State state) {
            if (state instanceof HanoiState) {
                HanoiState hanoiState = (HanoiState) state.copy();
                State resultState =  HanoiOperators.Factory(hanoiState, hanoiState.getCTower(), hanoiState.getATower());
                return new Successor(resultState, this);
            }

            return null;
        }

        @Override
        public String getName() {
            return "MOVE_CA";
        }

    };

    private static final Operator MOVE_BC = new Operator() {

        @Override
        public Successor apply(State state) {
            if (state instanceof HanoiState) {
                HanoiState hanoiState = (HanoiState) state.copy();
                State resultState =  HanoiOperators.Factory(hanoiState, hanoiState.getBTower(), hanoiState.getCTower());
                return new Successor(resultState, this);
            }

            return null;
        }

        @Override
        public String getName() {
            return "MOVE_BC";
        }

    };

    private static final Operator MOVE_CB = new Operator() {

        @Override
        public Successor apply(State state) {
            if (state instanceof HanoiState) {
                HanoiState hanoiState = (HanoiState) state.copy();
                State resultState =  HanoiOperators.Factory(hanoiState, hanoiState.getCTower(), hanoiState.getBTower());
                return new Successor(resultState, this);
            }

            return null;
        }

        @Override
        public String getName() {
            return "MOVE_CB";
        }

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
