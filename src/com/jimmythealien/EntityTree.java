package jimmyTheAlien;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class EntityTree extends EntityObject {

	ArrayList<Point> lPoint = new ArrayList<Point>();
	ArrayList<Point> rPoint = new ArrayList<Point>();
	protected BufferedImage b1;

	public EntityTree(Block b) {
		lPoint.add(new Point(30, 0));
		rPoint.add(new Point(30, 0));
		Random r = new Random();
		grow(r.nextInt(6) + 200);

		setPaintLevel((byte) 4);
		placeOnBlock(b);

	}

	public void onUpdate() {

	}

	private void grow(int h) {
		Random r = new Random();
		int chance = 2, l = lPoint.get(lPoint.size() - 1).x, d = 16, i1 = lPoint
				.get(lPoint.size() - 1).y + h;
		boolean inv = false;

		for (int i = lPoint.get(lPoint.size() - 1).y + 1; i <= i1; i++) {

			if (d == 0) {
				d = 1;
			}
			if (r.nextInt(d) == 0) {
				if (inv) {
					inv = false;
				} else {
					inv = true;
				}

				d = 32;
			} else {
				d--;
			}

			if (r.nextInt(chance) + 1 == chance) {

				if (r.nextInt(4) != 0) {
					if (inv) {
						l -= 1;
					} else {
						l += 1;
					}
				}

				chance *= 2;
			} else {
				chance /= 2;
			}

			int width = getHeight() / 6;
			lPoint.add(new Point(l, i));
			rPoint.add(new Point(width, i));
		}

		thickenTree();
		drawTree();
	}

	private void thickenTree() {
		int width = getHeight() / 6;

		int iL = 0, iR = 0;

		for (int i = 0; i < lPoint.size(); i++) {
			int i1 = rPoint.get(i).x - lPoint.get(i).x;
			if (i1 != width) {
				int i2 = width - i1;

				lPoint.get(i).x -= i2 / 2;
				rPoint.get(i).x += i2 / 2;

			}

			if (lPoint.get(i).x < iL) {
				iL = lPoint.get(i).x;
			}

			if (rPoint.get(i).x > iR) {
				iR = rPoint.get(i).x;
			}
		}

		setSize(iR - iL, getHeight());
	}

	private void drawTree() {
		b1 = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) b1.getGraphics();

		g.setColor(new Color(102, 85, 0));

		for (int i = 0; i < lPoint.size(); i++) {
			System.out.println("width: " + (rPoint.get(i).x - lPoint.get(i).x));
			g.fillRect(lPoint.get(i).x, lPoint.get(i).y * 5, rPoint.get(i).x, 5);
		}
	}

	public int getHeight() {

		int i = lPoint.get(0).y, i2 = lPoint.get(lPoint.size() - 1).y;
		System.out.println((i2 - i) * 5);
		return (i2 - i) * 5;
	}

	protected boolean onScreen() {
		return true;
	}

	public void paintComponent(Graphics g) {

		g.drawImage(b1, getX(), getY(), null);
	}

}
