package com.javaone.grievous;

import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Session;
import com.javaone.grievous.VideoFeedProvider.FileVideoFeedProvider;
import com.javaone.grievous.VideoFeedProvider.NaoVideoFeedProvider;

import java.io.IOException;

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
public class Main {

    private static final String CONNECTION_STRING = "tcp://169.254.61.216:9559";

    public static void main(String[] args) throws Exception {
        final GeneralGrievous generalGrievous;

        boolean emulation = args.length > 0 && args[0].equals("-em");

        generalGrievous = emulation ? makePseudoGeneral() : makeNaoGeneral();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    generalGrievous.stop();
                } catch (Exception e) {
                    err("Unexpected exception");
                    e.printStackTrace();
                }
            }
        });

        generalGrievous.start();
    }

    private static GeneralGrievous makePseudoGeneral() throws IOException {
        return new GeneralGrievous(new FileVideoFeedProvider("sample-saber.png"));
    }

    private static GeneralGrievous makeNaoGeneral() throws Exception {
        try {

            Session session = new Session();
            Future<Void> future = session.connect(CONNECTION_STRING);

            synchronized (future) {
                future.wait(1000);
            }

            return new GeneralGrievous(new NaoVideoFeedProvider(session));

        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to NAO on " + CONNECTION_STRING);
        }
    }

    public static void err(String format, Object... args) {
        System.err.println(String.format(format, args));
    }

    public static void out(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

}
