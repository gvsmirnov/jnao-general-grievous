package com.aldebaran.demo;

/**
 * Created by epinault on 26/09/2014.
 */
public class MainDance {

	private static String ROBOT_IP = RobotIP.ip;
	private static final String ROBOT_PORT = RobotIP.port;

	public static void main(String[] args) {
        new DancyMoves().start(ROBOT_IP, ROBOT_PORT);
	}

}
