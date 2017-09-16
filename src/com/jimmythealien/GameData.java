package jimmyTheAlien;

import java.awt.Point;
import java.util.ArrayList;

public class GameData {
	int windLevel, t, d;
	byte sunLight = 100;
	ArrayList<Block> blockList;
	ArrayList<BlockAir> airList;
	ArrayList<Entity> entityList;
	LightEngine lighting;

	static final short rightBound = 128, worldHeight = 128;
	static final int day = 4800;

	Block[][] blockMap = new Block[rightBound][worldHeight];
	BlockAir[][] airMap = new BlockAir[rightBound][worldHeight];

	public GameData(int windLevel, int t, int d, ArrayList<Block> blockList,
			ArrayList<BlockAir> airList, ArrayList<Entity> entityList,
			LightEngine lighting) {
		this.windLevel = windLevel;
		this.t = t;
		this.d = d;
		this.blockList = blockList;
		this.airList = airList;
		this.entityList = entityList;
		this.lighting = lighting;
	}

	public GameData() {
		this.windLevel = 0;
		this.t = 0;
		this.d = 0;
		this.blockList = new ArrayList<Block>();
		this.airList = new ArrayList<BlockAir>();
		this.entityList = new ArrayList<Entity>();
		this.lighting = new LightEngine();
	}

	public Point getTime(int i) {
		int t1 = t + i, d1 = d;

		if (t1 >= day) {
			d1 += t1 / day;
			t1 %= day;
		}

		return new Point(d1, t1);
	}

	public boolean isTime(Point p) {
		if (t == p.y && d == p.x) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isValid(int x, int y) {
		if (x > -1 && x < GameData.rightBound && y > -1
				&& y < GameData.worldHeight) {
			return true;
		} else {
			return false;
		}
	}

	public static GameData instance() {
		return Frame.game.data;
	}
}