package smai.domain;

import java.util.ArrayList;

public abstract class State {
    public abstract State copy();

    public final ArrayList<Successor> getSuccessors(Operator[] operators) {
        ArrayList<Successor> successors = new ArrayList();

        for (Operator operator : operators) {
            Successor successor = operator.apply(this);

            if (successor.getState() != null) {
                successors.add(successor);
            }
        }

        return successors;
    }
}
