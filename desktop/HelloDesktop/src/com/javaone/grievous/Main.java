package com.javaone.grievous;

import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Session;

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
public class Main {

    private static final String CONNECTION_STRING = "tcp://169.254.29.204:9559";

    public static void main(String[] args) throws Exception {
        Session session = new Session();
        Future<Void> future = session.connect(CONNECTION_STRING);

        try {
            synchronized (future) {
                future.wait(1000);
            }

            final GeneralGrievous generalGrievous = new GeneralGrievous();

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

            generalGrievous.start(session);

        } catch (RuntimeException e) {
            err("Failed to connect to NAO on %s", CONNECTION_STRING);
        }
    }

    public static void err(String format, Object... args) {
        System.err.println(String.format(format, args));
    }

    public static void out(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

}
