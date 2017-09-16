package jimmyTheAlien;

import java.awt.Point;

public class BlockDirt extends Block {

	EntityGrass e = null;
	Point p = new Point(0, 1);

	public BlockDirt(short xCord, short yCord) {
		super(xCord, yCord);
	}

	public BlockDirt() {
	}

	public Block newBlock(int x, int y) {
		return new BlockDirt((short) x, (short) y);
	}

	protected Item getItem() {
		return new ItemDirt();
	}

	public void growGrass(boolean b) {
		e = new EntityGrass(this);
		p = new Point(1, 1);

		if (b) {
			updateLight();
		}
	}

	public void removeGrass() {
		e.remove();
		e = null;
		p = new Point(0, 1);
		updateLight();
	}

	@Override
	public void remove() {
		if (e != null) {
			e.remove();
			e = null;
		}

		super.remove();
	}

	@Override
	public int getHardness() {
		return 1;
	}

	@Override
	public Point getTextureLocation() {
		return p;
	}

	@Override
	public String getBlockType() {
		return "Dirt";
	}

}
