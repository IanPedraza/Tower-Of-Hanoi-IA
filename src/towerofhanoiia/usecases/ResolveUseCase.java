package towerofhanoiia.usecases;

import towerofhanoiia.data.Callback;
import towerofhanoiia.data.HanoiRepository;
import towerofhanoiia.data.domain.Answer;
import towerofhanoiia.data.domain.Instance;


public class ResolveUseCase {
    private final HanoiRepository hanoiRepository;

    public ResolveUseCase(HanoiRepository hanoiRepository) {
        this.hanoiRepository = hanoiRepository;
    }
    
    public void invoke(Instance instance, Callback<Answer> callback) {
        this.hanoiRepository.resolve(instance, callback);
    }
    
}
