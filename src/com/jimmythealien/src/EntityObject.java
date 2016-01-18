package com.jimmythealien.src;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class EntityObject extends Entity {

	boolean fall = false;
	protected int vVel = 0, hVel = 0;
	float int1;
	Object lCollision, rCollision, uCollision, dCollision;

	public EntityObject() {
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (checkCollisionY(-1) != 2) {
			fall = true;
		}

		if (fall) {
			fall();
		}

		if (dCollision instanceof Entity) {

			float temp = int1;
			Entity e = (Entity) dCollision;
			int1 = e.getXCord();
			if (int1 != temp) {
				moveWithCheck(Math.round((int1 - temp) * 60f), 0);
			}
		}
	}

	public void fall() {

		for (int i = 0; i < 2; i++) {
			if (fall) {
				if (checkCollisionY(vVel) == 1 && vVel > 0
						&& uCollision instanceof Block) {
					vVel = 0;
				}

				moveWithCheck(hVel, vVel);

				if (vVel > -61) {
					vVel -= 1;
				}

				if (checkCollisionY(vVel) == 2) {
					int d;
					d = dCollision.getY() - getY() - getHeight();
					moveWithCheck(0, -d);

					fall = false;
					endFall(vVel);
					vVel = 0;
				}
			}
		}
	}

	protected void endFall(int i) {
	}

	public void placeOnBlock(Block b) {
		int newX, newY;

		newX = b.getXCord()*60;
		newY = b.getYCord()*60;
		setCoordinates(newX, newY);
		moveEntity(0, getHeight() / 2);

	}

	/*
	 * The point returned indicates how far the entity was moved. The x
	 * coordinate indicates the entity's movement on the x axis, y coordinate
	 * for y axis.
	 */

	protected Point moveWithCheck(int x, int y) {

		Point p = new Point(0, 0);

		if (y != 0) {
			int i = checkCollisionY(y);
			if (i == 0) {
				moveEntity(0, y);
				p.y = y;
			} else if (y < 0 && i == 2) {
				if (dCollision instanceof EntityObject) {
					EntityLiving e = (EntityLiving) dCollision;
					int i1 = e.moveWithCheck(0, y/2).y;
					p.y = moveWithCheck(0, i1).y;
				}

			} else if (y > 0 && i == 1) {
				if (uCollision instanceof EntityObject) {
					EntityLiving e = (EntityLiving) uCollision;
					int i1 = e.moveWithCheck(0, y/2).y;
					p.y = moveWithCheck(0, i1).y;
				}
			}

		}

		if (x < 0) {
			if (getXCord() + x > 0) {
				if (checkCollisionX(x) != 2) {
					moveEntity(x, 0);
					p.x = x;
				} else {
					if (lCollision instanceof EntityLiving) {
						int newX = x / 2;
						EntityLiving e = (EntityLiving) lCollision;
						
						int i = e.moveWithCheck(newX, 0).x;
						p.x = moveWithCheck(i, 0).x;
					} else {

						int newX = lCollision.getX() + lCollision.getWidth()
								- getX();

						moveEntity(newX, 0);
						p.x = newX;
					}
				}
			} else {
				p.x = -getXCord();
				moveEntity(p.x, 0);
			}
		} else if (x > 0) {
			int newXCord = getXCord() + getWidth();

			if (newXCord + x < GameData.rightBound*60) {
				if (checkCollisionX(x) != 1) {
					moveEntity(x, 0);
					p.x = x;

				} else {
					if (rCollision instanceof EntityLiving) {
						int newX = x / 2;
						EntityLiving e = (EntityLiving) rCollision;
						
						int i = e.moveWithCheck(newX, 0).x;
						p.x = moveWithCheck(i, 0).x;
					} else {
						// Frame.game.newDig(false);
						int newX = rCollision.getX() - getX() - getWidth();
						p.x = newX;
						moveEntity(newX, 0);
					}
				}
			} else {
				p.x = GameData.rightBound*60 - newXCord;
				moveEntity(p.x, 0);
			}
		}

		return p;
	}

	public int checkCollisionX(int moveSpeed) {

		Rectangle r1 = getBounds();
		r1.x += moveSpeed;

		int x = (int) getXCord()/60, y = (int) getYCord()/60, w = (getWidth() / 60) + 1, h = (getHeight() / 60) + 1;

		if (moveSpeed > 0) {
			x += 1;
		} else if (moveSpeed < 0) {
			x -= 1;
		}

		for (int i = x; i <= x + w; i++) {
			for (int i1 = y - h; i1 <= y + h; i1++) {
				if (GameData.isValid(i, i1)
						&& GameData.instance().blockMap[i][i1] != null) {
					Block b = GameData.instance().blockMap[i][i1];
					Rectangle r2 = b.getBounds();

					if (r2.intersects(r1)) {

						if (r1.x < r2.x) {
							rCollision = b;
							return 1;

							// indicates character has hit left side of a block.
						} else if (r1.x > r2.x) {
							lCollision = b;
							return 2;

							// indicates character has hit left side of a block.
						}

					}
				}
			}
		}

		for (int i = 0; i < GameData.instance().entityList.size(); i++) {
			Entity e = GameData.instance().entityList.get(i);

			if (e.isSolid() && !e.equals(this)) {
				Rectangle r2 = e.getBounds();
				if (r1.intersects(r2)) {
					if (r1.x < r2.x) {
						rCollision = e;
						return 1;

						// indicates character has hit left side of a block.
					} else if (r1.x > r2.x) {
						lCollision = e;
						return 2;

						// indicates character has hit left side of a block.
					}
				}

			}
		}

		lCollision = null;
		rCollision = null;
		return 0;
	}

	public int checkCollisionY(int jumpHeight) {

		Rectangle r1 = getBounds();
		r1.y -= jumpHeight;

		int x = (int) getXCord()/60, y = (int) getYCord()/60, w = (getWidth() / 60) + 1, h = (getHeight() / 60) + 1;

		if (jumpHeight < 0) {
			y -= 1;
		} else if (jumpHeight > 0) {
			y += 1;
		}

		h += jumpHeight / 60;

		for (int i = x; i <= x + w; i++) {
			for (int i1 = y; i1 <= y + h; i1++) {
				if (GameData.isValid(i, i1)
						&& GameData.instance().blockMap[i][i1] != null) {
					Block b = GameData.instance().blockMap[i][i1];
					Rectangle r2 = b.getBounds();

					if (r1.intersects(r2)) {
						if (r2.getY() < r1.getY()) {
							uCollision = b;
							return 1;
							// indicates character has hit head on bottom of
							// block.
						} else {
							dCollision = b;
							return 2;
							// indicates character has landed on top of block.
						}
					}
				}
			}
		}

		for (int i = 0; i < GameData.instance().entityList.size(); i++) {
			Entity e = GameData.instance().entityList.get(i);

			if (e.isSolid() && !e.equals(this)) {
				Rectangle r2 = e.getBounds();

				if (r1.intersects(r2)) {
					if (r2.getY() < r1.getY()) {
						if (uCollision != e) {
							uCollision = e;
							int1 = e.getXCord();
						}
						return 1;
						// indicates character has hit head on bottom of block.
					} else {
						if (dCollision != e) {
							dCollision = e;
							int1 = e.getXCord();
						}
						return 2;
						// indicates character has landed on top of block.
					}
				}
			}
		}

		dCollision = null;
		uCollision = null;
		return 0;

	}

	public String toString() {
		String s = this.getClass() + ": " + getXCord() + ", " + getYCord();
		return s;
	}
}
