package jimmyTheAlien;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;

public class ModelJimmy extends ModelBiped {

	protected int blink = 0;
	private final int bodyWidth = 52, bodyHeight = 52, headWidth = 34,
			headHeight = 23, armWidth = 19, armHeight = 35, legWidth = 6,
			legHeight = 23, footWidth = 16, footHeight = 10,
			thighY = headHeight + bodyHeight - 10, armY = headHeight
					+ bodyHeight / 2 - 10, armX = 3;
	private static boolean init = false;
	private static BufferedImage bodySprite[] = new BufferedImage[3],
			headSprite[] = new BufferedImage[11], armSprite, legSprite,
			footSprite;

	public ModelJimmy(EntityJimmy j) {
		super(j);

		if (!init) {
			try {

				BufferedImage spriteMap = ImageIO.read(getClass().getResource(
						"/Resources/jimmyMap.png"));

				for (int b = 0; b < 3; b++) {
					bodySprite[b] = new BufferedImage(bodyWidth, bodyHeight,
							spriteMap.getType());
					Graphics2D g = bodySprite[b].createGraphics();

					g.drawImage(spriteMap, 0, 0, bodyWidth, bodyHeight,
							bodyWidth * b, 0, bodyWidth * b + bodyWidth,
							bodyHeight, null);
					g.dispose();
				}

				for (int b = 0; b < 6; b++) {

					headSprite[b] = new BufferedImage(headWidth, headHeight,
							spriteMap.getType());
					Graphics2D g = headSprite[b].createGraphics();

					g.drawImage(spriteMap, 0, 0, headWidth, headHeight,
							headWidth * b, bodyHeight, headWidth * b
									+ headWidth, bodyHeight + headHeight, null);
					g.dispose();
				}

				for (int b = 0; b < 5; b++) {

					headSprite[b + 6] = new BufferedImage(headWidth,
							headHeight, spriteMap.getType());
					Graphics2D g = headSprite[b + 6].createGraphics();

					g.drawImage(spriteMap, 0, 0, headWidth, headHeight,
							headWidth * b, bodyHeight + headHeight, headWidth
									* b + headWidth, bodyHeight + headHeight
									* 2, null);
					g.dispose();
				}

				armSprite = new BufferedImage(armWidth, armHeight,
						spriteMap.getType());
				Graphics2D g = armSprite.createGraphics();

				g.drawImage(spriteMap, 0, 0, armWidth, armHeight,
						(bodyWidth * 3), 0, armWidth + (bodyWidth * 3),
						armHeight, null);
				g.dispose();

				legSprite = new BufferedImage(legWidth, legHeight,
						spriteMap.getType());
				g = legSprite.createGraphics();

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

		e.setSize(bodyWidth, headHeight + bodyHeight + legHeight * 2
				+ footHeight - 20);
	}

	public void blink() {

		if (getOrientation() == 1) {
			blink = 1;
		}
	}

	public void paintComponent(Graphics g, ImageObserver io) {

		Point p, p1;

		if (tActive) {
			if (getOrientation() == 1) {
				drawImage(g, headSprite[h1],
						e.getWidth() / 2 - headSprite[h1].getWidth() / 2, 0, io);
			} else {
				drawImage(g, headSprite[h1],
						e.getWidth() / 2 - headSprite[h1].getWidth() / 2, 0, io);
			}

		} else {

			switch (getOrientation()) {
			case 0:
				drawImage(g, headSprite[0],
						e.getWidth() / 2 - headSprite[0].getWidth() / 2, 0, io);
				break;
			case 1:

				switch (blink) {
				case 0:
					drawImage(g, headSprite[8], e.getWidth() / 2
							- headSprite[8].getWidth() / 2, 0, io);
					break;
				case 1:
				case 2:
				case 3:
					drawImage(g, headSprite[9], e.getWidth() / 2
							- headSprite[0].getWidth() / 2, 0, io);
					blink++;
					break;
				case 4:
				case 5:
				case 6:
					drawImage(g, headSprite[10], e.getWidth() / 2
							- headSprite[0].getWidth() / 2, 0, io);
					blink++;
					break;
				case 7:
				case 8:
				case 9:
					drawImage(g, headSprite[9], e.getWidth() / 2
							- headSprite[0].getWidth() / 2, 0, io);
					blink = 0;
					break;
				}
				break;
			case 2:
				drawImage(g, headSprite[4],
						e.getWidth() / 2 - headSprite[4].getWidth() / 2, 0, io);
				break;
			}
		}

		calculateAngs();

		switch (getOrientation()) {
		case 0: // facing left

			// left arm
			p = new Point(e.getX() + e.getWidth() / 2, e.getY() + armY);

			rotate(g, horizontalFlip(armSprite), rightLegAng - 45, 0, 0, p, io);

			p = new Point(e.getX() + e.getWidth() / 2, e.getY() + thighY);

			p1 = this.getRotatePoint(p, leftLegAng, legHeight);

			// left leg

			rotate(g, legSprite, lCalfAng, 0, -5, p1, io);
			rotate(g, legSprite, leftLegAng, 0, 0, p, io);
			rotate(g, footSprite, lCalfAng, -9, legHeight - 10, p1, io);

			// body
			drawImage(g, bodySprite[1], e.getWidth() / 2 - bodyWidth / 2,
					headHeight, io);

			// right leg

			p1 = this.getRotatePoint(p, rightLegAng, legHeight);

			rotate(g, legSprite, rCalfAng, 0, -5, p1, io);
			rotate(g, legSprite, rightLegAng, 0, 0, p, io);
			rotate(g, footSprite, rCalfAng, -9, legHeight - 10, p1, io);

			// right arm
			p = new Point(e.getX() + e.getWidth() / 2, e.getY() + armY);

			if (tActive) {
				rotate(g, horizontalFlip(armSprite), tAngle + 90, 0, 0, p, io);
			} else {
				rotate(g, horizontalFlip(armSprite), leftLegAng - 45, 0, 0, p,
						io);
			}

			break;

		case 1: // facing center

			rightLegAng = 0;
			iL = 0;
			iR = 0;
			runVar = true;

			// left leg
			drawImage(g, legSprite,
					e.getWidth() / 2 - bodyWidth / 2 + legWidth, thighY
							+ legHeight - 5, io);
			drawImage(g, legSprite,
					e.getWidth() / 2 - bodyWidth / 2 + legWidth, thighY, io);
			drawImage(g, footSprite, e.getWidth() / 2 - bodyWidth / 2
					+ legWidth - 9, thighY + (legHeight * 2) - 10, io);

			// right leg
			drawImage(g, legSprite, e.getWidth() / 2 + bodyWidth / 2 - legWidth
					* 2, thighY + legHeight - 5, io);
			drawImage(g, legSprite, e.getWidth() / 2 + bodyWidth / 2 - legWidth
					* 2, thighY, io);
			drawImage(g, horizontalFlip(footSprite), e.getWidth() / 2
					+ bodyWidth / 2 - legWidth * 2 - 1, thighY
					+ (legHeight * 2) - 10, io);

			// body
			drawImage(g, bodySprite[0], e.getWidth() / 2 - bodyWidth / 2,
					headHeight, io);

			// right arm
			p = new Point(e.getX() + e.getWidth() / 2 + armWidth - armX,
					e.getY() + armY);

			if (tActive) {
				if (tAngle <= 0) {
					rotate(g, horizontalFlip(armSprite), tAngle + 90, 0, 0, p,
							io);
				} else {
					rotate(g, armSprite, tAngle + 90, -armWidth, 0, p, io);
				}

			} else {
				rotate(g, horizontalFlip(armSprite), armAng, 0, 0, p, io);
			}

			p = new Point(e.getX() + e.getWidth() / 2 - armWidth + armX,
					e.getY() + armY);

			// left arm
			rotate(g, armSprite, -armAng, -armWidth, 0, p, io);

			break;

		case 2: // facing right

			p = new Point(e.getX() + e.getWidth() / 2, e.getY() + armY);

			// right arm

			if (tActive) {
				rotate(g, armSprite, tAngle + 90, -armWidth + 3, 0, p, io);
			} else {
				rotate(g, armSprite, leftLegAng + 45, -armWidth + 3, 0, p, io);
			}

			p = new Point(e.getX() + e.getWidth() / 2, e.getY() + thighY);

			// right leg

			p1 = this.getRotatePoint(p, rightLegAng, legHeight);

			rotate(g, legSprite, rCalfAng, 0, -5, p1, io);

			rotate(g, legSprite, rightLegAng, 0, 0, p, io);
			rotate(g, horizontalFlip(footSprite), rCalfAng, -1, legHeight - 10,
					p1, io);

			// body
			drawImage(g, bodySprite[2], e.getWidth() / 2 - bodyWidth / 2,
					headHeight, io);

			// left leg

			p1 = this.getRotatePoint(p, leftLegAng, legHeight);

			rotate(g, legSprite, lCalfAng, 0, -5, p1, Frame.game);

			rotate(g, legSprite, leftLegAng, 0, 0, p, io);
			rotate(g, horizontalFlip(footSprite), lCalfAng, -1, legHeight - 10,
					p1, io);

			p = new Point(e.getX() + e.getWidth() / 2, e.getY() + armY);

			// left arm
			rotate(g, armSprite, rightLegAng + 45, -armWidth + 3, 0, p, io);

			break;

		}
	}

}
