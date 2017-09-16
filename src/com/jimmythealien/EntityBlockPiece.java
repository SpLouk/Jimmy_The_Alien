package jimmyTheAlien;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EntityBlockPiece extends Entity {

	final int size = 10;
	private int hVel, vVel;
	BufferedImage sprite;

	public EntityBlockPiece(int x, int y, int i, Block b) {

		Random r = new Random();
		hVel = r.nextInt(9) - 4;
		vVel = r.nextInt(10);

		sprite = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics g = sprite.getGraphics();

		g.drawImage(Block.getTexture(b.getTextureLocation()), -x, -y, null);

		setLocation(b.getX() + x, b.getY() + y);
		setCoordinates(b.getXCord() + x / 60, b.getYCord() + y / 60);
		setSize(size, size);
		setPaintLevel((byte) 6);
	}

	public void onUpdate() {
			
		fall();

		if (!onScreen()) {
			this.remove();
		}
	}

	public void fall() {

		for (int i = 0; i < 2; i++) {

			moveEntity(hVel, vVel);

			if (vVel > -61) {
				vVel -= 1;
			}
		}

	}

	public void paintComponent(Graphics g) {
		g.drawImage(sprite, getX(), getY(), null);
	}
}
