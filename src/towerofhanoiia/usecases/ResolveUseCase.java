package towerofhanoiia.usecases;

import towerofhanoiia.data.Callback;
import towerofhanoiia.data.HanoiRepository;
import towerofhanoiia.data.domain.State;


public class ResolveUseCase {
    private final HanoiRepository hanoiRepository;

    public ResolveUseCase(HanoiRepository hanoiRepository) {
        this.hanoiRepository = hanoiRepository;
    }
    
    public void invoke(State initialState, Callback callback) {
        this.hanoiRepository.resolve(initialState, callback);
    }
    
}
