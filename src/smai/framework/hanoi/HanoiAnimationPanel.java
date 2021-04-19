package smai.framework.hanoi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import smai.data.renders.AnimationPanel;

public class HanoiAnimationPanel extends AnimationPanel {

    private final int margin = 20;
    private final double ratePostHeigth = 0.7;
    private final double ratePostWidht = 0.0125;
    private final double maxDisksHeigth = 0.75;
    private final double minDisksRatioWidth = 0.04;   
    
    @Override
    protected void paintComponent(Graphics g) {
        
        if (instance == null && step == null) {
            return;
        }
        
        if (!(instance instanceof HanoiInstance) && !(step.getState() instanceof HanoiState)) {
            return;
        }
        
        HanoiInstance hanoiInstance = (HanoiInstance) instance;
        HanoiState hanoiState = (HanoiState) step.getState();

        Graphics2D graphics = (Graphics2D) g;
        int width = this.getWidth() - margin * 2;
        int height = this.getHeight() - margin * 2;
        
        /* Background */ 
        
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fill(new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight()));

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
        
        int maxDisksWidth = (int) ((width/3) * 0.85);
        int diskHeight = (int) (((postHeigth / hanoiInstance.getNumberOfDisks()) * maxDisksHeigth) * 0.5);
        int diskY = postY + postHeigth - diskHeight;
        
        float alphaRate = 1.0f / hanoiInstance.getNumberOfDisks();
        
        addDisk(hanoiState.getATower().getDisks(), hanoiInstance.getNumberOfDisks(), maxDisksWidth, diskHeight, diskY, aPostX, graphics, alphaRate);
        addDisk(hanoiState.getBTower().getDisks(), hanoiInstance.getNumberOfDisks(), maxDisksWidth, diskHeight, diskY, bPostX, graphics, alphaRate);
        addDisk(hanoiState.getCTower().getDisks(), hanoiInstance.getNumberOfDisks(), maxDisksWidth, diskHeight, diskY, cPostX, graphics, alphaRate);
        
        /* Floor */
        
        graphics.setColor(Color.BLACK);
        graphics.fill(new Rectangle2D.Double(0, diskY, this.getWidth(), this.getHeight()*0.15));
    }
        
    private void addDisk(LinkedList<Integer> disks, int numberOfDisks,int baseDiskWidth, int diskHeight, int diskY, int postX, Graphics2D graphics, float alphaRate) {
        int maxIndex = disks.size() - 1;
        int numberOnStack = 1;
        
        for (int i = maxIndex; i >= 0; i--) {
            int disk = disks.get(i);
            int diskWidth = (baseDiskWidth * disk) / numberOfDisks;
            int diskX = (int) (postX - diskWidth / 2);
            int dynamicDiskY = (int) diskY - (diskHeight*numberOnStack);
            numberOnStack++;
            
            drawDisk(graphics, diskX, dynamicDiskY, diskWidth, diskHeight, alphaRate*disk);
        }
    }

    private void drawPost(Graphics2D graphics, int x, int y, int width, int height) {
        int offset = (int)(width*0.5);
        double majorWidth = width*0.7;
        double minorWidt = width*0.3;
        
        graphics.setColor(Color.DARK_GRAY);
        graphics.fill(new Rectangle2D.Double(x-offset, y, majorWidth, height));
        
        graphics.setColor(Color.GRAY);
        graphics.fill(new Rectangle2D.Double(x-offset+majorWidth, y, minorWidt, height));
    }

    private void drawDisk(Graphics2D graphics, int x, int y, int width, int height, float alpha) {
        double halfWidth = width*0.5;
        
        graphics.setColor(new Color(alpha, 0.1f, 0.0f));
        graphics.fill(new Rectangle2D.Double(x, y, halfWidth, height));
        
        graphics.setColor(new Color((float)(alpha*0.8), 0.1f, 0.0f));
        graphics.fill(new Rectangle2D.Double(x+halfWidth, y, halfWidth, height));
        
        graphics.setColor(Color.BLACK);
        graphics.draw(new Rectangle2D.Double(x, y, width, height));
    }
}
