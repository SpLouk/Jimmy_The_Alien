package jimmyTheAlien;

public class ItemStone extends ItemBlock {

	protected Tool getTool(Inventory i) {
		return new ToolBlock(this, i);
	}

	protected Block getBlock() {
		return Block.blocks[1];
	}
}
