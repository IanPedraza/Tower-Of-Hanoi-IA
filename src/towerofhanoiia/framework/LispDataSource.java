package towerofhanoiia.framework;

import towerofhanoiia.data.Callback;
import towerofhanoiia.data.HanoiLocalDataSource;
import towerofhanoiia.data.domain.Answer;
import towerofhanoiia.data.domain.Instance;

public class LispDataSource implements HanoiLocalDataSource {

    @Override
    public void resolve(Instance instance, Callback<Answer> callback) {
        // TODO: Here get the data
        Answer answer = new Answer();
        answer.setHasAnswer(true);
        answer.setInstance(instance);
        
        callback.onSuccess(answer);
    }
    
}
