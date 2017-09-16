package jimmyTheAlien;

public class ItemDirt extends ItemBlock {

	protected Tool getTool(Inventory i) {
		return new ToolBlock(this, i);
	}

	protected Block getBlock() {
		return Block.blocks[0];
	}
}
