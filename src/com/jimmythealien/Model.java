package jimmyTheAlien;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Model {

	private int orientationInt = 0, actInt = 0;
	protected int h1 = 8, tAngle = 0, leftLegAng = 0, rightLegAng = 0, iL = 0,
			iR = 0;
	boolean tActive = false;
	Entity e;

	public Model(Entity e) {
		this.e = e;
	}

	public void update() {

	}

	public void setOrientation(int o) {

		if (orientationInt != o) {
			rightLegAng = 0;
			leftLegAng = 0;
			iL = 0;
			iR = 0;
		}

		orientationInt = o;
	}

	public void blink() {

	}

	public void setActInt(int i) {
		actInt = i;

	}

	public int getHeadInt() {
		return h1;
	}

	public void setHeadInt(int h1) {
		this.h1 = h1;
	}

	public int getOrientation() {
		return orientationInt;
	}

	public static BufferedImage horizontalFlip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();
		return dimg;
	}

	protected void rotate(Graphics g, BufferedImage img, int angle, int x,
			int y, Point p, ImageObserver io) {

		Graphics2D g2d = (Graphics2D) g;

		AffineTransform temp = g2d.getTransform();
		AffineTransform at = AffineTransform.getTranslateInstance(p.x, p.y);
		at.rotate(Math.toRadians(-angle));
		g2d.setTransform(at);

		g2d.drawImage(img, x, y, io);

		g2d.setTransform(temp);

	}

	public void drawImage(Graphics g, Image image, int x, int y,
			ImageObserver io) {
		g.drawImage(image, e.getX() + x, e.getY() + y, io);
	}

	public void paintComponent(Graphics g, ImageObserver io) {

	}

	public int getActInt() {
		return actInt;
	}
}
