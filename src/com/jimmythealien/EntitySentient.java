package jimmyTheAlien;

import java.awt.Point;

public class EntitySentient extends EntityLiving {

	protected boolean isPlayer = false;
	protected Inventory inventory;
	protected HeadsUpDisplay hud;
	
	
	public EntitySentient(int health, int jump, int move, int sight, int behaviour,
			int damage) {
		super(health, jump, move, sight, behaviour, damage);
	}
	
	public void onUpdate(){
		super.onUpdate();

		if (this.isPlayer) {
			Point a = Frame.game.mouseLoc;
			Point b = getCenter();

			float dist;

			dist = (float) Math.sqrt(Math.pow((b.x - a.x), 2)
					+ Math.pow(b.y - a.y, 2));

			if (dist <= 200) {
				inventory.getSelectedSlot().tool.active();
			} else {
				inventory.getSelectedSlot().tool.inActive();
			}
		} else {
			inventory.getSelectedSlot().tool.inActive();
		}

		if (!fall) {
			model.setActInt(0);
		}

		hud.onUpdate();
	}
	
	public void setPlayer(boolean b) {
		isPlayer = b;
	}

	public boolean isPlayer() {
		return isPlayer;
	}
	
	protected void kill() {

		remove();
		inventory.getSelectedSlot().tool.remove();

		if (isPlayer()) {
			Frame.game.m = new ModeFreeRoam();
		}

	}

}
