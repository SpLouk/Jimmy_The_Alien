package jimmyTheAlien;

import java.awt.image.BufferedImage;

public class ItemBlock extends Item {

	protected Block getBlock() {
		return null;
	}

	protected BufferedImage getImg() {
		return Block.getTexture(getBlock().getTextureLocation());
	}
}
