package com.jimmythealien.src;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class Object {

	protected byte paintLevel;
	private static byte topPaintLevel;
	protected int x = 0, y = 0, xCord2, yCord2;
	protected short width = 0, height = 0;
	protected boolean onScreen = false;

	public void setLocation(int i, int i2) {
		x = i;
		y = i2;
	}

	public void setLocation(Point p) {
		x = p.x;
		y = p.y;
	}

	public void setSize(int w, int h) {
		width = (short) w;
		height = (short) h;
	}

	public void setPaintLevel(byte i) {
		paintLevel = i;

		if (i > topPaintLevel) {
			topPaintLevel = i;
		}
	}

	public int getPaintlevel() {
		return paintLevel;
	}

	public static int getMaxPLevel() {
		return topPaintLevel;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Point getCenter() {
		int x, y;

		x = getX() + getWidth() / 2;
		y = getY() + getHeight() / 2;

		return new Point(x, y);
	}

	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	protected boolean onScreen() {
		boolean b = true;

		int f1 = Frame.game.getWidth(), f2 = Frame.game.getHeight();

		if (getY() > f2 || getY() + getHeight() < 0 || getX() > f1
				|| getX() + getWidth() < 0) {
			b = false;
		}

		screenState(b);
		return b;
	}

	protected void screenState(boolean b) {
		onScreen = b;
	}

}
