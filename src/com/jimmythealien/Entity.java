package com.jimmythealien.src;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Entity extends Object {

	public Entity() {
		setPaintLevel((byte) 3);
		GameData.instance().entityList.add(this);
	}
	

	public Entity(int x, int y) {
		setCoordinates(x, y);
		GameData.instance().entityList.add(this);
	}

	public void onUpdate() {
		if (yCord2 < 0) {
			System.out.println(yCord2);
		}
	}

	public int getXCord() {
		return xCord2;
	}

	public int getYCord() {
		return yCord2;
	}

	public boolean isPlayer() {
		return false;
	}

	public boolean isLiving() {
		return false;
	}

	public boolean isSolid() {
		return false;
	}

	public void setCoordinates(int x, int y) {
		xCord2 = x;
		yCord2 = y;
	}
	
	public void setCoordinates(Point p) {
		xCord2 = p.x;
		yCord2 = p.y;
	}
	
	public static Entity collision(Point p){

		for (int i = 0; i < GameData.instance().entityList.size(); i++) {
			Entity e = GameData.instance().entityList.get(i);

			if (e.isSolid()) {
				Rectangle r2 = e.getBounds();

				if (r2.contains(p)) {
					return e;
				}
			}
		}
		
		return null;
	}

	public void moveEntity(int x, int y) {
		setLocation(getX() + x, getY() - y);
		setCoordinates(getXCord() + x, getYCord() + y);
	}

	public void place() {
		setLocation(Frame.game.m.eCordToPos(getXCord(), getYCord()));
	}

	public Entity remove() {
		GameData.instance().entityList.remove(this);
		return this;
	}
	
	
	public abstract void paintComponent(Graphics g);

}
