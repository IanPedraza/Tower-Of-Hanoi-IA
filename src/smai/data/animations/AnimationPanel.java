package smai.data.animations;

import java.awt.Graphics;
import javax.swing.JPanel;
import smai.domain.Instance;
import smai.domain.Node;

public abstract class AnimationPanel extends JPanel {

    protected Node step;
    protected Instance instance;

    public final void render(Node step, Instance instance) {
        this.step = step;
        this.instance = instance;
        this.repaint();
    }

    @Override
    protected abstract void paintComponent(Graphics g);

}
