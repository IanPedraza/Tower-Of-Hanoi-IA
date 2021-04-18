package smai.usecases;

import smai.data.renders.AnimationPanel;
import smai.data.repositories.AnimationRepository;
import smai.domain.Answer;


public class AnimationControlUseCase {
    private final AnimationRepository repository;

    public AnimationControlUseCase(AnimationRepository repository) {
        this.repository = repository;
    }
    
    public void play(Answer answer, AnimationPanel canvas) {
        this.repository.play(answer, canvas);
    }  
    
    public void play() {
        this.repository.play();
    }  
    
    public void pause() {
        this.repository.pause();
    }
}
