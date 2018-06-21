package com.kongxy6.screenshot.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.kongxy6.lang.LANG;
import com.kongxy6.ocr.ImageToString;
import com.kongxy6.translate.HttpTranslate;
import com.kongxy6.translate.TranslateResEntity;
import com.kongxy6.util.ImageHelper;

import net.sourceforge.tess4j.TesseractException;

public class PaintRectanglePanel extends JPanel {

	/**
	 * 截图组件
	 */
	private static final long serialVersionUID = 7648040934336828343L;

	private Image screenImage;

	private JLabel jLabel;

	private boolean isDrag = false;

	private int xBegin = 0;

	private int yBegin = 0;

	private int xEnd = 0;

	private int yEnd = 0;

	public PaintRectanglePanel(JLabel jLabelText, JLabel jLabelN, JLabel jLabelV, JLabel jLabelA,
			ScreenWindow screenWindow, Dimension dimension) throws AWTException, InterruptedException {
		screenImage = ScreenCaptureImage.getScreenImage(0, 0, dimension.width, dimension.height);
		jLabel = new JLabel(new ImageIcon(screenImage));
		this.setLayout(new GridLayout(1, 1)); // 避免label无法充满panel
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		this.add(jLabel);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mEvent) {
				if (mEvent.getButton() == MouseEvent.BUTTON3) {
					screenWindow.dispose();
				}
			}

			public void mousePressed(MouseEvent mEvent) {
				xBegin = mEvent.getXOnScreen();
				yBegin = mEvent.getYOnScreen();
			}

			public void mouseReleased(MouseEvent mEvent) {
				if (isDrag) {
					if (xBegin > xEnd) {
						int temp = xBegin;
						xBegin = xEnd;
						xEnd = temp;
					}
					if (yBegin > yEnd) {
						int temp = yBegin;
						yBegin = yEnd;
						yEnd = temp;
					}
					String text = "";
					try {
						text = new ImageToString(
								ImageHelper.imageCut(screenImage, xBegin, yBegin, xEnd - xBegin, yEnd - yBegin))
										.getString();
					} catch (TesseractException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					HttpTranslate httpTranslate = new HttpTranslate(LANG.EN, LANG.ZH, text);
					TranslateResEntity translateResEntity = new TranslateResEntity();
					try {
						translateResEntity = httpTranslate.resolve(httpTranslate.query());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					jLabelText.setText(translateResEntity.getText());
					Map<String, String> tMap = translateResEntity.gettMap();
					for (Map.Entry<String, String> entry : tMap.entrySet()) {
						if ("n.".equals(entry.getKey())) {
							jLabelN.setText(entry.getKey() + " " + entry.getValue());
						}
						if ("v.".equals(entry.getKey())) {
							jLabelV.setText(entry.getKey() + " " + entry.getValue());
						}
						if ("adj.".equals(entry.getKey())) {
							jLabelA.setText(entry.getKey() + " " + entry.getValue());
						}
					}
					screenWindow.dispose();
				}
			}

		});
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				if (!isDrag) {
					isDrag = true;
				}
				// paint rectangle
				xEnd = e.getXOnScreen();
				yEnd = e.getYOnScreen();
				repaint();
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		// 之后改为动态绘制可缩放的组件
		g.setColor(Color.BLUE);
		g.drawRect(Math.min(xBegin, xEnd), Math.min(yBegin, yEnd), Math.abs(xBegin - xEnd), Math.abs(yBegin - yEnd));
		g.dispose();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// g.drawImage(image, 0, 0, this.getSize().width, this.getSize().height, this);
		// g.dispose();
	}
}
