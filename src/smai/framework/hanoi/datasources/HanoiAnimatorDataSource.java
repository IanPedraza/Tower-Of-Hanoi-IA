package smai.framework.hanoi.datasources;

import javax.swing.JPanel;
import smai.data.datasources.AnimationDataSource;
import smai.domain.Answer;

public class HanoiAnimatorDataSource implements AnimationDataSource {

    @Override
    public void play(Answer answer, JPanel panel) {
        // Animate answer
        System.out.println("Animated");
    }
    
    
}
