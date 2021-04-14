package towerofhanoiia.data;

import towerofhanoiia.data.domain.State;

public interface HanoiLocalDataSource {
    public void resolve(State initialState, Callback callback);
}
