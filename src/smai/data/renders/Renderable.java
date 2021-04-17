package smai.data.renders;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import smai.data.datasources.AnimationDataSource;
import smai.domain.Answer;

public abstract class Renderable extends Thread implements AnimationDataSource {

    private int fps;
    private int delay;
    private boolean isPaused;
    private JPanel canvas;

    public Renderable() {
        this.fps = 60;
        this.isPaused = false;
        this.canvas = null;
        setSleep();
    }

    protected abstract void render(JPanel canvas);

    public void setFps(int fps) {
        this.fps = fps;
        setSleep();
    }

    private void setSleep() {
        this.delay = (int) (1000 / fps);
    }

    private void sleep() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            // ignored
        }
    }

    private void clear() {
        Graphics graphics = canvas.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    @Override
    public void play(Answer answer, JPanel panel) {
        this.isPaused = false;
        this.canvas = panel;
        this.start();
    }

    @Override
    public void puase() {
        this.isPaused = true;
    }

    @Override
    public void run() {
        while (!isPaused) {   
            clear();
            render(canvas);
            sleep();
        }
    }

}
