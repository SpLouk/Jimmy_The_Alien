package com.jimmythealien.src;

import java.awt.Point;
import java.util.Random;

public class EntityJimmy extends EntitySentient {

	Point blink;
	EntityJimmy follow;

	public EntityJimmy() {
		super(20, 22, 8, 40, EntityLiving.BEH_NEUTRAL, 5);
		setModel(new ModelJimmy(this));
		model.setOrientation(1);
		inventory = new Inventory(9, this);
		hud = new HeadsUpDisplay(this);

		blink = GameData.instance().getTime(120);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (GameData.instance().isTime(blink)) {
			model.blink();
			Random r = new Random();
			blink = GameData.instance().getTime(120 + r.nextInt(120));
		}

		switch (moveInt) {
		case 0:
			model.setOrientation(1);
			break;
		case 1:
			moveLeft();
			break;
		case 2:
			moveRight();
			break;
		}

		if (inventory.getSelectedSlot().tool.active) {
			Point b = Frame.game.mouseLoc;

			model.tActive = true;

			if (b.y < getY()) {
				if (b.x < getX()) {
					model.setHeadInt(1);
				} else if (b.x > getX() + getWidth()) {
					model.setHeadInt(3);
				} else {
					model.setHeadInt(2);
				}

			} else if (b.y > getY() + getHeight()) {
				if (b.x < getX()) {
					model.setHeadInt(7);
				} else if (b.x > getX() + getWidth()) {
					model.setHeadInt(5);
				} else {
					model.setHeadInt(6);
				}
			} else {
				if (b.x < getX()) {
					model.setHeadInt(0);
				} else if (b.x > getX() + getWidth()) {
					model.setHeadInt(4);
				} else {
					model.setHeadInt(8);
				}
			}

			int x = b.x - (getX() + getWidth() / 2);
			int y = (getY() + getHeight() / 2) - b.y;

			double m = Math.atan2(y, x) * 180 / Math.PI;

			model.tAngle = (int) m;

		} else {
			model.tActive = false;
		}

		if (!isPlayer()) {
			excecuteAI();
		}
	}

	private void excecuteAI() {

		for (int i = 0; i < GameData.instance().entityList.size(); i++) {
			Entity e = GameData.instance().entityList.get(i);

			if (e instanceof EntityJimmy && e != this) {
				follow = (EntityJimmy) e;
			}
		}
		if (follow != null) {
			if (getX() < follow.getX() - 200) {
				moveInt = 2;
			} else if (getX() > follow.getX() + 200) {
				moveInt = 1;
			} else {
				moveInt = 0;
			}
		} else {
			moveInt = 0;
		}

		switch (moveInt) {
		case 1:
			if (checkCollisionX(-moveSpeed) == 2) {
				up();
			}
			break;
		case 2:
			if (checkCollisionX(moveSpeed) == 1) {
				up();
			}
			break;
		}
	}
	
	public String toFile(){
		
		return super.toFile() + "&" + blink.x + "&" + blink.y;
	}
	
	protected void fromFile(String[] args){
		super.fromFile(args);
		
		blink = new Point(Integer.parseInt(args[9]), Integer.parseInt(args[10]));
	}
	
	EntitySaveable newEntity(){
		return new EntityJimmy();
	}
}
