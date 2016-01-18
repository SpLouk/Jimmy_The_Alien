package com.jimmythealien.src;

import java.awt.Cursor;
import java.awt.Point;

public abstract class Tool extends Entity {

	protected Inventory inventory;
	protected Point loc, p = new Point(0, 0);
	protected boolean active = false;

	public Tool(Inventory i) {

		loc = new Point(0, 0);
		this.setPaintLevel((byte) 7);
		inventory = i;

	}

	protected void active() {

		active = true;

		loc = Frame.game.mouseLoc;

		this.p = Frame.game.m.posToCord(loc.x, loc.y);

		Point p1 = Frame.game.m.cordToPos(p.x, p.y);

		if (loc.x <= p1.x) {

			p = Frame.game.m.posToCord(loc.x + 60, loc.y);
		}

		if (loc.y <= p1.y) {

			p = Frame.game.m.posToCord(loc.x, loc.y - 60);
		}
	}

	public void setLocation(int x, int y) {
		if (active) {
			active();
		}

	}

	public int getX() {
		return loc.x;
	}

	public int getY() {
		return loc.y;
	}

	protected void inActive() {
		active = false;
		Frame.game.setCursor(Cursor.getDefaultCursor());
	}

	protected void use() {

	}

	protected Point getLoc() {
		return loc;
	}

	protected boolean onScreen() {
		return true;
	}

}
