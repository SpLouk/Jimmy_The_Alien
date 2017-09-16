package jimmyTheAlien;

import java.awt.Point;
import java.util.Random;

public class EntityFurry extends EntityLiving {

	Point attack;
	boolean attackReady;
	EntityLiving target;
	int entityNum;

	public EntityFurry() {
		super(10, 15, 10, 20, EntityLiving.BEH_AGGRESIVE, 2);
		setModel(new ModelFurry(this));
		model.setOrientation(1);
		attack = GameData.instance().getTime(40);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (GameData.instance().isTime(attack)) {
			attackReady = true;
			attack = GameData.instance().getTime(40);
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

		if (!isPlayer()) {
			excecuteAI();
		}
	}

	protected static void newSpawn() {

		EntityFurry e = new EntityFurry();
		e.spawnOffScreen();

		Random r = new Random();
		int i1 = r.nextInt(5) + 1;
		for (int i = 0; i < i1; i++) {
			EntityFurry e1 = new EntityFurry();
			e1.spawnOffScreen();
		}
	}

	private void excecuteAI() {

		if (target != null) {
			if (getX() + getWidth() < target.getX()) {
				moveInt = 2;
			} else if (getX() > target.getX() + target.getWidth()) {
				moveInt = 1;
			}
		} else {
			for(int i = 0; i < GameData.instance().entityList.size(); i++){
				Entity e = GameData.instance().entityList.get(i);
				
				if(e instanceof EntityLiving && !(e instanceof EntityFurry) && canSee(e)){
					target = (EntityLiving)e;
				}
			}
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
		
		if(target != null && target != GameData.instance().entityList.get(entityNum)){
			target = null;
		}
		
		if (target != null && attackReady && vCollision == target) {
			 target.addHealth(-damage);
			attackReady = false;
		}
	}
}
