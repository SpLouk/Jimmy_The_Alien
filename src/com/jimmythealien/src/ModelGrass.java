package com.jimmythealien.src;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;

public class ModelGrass extends Model {
	
	private static BufferedImage grassSprite[] = new BufferedImage[6],
			grassBase;
	private int grassSway = 0, grassLoc = 0, updateCount = 0;
	private boolean swayRight = true;

	static {
		try {
			BufferedImage spriteMap = ImageIO.read(GameData.instance().getClass().getResource(
					"/Resources/Grass.png"));

			grassBase = new BufferedImage(60, 30, spriteMap.getType());
			Graphics2D g = grassBase.createGraphics();
			g.drawImage(spriteMap, 0, 0, null);

			for (int b = 1; b < 7; b++) {
				grassSprite[b - 1] = new BufferedImage(60, 30,
						spriteMap.getType());
				g = grassSprite[b - 1].createGraphics();
				g.drawImage(spriteMap, 0, 0, 60, 30, 60 * b, 0,
						60 * b + 60, 30, null);
				g.dispose();
			}
		} catch (Exception e) {
		}
	}
	public ModelGrass(EntityGrass eG) {
		super(eG);

		e.setSize(60, 60);

	}

	@Override
	protected void onUpdate() {
		int nextUpdate = 0;
		switch (Frame.game.getWindLevel()) {
		case 0:
			nextUpdate = 10;
			break;
		case 1:
		case -1:
			nextUpdate = 6;
			break;
		case 2:
		case -2:
			nextUpdate = 3;
			break;
		case 3:
		case -3:
			nextUpdate = 0;
			break;
		}

		if (updateCount >= nextUpdate) {

			if (Frame.game.getWindLevel() < 0
					&& Frame.game.getWindLevel() - 1 < grassLoc) {
				grassLoc -= 1;
			}
			if (Frame.game.getWindLevel() > 0
					&& Frame.game.getWindLevel() + 1 > grassLoc) {
				grassLoc += 1;
			}
			if (Frame.game.getWindLevel() == 0) {
				grassLoc = 0;
			}

			if (grassSway == 1) {
				swayRight = false;
			}

			if (grassSway == -1) {
				swayRight = true;
			}

			if (swayRight) {
				grassSway += 1;
			} else {
				grassSway -= 1;
			}

			updateCount = 0;
		} else {
			updateCount += 1;
		}
	}

	public int getGrassPos() {
		return grassLoc + grassSway;
	}

	@Override
	public void paintComponent(Graphics g, ImageObserver io) {

		drawImage(g, grassBase, 0, 30, io);

		int grassPos = getGrassPos();
		boolean east = true;
		if (grassPos < 0) {
			grassPos *= -1;
			east = false;
		}

		if (east) {
			drawImage(g, horizontalFlip(grassSprite[grassPos]), 0, 0, io);
		} else {
			drawImage(g, grassSprite[grassPos], 0, 0, io);
		}
	}
}
