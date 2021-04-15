package smai.data;

import javax.swing.JPanel;
import smai.domain.Answer;

public class AnimationRepository {
    private final AnimationDataSource dataSource;

    public AnimationRepository(AnimationDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void play(Answer answer, JPanel panel) {
        this.dataSource.play(answer, panel);
    }
}
