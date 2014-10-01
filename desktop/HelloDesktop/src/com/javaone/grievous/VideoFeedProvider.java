package com.javaone.grievous;

import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Session;
import com.aldebaran.qimessaging.helpers.al.ALVideoDevice;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
public interface VideoFeedProvider {

    byte[] getCurrentFrame() throws CallError, InterruptedException;
    void stop() throws InterruptedException, CallError;

    class NaoVideoFeedProvider implements VideoFeedProvider {

        private static final int CAMERA_ID = 0;
        private static final int RESOLUTION_ID = 2;
        private static final int COLORSPACE_RGB = 11;

        private static final int FPS = 20;
        private static final int IMAGE_POSITION = 6;

        private final String moduleName = "SITH_LORD_WANNABE_" + UUID.randomUUID();

        private ALVideoDevice videoDevice;
        private String subscriptionId;

        public NaoVideoFeedProvider(Session session) throws InterruptedException, CallError {
            videoDevice = new ALVideoDevice(session);
            subscriptionId = videoDevice.subscribeCamera(moduleName, CAMERA_ID, RESOLUTION_ID, COLORSPACE_RGB, FPS);
        }

        @Override
        public byte[] getCurrentFrame() throws InterruptedException, CallError {
            List<Object> image = videoDevice.getImageRemote(subscriptionId);

            byte[] result = ((ByteBuffer) image.get(IMAGE_POSITION)).array();

            videoDevice.releaseImage(subscriptionId);

            return result;
        }

        @Override
        public void stop() throws InterruptedException, CallError {
            videoDevice.unsubscribe(subscriptionId);
        }
    }

    class FileVideoFeedProvider implements VideoFeedProvider {

        private final byte[] bytes;

        public FileVideoFeedProvider(String filename) throws IOException {
            File imgPath = new File(filename);
            BufferedImage bufferedImage = ImageIO.read(imgPath);

            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            int[] pixels = new int[width * height];

            bufferedImage.getRGB(0, 0, width, height, pixels, 0, width);

            this.bytes = decodeColors(pixels);
        }

        @Override
        public byte[] getCurrentFrame() {
            return bytes;
        }

        @Override
        public void stop() {

        }

        public static byte[] decodeColors(int[] buffer) {
            byte[] result = new byte[buffer.length * 3];

            for(int i = 0; i < buffer.length; i ++) {

                int rgb = buffer[i];

                result[i * 3 + 2] = (byte) (rgb & 0xFF);
                result[i * 3 + 1] = (byte) ((rgb >> 8) & 0xFF);
                result[i * 3 + 0] = (byte) ((rgb >> 16) & 0xFF);

            }

            return result;
        }
    }


}
