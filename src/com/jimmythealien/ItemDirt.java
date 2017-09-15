package com.jimmythealien.src;

public class ItemDirt extends ItemBlock {

	protected Tool getTool(Inventory i) {
		return new ToolBlock(this, i);
	}

	protected Item newItem(){
		return new ItemDirt();
	}
	
	protected Block getBlock() {
		return Block.blocks[0];
	}
}
