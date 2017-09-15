package com.jimmythealien.src;

import java.awt.Point;

public abstract class EntitySentient extends EntityLiving {

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
	
	public String toFile(){
		if(isPlayer()){
			return super.toFile() + inventory.toFile() + "&p";
		} else {
			return super.toFile() + inventory.toFile() + "&n";
		}
	}
	
	protected void fromFile(String[] args){
		super.fromFile(args);
		inventory.fromFile(args[7]);
		if(args[8] == "p"){
			isPlayer = true;
		}
		
	}
	
	protected void kill() {

		remove();
		inventory.getSelectedSlot().tool.remove();

		if (isPlayer()) {
			ModePlayer.findPlayer();
		}

	}

}
