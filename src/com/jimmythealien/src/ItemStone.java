package com.jimmythealien.src;

public class ItemStone extends ItemBlock {

	protected Tool getTool(Inventory i) {
		return new ToolBlock(this, i);
	}

	protected Item newItem(){
		return new ItemStone();
	}
	
	protected Block getBlock() {
		return Block.blocks[1];
	}
}
