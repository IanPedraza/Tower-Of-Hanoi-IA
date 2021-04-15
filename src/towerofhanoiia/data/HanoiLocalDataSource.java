package towerofhanoiia.data;

import towerofhanoiia.data.domain.Answer;
import towerofhanoiia.data.domain.Instance;

public interface HanoiLocalDataSource {
    public void resolve(Instance instance, Callback<Answer> callback);
}
