package jimmyTheAlien;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;

public class ModelFurry extends ModelBiped {

	private final int bodyWidth = 30, bodyHeight = 30, armWidth = 7,
			legWidth = 5, legHeight = 10, footWidth = 9, footHeight = 4,
			thighY = bodyHeight - 3;
	private static boolean init = false;
	private static BufferedImage bodySprite[] = new BufferedImage[3],
			legSprite, footSprite;

	public ModelFurry(EntityFurry e1) {
		super(e1);

		if (!init) {
			try {

				BufferedImage spriteMap = ImageIO.read(getClass().getResource(
						"/Resources/furryMap.png"));

				for (int b = 0; b < 3; b++) {
					bodySprite[b] = new BufferedImage(bodyWidth, bodyHeight,
							spriteMap.getType());
					Graphics2D g = bodySprite[b].createGraphics();

					g.drawImage(spriteMap, 0, 0, bodyWidth, bodyHeight,
							bodyWidth * b, 0, bodyWidth * b + bodyWidth,
							bodyHeight, null);
					g.dispose();
				}

				legSprite = new BufferedImage(legWidth, legHeight,
						spriteMap.getType());
				Graphics2D g = legSprite.createGraphics();

				g.drawImage(spriteMap, 0, 0, legWidth, legHeight, bodyWidth * 3
						+ armWidth, 0, legWidth + armWidth + (bodyWidth * 3),
						legHeight, null);
				g.dispose();

				footSprite = new BufferedImage(footWidth, footHeight,
						spriteMap.getType());
				g = footSprite.createGraphics();

				g.drawImage(spriteMap, 0, 0, footWidth, footHeight, armWidth
						+ legWidth + (bodyWidth * 3), 0, armWidth + legWidth
						+ footWidth + (bodyWidth * 3), footHeight, null);
				g.dispose();

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			init = true;
		}

		e.setSize(bodyWidth, bodyHeight + legHeight * 2 + footHeight - 9);
	}

	public void paintComponent(Graphics g, ImageObserver io) {

		Point p, p1;

		calculateAngs();

		switch (getOrientation()) {
		case 0: // facing left

			p = new Point(e.getX() + e.getWidth() / 2, e.getY() + thighY);

			p1 = this.getRotatePoint(p, leftLegAng, legHeight);

			// left leg

			rotate(g, legSprite, lCalfAng, 0, -5, p1, io);
			rotate(g, legSprite, leftLegAng, 0, 0, p, io);
			rotate(g, footSprite, lCalfAng, -4, legHeight - 6, p1, io);

			// body
			drawImage(g, bodySprite[1], e.getWidth() / 2 - bodyWidth / 2, 0, io);

			// right leg

			p1 = this.getRotatePoint(p, rightLegAng, legHeight);

			rotate(g, legSprite, rCalfAng, 0, -5, p1, io);
			rotate(g, legSprite, rightLegAng, 0, 0, p, io);
			rotate(g, footSprite, rCalfAng, -4, legHeight - 6, p1, io);

			break;

		case 1: // facing center

			rightLegAng = 0;
			iL = 0;
			iR = 0;
			runVar = true;

			// left leg
			drawImage(g, legSprite,
					e.getWidth() / 2 - bodyWidth / 2 + legWidth, thighY
							+ legHeight - 3, io);
			drawImage(g, legSprite,
					e.getWidth() / 2 - bodyWidth / 2 + legWidth, thighY, io);
			drawImage(g, footSprite, e.getWidth() / 2 - bodyWidth / 2
					+ legWidth - 4, thighY + (legHeight * 2) - 6, io);

			// right leg
			drawImage(g, legSprite, e.getWidth() / 2 + bodyWidth / 2 - legWidth
					* 2, thighY + legHeight - 3, io);
			drawImage(g, legSprite, e.getWidth() / 2 + bodyWidth / 2 - legWidth
					* 2, thighY, io);
			drawImage(g, horizontalFlip(footSprite), e.getWidth() / 2
					+ bodyWidth / 2 - legWidth * 2, thighY + (legHeight * 2)
					- 6, io);

			// body
			drawImage(g, bodySprite[0], e.getWidth() / 2 - bodyWidth / 2, 0, io);

			break;

		case 2: // facing right

			p = new Point(e.getX() + e.getWidth() / 2, e.getY() + thighY);

			// right leg

			p1 = this.getRotatePoint(p, rightLegAng, legHeight);

			rotate(g, legSprite, rCalfAng, 0, -5, p1, io);

			rotate(g, legSprite, rightLegAng, 0, 0, p, io);
			rotate(g, horizontalFlip(footSprite), rCalfAng, -1, legHeight - 6,
					p1, io);

			// body
			drawImage(g, bodySprite[2], e.getWidth() / 2 - bodyWidth / 2, 0, io);

			// left leg

			p1 = this.getRotatePoint(p, leftLegAng, legHeight);

			rotate(g, legSprite, lCalfAng, 0, -5, p1, io);
			rotate(g, legSprite, leftLegAng, 0, 0, p, io);
			rotate(g, horizontalFlip(footSprite), lCalfAng, -1, legHeight - 6,
					p1, io);

			break;

		}
	}

}
