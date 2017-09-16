package jimmyTheAlien;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class EntityObject extends Entity {

	boolean fall = false;
	protected int vVel = 0, hVel = 0;
	float int1;
	Object hCollision, vCollision;

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

		if (vCollision instanceof Entity) {

			float temp = int1;
			Entity e = (Entity) vCollision;
			int1 = e.getXCord();
			if (int1 != temp) {
				moveWithCheck(Math.round((int1 - temp) * 60f), 0);
			}
		}
	}

	public void fall() {

		for (int i = 0; i < 2; i++) {
			if (fall) {
				if (checkCollisionY(vVel) == 1 && vVel > 0) {
					if (vCollision instanceof Block) {
						vVel = 0;
					} else {
						vVel -= 1;
					}
				}

				int i1 = moveWithCheck(hVel, vVel).y;
				if(i1 == 2){
					vVel = 0;
				}
				// Frame.game.formMouseMoved();

				if (vVel > -61) {
					vVel -= 1;
				}

				if (checkCollisionY(vVel) == 2) {
					int d;
					d = vCollision.getY() - getY() - getHeight();
					moveWithCheck(0, -d);

					// Frame.game.formMouseMoved();
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
		float newX, newY;

		newX = b.getXCord();
		newY = b.getYCord();
		setCoordinates(newX, newY);
		moveEntity(0, getHeight() / 2);

	}

	/*
	 * The point returned is based on whether movement was successful. If the
	 * returned point is 2, collision was detected and movement was stopped. If
	 * the returned value of p is 0, movement was succesful. p.x corresponds to
	 * x movement, p.y corresponds to y movement.
	 */

	protected Point moveWithCheck(int x, int y) {

		Point p = new Point(0, 0);

		if (y != 0) {
			int i = checkCollisionY(y);
			if (i == 0) {
				moveEntity(0, y);
			} else if ((y < 0 && i == 2) || (y > 0 && i == 1)){
				if (vCollision instanceof EntityObject) {
					EntityLiving e = (EntityLiving) vCollision;
					int i1 = e.moveWithCheck(0, y).y;
					if (i1 == 0) {
						moveEntity(0, y);
					} else {
						p.y = 2;
					}
					} else {
						p.y = 2;
					}

			} else {
				p.y = 2;
			}
		}

		if (x < 0) {
			if (getXCord() + ((float) x / 60f) > 0) {
				if (checkCollisionX(x) != 2) {
					moveEntity(x, 0);

				} else {
					if (hCollision instanceof EntityLiving) {
						int newX = x / 2;
						EntityLiving e = (EntityLiving) hCollision;
						int i = e.moveWithCheck(newX, 0).x;
						if (i == 0) {
							moveEntity(newX, 0);
						}
					} else {
						p.x = 2;

						int newX = hCollision.getX() + hCollision.getWidth()
								- getX();

						moveEntity(newX, 0);
					}
				}
			} else {
				p.x = 2;
				moveEntity((int) (-getXCord() * 60), 0);
			}
		} else if (x > 0) {
			float newXCord = getXCord() + getWidth() / 60f;

			if (newXCord + ((float) x / 60f) < GameData.rightBound) {
				if (checkCollisionX(x) != 1) {
					moveEntity(x, 0);

				} else {
					if (hCollision instanceof EntityLiving) {
						int newX = x / 2;
						EntityLiving e = (EntityLiving) hCollision;
						int i = e.moveWithCheck(newX, 0).x;
						if (i == 0) {
							moveEntity(newX, 0);
						}
					} else {
						// Frame.game.newDig(false);
						int newX = hCollision.getX() - getX() - getWidth();
						p.x = 1;
						moveEntity(newX, 0);
					}
				}
			} else {
				p.x = 1;
				moveEntity((int) ((GameData.rightBound - newXCord) * 60), 0);
			}
		}

		return p;
	}

	public int checkCollisionX(int moveSpeed) {

		Rectangle r1 = getBounds();
		r1.x += moveSpeed;

		int x = (int) getXCord(), y = (int) getYCord(), w = (getWidth() / 60) + 1, h = (getHeight() / 60) + 1;

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
							hCollision = b;
							return 1;

							// indicates character has hit left side of a block.
						} else if (r1.x > r2.x) {
							hCollision = b;
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
						hCollision = e;
						return 1;

						// indicates character has hit left side of a block.
					} else if (r1.x > r2.x) {
						hCollision = e;
						return 2;

						// indicates character has hit left side of a block.
					}
				}

			}
		}

		return 0;
	}

	public int checkCollisionY(int jumpHeight) {

		Rectangle r1 = getBounds();
		r1.y -= jumpHeight;

		int x = (int) getXCord(), y = (int) getYCord(), w = (getWidth() / 60) + 1, h = (getHeight() / 60) + 1;

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
							vCollision = b;
							return 1;
							// indicates character has hit head on bottom of
							// block.
						} else {
							vCollision = b;
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
						if (vCollision != e) {
							vCollision = e;
							int1 = e.getXCord();
						}
						return 1;
						// indicates character has hit head on bottom of block.
					} else {
						if (vCollision != e) {
							vCollision = e;
							int1 = e.getXCord();
						}
						return 2;
						// indicates character has landed on top of block.
					}
				}
			}
		}

		vCollision = null;
		return 0;

	}
	
	public String toString(){
		String s = this.getClass() + ": " + getXCord() + ", " + getYCord();
		return s;
	}
}
