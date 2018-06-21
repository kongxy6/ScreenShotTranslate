package com.kongxy6.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageHelper {

	public ImageHelper() {
		// TODO Auto-generated constructor stub
	}

	// ToolkitImage to BufferedImage, and cut
	public static Image imageCut(Image image, int x, int y, int w, int h) {
		// BufferedImage bufferedImage = ImageIO.read(new FileInputStream(filePath));
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = bufferedImage.createGraphics();
		graphics2d.drawImage(image, 0, 0, null);
		graphics2d.dispose();
		bufferedImage = bufferedImage.getSubimage(x, y, w, h);
		return (Image) bufferedImage;
	}
}
