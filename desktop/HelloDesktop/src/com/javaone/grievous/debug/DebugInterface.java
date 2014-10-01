package com.javaone.grievous.debug;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
public class DebugInterface {

    private static final int DEBUG_WIDTH = 640;
    private static final int DEBUG_HEIGHT = 480;

    private JFrame debugFrame;
    private Panel imagePanel;

    private volatile Image lastImage;
    private volatile Image lastDistribution;

    public void start() {
        SwingUtilities.invokeLater(() -> {
            debugFrame = new JFrame();
            debugFrame.setName("Force Vision");
            debugFrame.setSize(DEBUG_WIDTH, DEBUG_HEIGHT);
            debugFrame.setVisible(true);
            debugFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

            imagePanel = new Panel();
            debugFrame.add(imagePanel);
        });
    }

    public void setLastImage(int[] pixels) {

        lastImage = toImage(pixels);

        scheduleRepaint();
    }

    private Image toImage(int[] pixels) {
        BufferedImage image = new BufferedImage(DEBUG_WIDTH, DEBUG_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        image.setRGB(0, 0, DEBUG_WIDTH, DEBUG_HEIGHT, pixels, 0, DEBUG_WIDTH);

        return image;
    }

    public void setLastProbabilityDistribution(int[] probabilityDistribution) {
        lastDistribution = toImage(probabilityDistribution);
    }

    private void scheduleRepaint() {
        if(imagePanel != null) {
            imagePanel.repaint();
        }
    }

    private class Panel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Image image = System.currentTimeMillis() % 2000 < 1000 ?
                    lastImage : lastDistribution;

            g.drawImage(image, 0, 0, debugFrame);
        }
    }
}
