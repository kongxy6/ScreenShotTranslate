package com.kongxy6.screenshot.gui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Robot;

public class ScreenCaptureImage {

	public ScreenCaptureImage() {
		// TODO Auto-generated constructor stub
	}

	public static Image getScreenImage(int x, int y, int width, int height) throws AWTException, InterruptedException {
		Robot robot = new Robot();
		Image screen = robot.createScreenCapture(new Rectangle(x, y, width, height)).getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		MediaTracker mediaTracker = new MediaTracker(new Label());
		mediaTracker.addImage(screen, 0);
		mediaTracker.waitForID(0);
		return screen;
	}
}
