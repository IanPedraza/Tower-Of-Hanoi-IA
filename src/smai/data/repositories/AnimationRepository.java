package smai.data.repositories;

import smai.data.datasources.AnimationDataSource;
import smai.data.animations.AnimationPanel;
import smai.domain.Response;

public class AnimationRepository {

    private AnimationDataSource dataSource;

    public AnimationRepository(AnimationDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(AnimationDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void play(Response response, AnimationPanel canvas) {
        this.dataSource.play(response, canvas);
    }

    public void play() {
        this.dataSource.play();
    }

    public void pause() {
        this.dataSource.pause();
    }
}
