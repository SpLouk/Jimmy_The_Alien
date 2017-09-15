package com.jimmythealien.src;

import java.awt.Point;

public class BlockHardRock extends Block {

	public BlockHardRock(short xCord, short yCord) {
		super(xCord, yCord);
	}

	public BlockHardRock() {
	}

	public Block newBlock(int x, int y) {
		return new BlockHardRock((short) x, (short) y);
	}

	@Override
	public int getHardness() {
		return -1;
	}

	@Override
	public Point getTextureLocation() {
		return new Point(0, 0);
	}

	@Override
	public String getBlockType() {
		return "HardRock";
	}

	public ItemBlock getItem() {
		return null;
	}
}