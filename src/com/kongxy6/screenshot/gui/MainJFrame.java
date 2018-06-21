package com.kongxy6.screenshot.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author kongxy
 * 截图->图片中的英文提取出来->翻译该英文
 */
public class MainJFrame extends JFrame {

	private static final long serialVersionUID = -2561402942458168580L;

	private JButton jButton;

	public MainJFrame() throws IOException, Exception {
		// TODO Auto-generated constructor stub
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(new GridLayout(4, 1));
		JLabel jLabelText = new JLabel("");
		panel.add(jLabelText);
		JLabel jLabelN = new JLabel("");
		panel.add(jLabelN);
		JLabel jLabelV = new JLabel("");
		panel.add(jLabelV);
		JLabel jLabelA = new JLabel("");
		panel.add(jLabelA);
		jButton = new JButton("截图");
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					new ScreenWindow(jLabelText, jLabelN, jLabelV, jLabelA);
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showConfirmDialog(null, "Initial Fail", "System Error!", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.setTitle("ScreenshotTranslate");
		this.getContentPane().add(panel);
		this.getContentPane().add(jButton, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(320, 200);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) throws IOException, Exception {
		// TODO Auto-generated method stub
		new MainJFrame();
	}

}
