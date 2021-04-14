package towerofhanoiia.data;

import towerofhanoiia.data.domain.Answer;
import towerofhanoiia.data.domain.State;

public class HanoiRepository {
    private final HanoiLocalDataSource localDataSource;

    public HanoiRepository(HanoiLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }
    
    public void resolve(State initialState, int numberOfDisks, Callback<Answer> callback) {
        this.localDataSource.resolve(initialState, numberOfDisks, callback);
    }
    
}
