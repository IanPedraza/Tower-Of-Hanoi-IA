package towerofhanoiia.data;

import towerofhanoiia.data.domain.Answer;
import towerofhanoiia.data.domain.Instance;

public class HanoiRepository {
    private final HanoiLocalDataSource localDataSource;

    public HanoiRepository(HanoiLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }
    
    public void resolve(Instance instance, Callback<Answer> callback) {
        this.localDataSource.resolve(instance, callback);
    }
    
}
