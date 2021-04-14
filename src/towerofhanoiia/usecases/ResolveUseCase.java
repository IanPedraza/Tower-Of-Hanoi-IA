package towerofhanoiia.usecases;

import towerofhanoiia.data.Callback;
import towerofhanoiia.data.HanoiRepository;
import towerofhanoiia.data.domain.State;


public class ResolveUseCase {
    private final HanoiRepository hanoiRepository;

    public ResolveUseCase(HanoiRepository hanoiRepository) {
        this.hanoiRepository = hanoiRepository;
    }
    
    public void invoke(State initialState, int numberOfDisks, Callback callback) {
        this.hanoiRepository.resolve(initialState, numberOfDisks, callback);
    }
    
}
