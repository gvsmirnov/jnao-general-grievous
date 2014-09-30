package com.javaone.grievous.debug;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
public class DebugInterface {

    private static final int DEBUG_WIDTH = 640;
    private static final int DEBUG_HEIGHT = 480;

    private JFrame debugFrame;
    private Panel imagePanel;

    private volatile BufferedImage lastImage;

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                debugFrame = new JFrame();
                debugFrame.setSize(DEBUG_WIDTH, DEBUG_HEIGHT);
                debugFrame.setVisible(true);
                debugFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

                imagePanel = new Panel();
                debugFrame.add(imagePanel);
            }
        });
    }

    private static int[] convertByteBuffer(ByteBuffer buffer) {
        //FIXME!

        int[] result = new int[buffer.position()];
        byte[] byteArray = buffer.array();

        for(int i = 0; i < byteArray.length; i ++) {
            result[i] = byteArray[i];
        }

        return result;
    }

    public void setLastImage(ByteBuffer buffer) {
        lastImage = new BufferedImage(DEBUG_WIDTH, DEBUG_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        lastImage.setRGB(0, 0, DEBUG_WIDTH, DEBUG_HEIGHT, convertByteBuffer(buffer), 0, DEBUG_WIDTH);

        scheduleRepaint();
    }

    private void scheduleRepaint() {
        if(imagePanel != null) {
            imagePanel.repaint();
        }
    }

    private class Panel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(lastImage, 0, 0, debugFrame);
        }
    }
}
