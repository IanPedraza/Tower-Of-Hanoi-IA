package smai.data;

import javax.swing.JPanel;
import smai.domain.Answer;

public interface AnimationDataSource {
    public void play(Answer answer, JPanel panel);
}
