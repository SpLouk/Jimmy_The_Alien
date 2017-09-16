package jimmyTheAlien;

import java.awt.Point;
import java.util.Random;

public class WorldEvents {

	Point tSpawn, cSpawn;

	public WorldEvents() {
		tSpawn = GameData.instance().getTime(1);
		cSpawn = GameData.instance().getTime(120);
	}

	protected void onUpdate() {
		if (GameData.instance().isTime(tSpawn)) {
			
			EntityFurry.newSpawn();
			
			Random r = new Random();
			tSpawn = GameData.instance().getTime(600 + r.nextInt(1200));
			
			
		}
		
		if (GameData.instance().isTime(cSpawn)) {
			
			EntityCloud c = new EntityCloud();
			
			Random r = new Random();
			cSpawn = GameData.instance().getTime(120 + r.nextInt(120));
		}
		
		
	}

}
