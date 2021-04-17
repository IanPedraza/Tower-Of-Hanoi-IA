package smai.usecases;

import smai.data.repositories.AnimationRepository;

public class PauseAnimationUseCase {
    private final AnimationRepository repository;

    public PauseAnimationUseCase(AnimationRepository repository) {
        this.repository = repository;
    }
    
    public void invoke() {
        this.repository.pause();
    }
}
