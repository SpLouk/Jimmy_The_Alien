package jimmyTheAlien;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Entity extends Object {

	private float xCord, yCord;

	public Entity() {
		setPaintLevel((byte) 3);
		GameData.instance().entityList.add(this);
	}

	public Entity(float f, float f1) {
		xCord = f;
		yCord = f1;
		GameData.instance().entityList.add(this);
	}

	public void onUpdate() {
		if (yCord < 0) {
		}
	}

	public float getXCord() {
		return xCord;
	}

	public float getYCord() {
		return yCord;
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

	public void setCoordinates(float x, float y) {
		xCord = x;
		yCord = y;
	}
	
	public void setCoordinates(Point p) {
		xCord = p.x;
		yCord = p.y;
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
		setCoordinates(getXCord() + (float) x / 60, getYCord() + (float) y / 60);
	}

	public void load() {

		setLocation(Frame.game.m.cordToPos(getXCord(), getYCord()));
	}

	public void remove() {
		GameData.instance().entityList.remove(this);
	}

	public abstract void paintComponent(Graphics g);

}
