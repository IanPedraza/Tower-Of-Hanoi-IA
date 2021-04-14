package towerofhanoiia.data;

import towerofhanoiia.data.domain.Answer;
import towerofhanoiia.data.domain.State;

public interface HanoiLocalDataSource {
    public void resolve(State initialState, int numberOfDisks, Callback<Answer> callback);
}
