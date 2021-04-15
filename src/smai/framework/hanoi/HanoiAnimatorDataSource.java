package smai.framework.hanoi;

import javax.swing.JPanel;
import smai.data.AnimationDataSource;
import smai.domain.Answer;

public class HanoiAnimatorDataSource implements AnimationDataSource {

    @Override
    public void play(Answer answer, JPanel panel) {
        // Animate answer
        System.out.println("Animated");
    }
    
    
}
