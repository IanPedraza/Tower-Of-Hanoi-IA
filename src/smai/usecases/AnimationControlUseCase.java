package smai.usecases;

import smai.data.animations.AnimationPanel;
import smai.data.repositories.AnimationRepository;
import smai.domain.Response;

public class AnimationControlUseCase {

    private final AnimationRepository repository;

    public AnimationControlUseCase(AnimationRepository repository) {
        this.repository = repository;
    }

    public void play(Response response, AnimationPanel canvas) {
        this.repository.play(response, canvas);
    }

    public void play() {
        this.repository.play();
    }

    public void pause() {
        this.repository.pause();
    }
}
