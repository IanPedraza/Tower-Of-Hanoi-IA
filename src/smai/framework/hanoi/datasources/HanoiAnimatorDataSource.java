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
    private final double ratePostWidht = 0.0125;
    private final double maxDisksHeigth = 0.75;
    private final double minDisksRatioWidth = 0.04;

    public HanoiAnimatorDataSource() {
        setFps(15);
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
        
        /* Background */ 
        
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fill(new Rectangle2D.Double(0, 0, canvas.getWidth(), canvas.getHeight()));

        /* POSTS */
        
        int spacePerPost = width / 3;
        
        int postHeigth = (int) (height * ratePostHeigth);
        int postWidth = (int) (width*ratePostWidht);
        
        int postX = (int) ((width * 0.1) + (spacePerPost * 0.5));
        
        int aPostX = postX;
        int bPostX = postX * 2;
        int cPostX = postX * 3;
        
        int postY = height - postHeigth;

        drawPost(graphics, aPostX, postY, postWidth, postHeigth);
        drawPost(graphics, bPostX, postY, postWidth, postHeigth);
        drawPost(graphics, cPostX, postY, postWidth, postHeigth);

        /* DISKS */
        
        int baseDiskWidth = (int) (width * minDisksRatioWidth);
        int diskHeight = (int) (((postHeigth / hanoiInstance.getNumberOfDisks()) * maxDisksHeigth) * 0.5);
        int diskY = postY + postHeigth - diskHeight;
        
        float alphaRate = 1.0f / hanoiInstance.getNumberOfDisks();
        
        addDisk(hanoiState.getATower().getDisks(), baseDiskWidth, diskHeight, diskY, aPostX, graphics, alphaRate);
        addDisk(hanoiState.getBTower().getDisks(), baseDiskWidth, diskHeight, diskY, bPostX, graphics, alphaRate);
        addDisk(hanoiState.getCTower().getDisks(), baseDiskWidth, diskHeight, diskY, cPostX, graphics, alphaRate);
        
        /* Floor */
        
        graphics.setColor(Color.BLACK);
        graphics.fill(new Rectangle2D.Double(0, diskY, canvas.getWidth(), canvas.getHeight()*0.15));
    }
    
    private void addDisk(LinkedList<Integer> disks, int baseDiskWidth, int diskHeight, int diskY, int postX, Graphics2D graphics, float alphaRate) {
        int maxIndex = disks.size() - 1;
        int numberOnStack = 1;
        
        for (int i = maxIndex; i >= 0; i--) {
            int disk = disks.get(i);
            int diskWidth = baseDiskWidth * disk;
            int diskX = (int) (postX - diskWidth / 2);
            int dynamicDiskY = (int) diskY - (diskHeight*numberOnStack);
            numberOnStack++;
            
            drawDisk(graphics, diskX, dynamicDiskY, diskWidth, diskHeight, alphaRate*disk);
        }
    }

    private void drawPost(Graphics2D graphics, int x, int y, int width, int height) {
        int offset = (int)(width*0.5);
        graphics.setColor(Color.BLACK);
        graphics.fill(new Rectangle2D.Double(x-offset, y, width, height));
    }

    private void drawDisk(Graphics2D graphics, int x, int y, int width, int height, float alpha) {
        Rectangle2D rectangle = new Rectangle2D.Double(x, y, width, height);
        
        graphics.setColor(new Color(alpha, 0.1f, 0.0f));
        graphics.fill(rectangle);
        
        graphics.setColor(Color.BLACK);
        graphics.draw(rectangle);
    }

}
