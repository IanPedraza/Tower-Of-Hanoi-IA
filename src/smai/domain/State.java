package smai.domain;

import java.util.LinkedList;

public abstract class State {
    public abstract State copy();

    public final LinkedList<Successor> getSuccessors(Operator[] operators) {
        LinkedList<Successor> successors = new LinkedList();

        for (Operator operator : operators) {
            Successor successor = operator.apply(this);

            if (successor.getState() != null) {
                successors.add(successor);
            }
        }

        return successors;
    }
}
