package com.jimmythealien.src;

import java.awt.Point;

public class BlockStone extends Block {

	public BlockStone(short xCord, short yCord) {
		super(xCord, yCord);
	}

	public BlockStone() {
	}

	public Block newBlock(int x, int y) {
		return new BlockStone((short) x, (short) y);
	}

	protected Item getItem() {
		return new ItemStone();
	}

	@Override
	public int getHardness() {
		return 10;
	}

	@Override
	public Point getTextureLocation() {
		return new Point(1, 0);
	}

	@Override
	public String getBlockType() {
		return "Stone";
	}
}
