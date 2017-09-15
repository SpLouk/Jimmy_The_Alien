package com.jimmythealien.src;

public abstract class EntitySaveable extends EntityObject {

	protected static final EntitySaveable[] entities = {
			(EntitySaveable) new EntityFurry().remove(),
			(EntitySaveable) new EntityJimmy().remove(),
			(EntitySaveable) new EntityTree().remove(),
			(EntitySaveable) new EntityGrass().remove()};

	public String toFile() {
		int entity = -1;
		for (int i = 0; i < entities.length; i++) {
			if (this.getClass() == entities[i].getClass()) {
				entity = i;
			}
		}

		return entity + "&" + getXCord() + "&" + getYCord() + "&" + hVel + "&" + vVel;
	}
	
	protected void fromFile(String[] args){
		this.setCoordinates(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		hVel = Integer.parseInt(args[2]);
		vVel = Integer.parseInt(args[3]);
	}
	
	abstract EntitySaveable newEntity();
}
