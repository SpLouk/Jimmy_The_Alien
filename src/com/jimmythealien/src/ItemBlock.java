package com.jimmythealien.src;

import java.awt.image.BufferedImage;

public abstract class ItemBlock extends Item {

	protected Block getBlock() {
		return null;
	}

	protected BufferedImage getImg() {
		return Block.getTexture(getBlock().getTextureLocation());
	}
}
