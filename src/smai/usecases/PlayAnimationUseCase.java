package smai.usecases;

import javax.swing.JPanel;
import smai.data.renders.AnimationPanel;
import smai.data.repositories.AnimationRepository;
import smai.domain.Answer;


public class PlayAnimationUseCase {
    private final AnimationRepository repository;

    public PlayAnimationUseCase(AnimationRepository repository) {
        this.repository = repository;
    }
    
    public void invoke(Answer answer, AnimationPanel canvas) {
        this.repository.play(answer, canvas);
    }  
    
    public void invoke() {
        this.repository.play();
    }  
}
