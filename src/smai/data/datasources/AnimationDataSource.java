package smai.data.datasources;

import javax.swing.JPanel;
import smai.domain.Answer;

public interface AnimationDataSource {
    public void play(Answer answer, JPanel panel);
    public void puase();
}
