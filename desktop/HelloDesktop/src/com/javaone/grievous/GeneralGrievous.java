package com.javaone.grievous;

import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Session;
import com.aldebaran.qimessaging.helpers.al.ALVideoDevice;
import com.javaone.grievous.debug.DebugInterface;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.javaone.grievous.Main.out;

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
public class GeneralGrievous implements Runnable {

    private static final int CAMERA_ID = 0;
    private static final int RESOLUTION_ID = 2;
    private static final int COLORSPACE_RGB = 11;

    private static final int FPS = 20;
    private static final int IMAGE_POSITION = 6;

    private static final long LATENCY = 20;
    private static final int LIGHTSABER_COLOR = 0x69feff;

    private final String moduleName = "SITH_LORD_WANNABE_" + UUID.randomUUID();

    private ALVideoDevice videoDevice;
    private String subscriptionId;
    private volatile boolean stopped;

    private DebugInterface debugInterface;

    public void start(Session session) throws InterruptedException, CallError {
        videoDevice = new ALVideoDevice(session);
        subscriptionId = videoDevice.subscribeCamera(moduleName, CAMERA_ID, RESOLUTION_ID, COLORSPACE_RGB, FPS);

        setUpDebugInterface();

        new Thread(this).start();
    }

    private void setUpDebugInterface() {
        debugInterface = new DebugInterface();
        debugInterface.start();
    }

    public void stop() throws InterruptedException, CallError {

        out("Stopping");

        stopped = true;
        videoDevice.unsubscribe(subscriptionId);
    }

    @Override
    public void run() {
        try {
            while (!stopped) {
                cycle();
                TimeUnit.MILLISECONDS.sleep(LATENCY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        out("Stopped");

    }

    private void cycle() throws CallError, InterruptedException {
        List<Object> image = videoDevice.getImageRemote(subscriptionId);

        processImage(image);

        videoDevice.releaseImage(subscriptionId);
    }

    private void processImage(List<Object> imageDescriptor) {
        ByteBuffer buffer = (ByteBuffer) imageDescriptor.get(IMAGE_POSITION);

        int[] image = convertByteBuffer(buffer);
        int[] probabilityDistribution = detectLightsaber(image);

        debugInterface.setLastImage(image);
        debugInterface.setLastProbabilityDistribution(probabilityDistribution);
    }

    private int[] detectLightsaber(int[] image) {
        return IntStream.of(image).
                map(pixel -> Math.abs(pixel - LIGHTSABER_COLOR)).
                //map(diff -> (diff & 0xFF + diff & 0xFF00 + diff & 0xFF0000) / 0xFFFFFF).
                toArray();
    }

    private static int[] convertByteBuffer(ByteBuffer buffer) {

        //TODO: streams, maybe?

        int[] result = new int[buffer.position() / 3];
        byte[] byteArray = buffer.array();

        for(int i = 0; i < byteArray.length; i +=3) {

            int rgb = byteArray[i];
            rgb = (rgb << 8) + byteArray[i + 1];
            rgb = (rgb << 8) + byteArray[i + 2];

            result[i / 3] = rgb;
        }

        return result;
    }
}
