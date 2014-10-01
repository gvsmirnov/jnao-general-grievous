package com.javaone.grievous;

import com.aldebaran.qimessaging.CallError;
import com.javaone.grievous.debug.DebugInterface;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.javaone.grievous.Main.out;

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
public class GeneralGrievous implements Runnable {

    private static final long LATENCY = 20;
    private static final int LIGHTSABER_COLOR = 0x69FEFF;

    private final VideoFeedProvider provider;

    private volatile boolean stopped;

    private DebugInterface debugInterface;

    public GeneralGrievous(VideoFeedProvider provider) {
        this.provider = provider;
    }

    public void start() throws InterruptedException, CallError {
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
        processImage(provider.getCurrentFrame());
    }

    private void processImage(byte[] buffer) {
        int[] image = encodeColors(buffer);
        int[] probabilityDistribution = detectLightsaber(image);

        debugInterface.setLastImage(image);
        debugInterface.setLastProbabilityDistribution(probabilityDistribution);
    }

    private int[] detectLightsaber(int[] image) {
        return IntStream.of(image).
                map(pixel -> Math.abs(pixel - LIGHTSABER_COLOR)).
                map(GeneralGrievous::detectLuminance).
                map(greyscale -> greyscale * 0x010101).
                toArray();
    }

    private static int detectLuminance(int pixel) {
        int luminance =
               pixel & 0xFF +
               (pixel >> 8) & 0xFF +
               (pixel >> 16) & 0xFF;

        luminance /= 3;

        return luminance;
    }

    public static int[] encodeColors(byte[] buffer) {
        int[] result = new int[buffer.length / 3];

        for(int i = 0; i < buffer.length; i +=3) {

            int rgb = buffer[i];
            rgb = (rgb << 8) + buffer[i + 1];
            rgb = (rgb << 8) + buffer[i + 2];

            result[i / 3] = rgb;
        }

        return result;
    }
}
