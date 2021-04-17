package smai.framework.hanoi.datasources;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import javax.swing.JPanel;
import smai.data.renders.StepperRenderable;
import smai.domain.Instance;
import smai.domain.Node;
import smai.framework.hanoi.HanoiInstance;
import smai.framework.hanoi.HanoiState;

public class HanoiAnimatorDataSource extends StepperRenderable {

    private final int margin = 20;
    private final double ratePostHeigth = 0.7;
    private final double ratePostWidth = 0.05;

    public HanoiAnimatorDataSource() {
        setFps(1);
    }

    @Override
    protected void render(JPanel canvas, Node step, Instance instance) {
        if (!(instance instanceof HanoiInstance) && !(step.getState() instanceof HanoiState)) {
            return;
        }

        HanoiInstance hanoiInstance = (HanoiInstance) instance;
        HanoiState hanoiState = (HanoiState) step.getState();

        Graphics2D graphics = (Graphics2D) canvas.getGraphics();
        int width = canvas.getWidth() - margin * 2;
        int height = canvas.getHeight() - margin * 2;

        /* POSTS */
        int spacePerPost = width / 3;

        int postHeigth = (int) (height * ratePostHeigth);
        int postWidth = (int) (spacePerPost * ratePostWidth);

        int postX = (int) ((width * 0.1) + (spacePerPost * 0.5));
        int aPostX = postX;
        int bPostX = postX * 2;
        int cPostX = postX * 3;

        int postY = height - postHeigth;

        paintPost(graphics, aPostX, postY, postWidth, postHeigth);
        paintPost(graphics, bPostX, postY, postWidth, postHeigth);
        paintPost(graphics, cPostX, postY, postWidth, postHeigth);

        /* DISKS */
        int baseDiskWidth = (int) (width * 0.05);
        int diskHeight = (int) ((postHeigth / hanoiInstance.getNumberOfDisks()) * 0.75);
        int diskY = (int) ((postY + postHeigth) - 5);

        LinkedList<Integer> aDisks = hanoiState.getATower().getDisks();
        LinkedList<Integer> bDisks = hanoiState.getBTower().getDisks();
        LinkedList<Integer> cDisks = hanoiState.getCTower().getDisks();

        for (int i = aDisks.size() - 1; i >= 0; i--) {
            int disk = aDisks.get(i);
            int diskWidth = baseDiskWidth * disk;
            int diskX = (int) (aPostX - diskWidth / 2);

            paintDisk(graphics, diskX, diskY - (diskHeight * (aDisks.size() - 1 - i)), diskWidth, diskHeight);
        }

        for (int i = bDisks.size() - 1; i >= 0; i--) {
            int disk = bDisks.get(i);
            int diskWidth = baseDiskWidth * disk;
            int diskX = (int) (bPostX - diskWidth / 2);

            paintDisk(graphics, diskX, diskY - (diskHeight * (aDisks.size() - 1 - i)), diskWidth, diskHeight);
        }

        for (int i = cDisks.size() - 1; i >= 0; i--) {
            int disk = cDisks.get(i);
            int diskWidth = baseDiskWidth * disk;
            int diskX = (int) (cPostX - diskWidth / 2);

            paintDisk(graphics, diskX, diskY - (diskHeight * (aDisks.size() - 1 - i)), diskWidth, diskHeight);
        }
    }

    private void paintPost(Graphics2D graphics, int x, int y, int width, int height) {
        graphics.setColor(Color.BLACK);
        graphics.fill(new Rectangle2D.Double(x, y, width, height));
    }

    private void paintDisk(Graphics2D graphics, int x, int y, int width, int height) {
        graphics.setColor(Color.ORANGE);
        graphics.fill(new Rectangle2D.Double(x, y, width, height));
    }

}
