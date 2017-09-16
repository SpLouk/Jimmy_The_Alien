package jimmyTheAlien;

/**
 * 
 * @author david
 */
public class LightEngine {

	public void init() {

		for (int i = 0; i < GameData.instance().airList.size(); i++) {
			GameData.instance().airList.get(i).updateLight();
		}

		for (int i = 0; i < GameData.instance().blockList.size(); i++) {
			Block b = GameData.instance().blockList.get(i);
			b.updateLight();
		}
	}
}
