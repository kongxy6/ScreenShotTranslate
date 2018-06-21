package com.kongxy6.ocr;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import net.sourceforge.tess4j.TesseractException;

public class ImageToString {

	private static BufferedImage bufferedImage;

	private SingletonTesseract singletonTesseract = SingletonTesseract.getInstance();

	public ImageToString() {
		// TODO Auto-generated constructor stub
	}

	public ImageToString(Image image) {
		bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = bufferedImage.createGraphics();
		graphics2d.drawImage(image, 0, 0, null);
		graphics2d.dispose();
	}

	public String getString() throws TesseractException {
		String res = singletonTesseract.geTesseract1().doOCR(bufferedImage);
		// 对这个String做初步的处理
		return res;
	}

}
