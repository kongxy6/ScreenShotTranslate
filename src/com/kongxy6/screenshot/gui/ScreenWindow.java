package com.kongxy6.screenshot.gui;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScreenWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8658810548331316214L;

	private PaintRectanglePanel contentPanel;

	private Dimension dimension;

	public ScreenWindow(JLabel jLabelText, JLabel jLabelN, JLabel jLabelV, JLabel jLabelA)
			throws AWTException, InterruptedException {
		// TODO Auto-generated constructor stub
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		contentPanel = new PaintRectanglePanel(jLabelText, jLabelN, jLabelV, jLabelA, this, dimension);
		this.setContentPane(contentPanel);
		this.setUndecorated(true);
		this.setSize(dimension.width, dimension.height);
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}
