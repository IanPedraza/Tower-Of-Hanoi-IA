package smai.usecases;

import javax.swing.JPanel;
import smai.data.AnimationRepository;
import smai.domain.Answer;


public class AnimateUseCase {
    private final AnimationRepository repository;

    public AnimateUseCase(AnimationRepository repository) {
        this.repository = repository;
    }
    
    public void invoke(Answer answer, JPanel panel) {
        this.repository.play(answer, panel);
    }
    
}
