package com.jimmythealien.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 * 
 * @author david
 */
public class EntityGrass extends EntityFlora {
	
	byte grassHeight = 10, i2 = 0;
	boolean b1 = false, b2 = false;
	Point[] p1 = new Point[6], p2 = new Point[6];

	public EntityGrass(Block b) {

		grow = GameData.instance().getTime(2400);
		
		for (int i = 0; i < p1.length; i++) {
			p1[i] = new Point(0, 0);
			p2[i] = new Point(0, 0);

		}

		setSize(60, 60);
		placeOnBlock(b);
		setPaintLevel((byte) 2);

		blockX = b.getXCord();
		blockY = b.getYCord();

		this.place();
	}
	
	public EntityGrass(Block b, boolean b1) {

		for (int i = 0; i < p1.length; i++) {
			p1[i] = new Point(0, 0);
			p2[i] = new Point(0, 0);

		}

		setSize(60, 60);
		placeOnBlock(b);
		setPaintLevel((byte) 2);

		blockX = b.getXCord();
		blockY = b.getYCord();

		if(b1){
			this.place();
		}
	}
	
	public EntityGrass() {
		for (int i = 0; i < p1.length; i++) {
			p1[i] = new Point(0, 0);
			p2[i] = new Point(0, 0);

		}

		setSize(60, 60);
		setPaintLevel((byte) 2);
	}

	@Override
	public void onUpdate() {

		if (GameData.instance().isTime(grow)) {
			Random r = new Random();
			grow = GameData.instance().getTime(2400 + r.nextInt(2400));
			spread();

		}
		if (Block.collision(blockX, blockY + 1, false)) {
			System.out.println(blockX + ", " + blockY);
			BlockDirt b = (BlockDirt) Block.getBlock(blockX, blockY);
			b.removeFlora();
		}

		int i1 = GameData.instance().windLevel;

		if (b1) {
			if (i2 < i1 * 2) {
				i2 += 2;
			} else {
				b1 = false;
			}
		} else {
			if (i2 > i1 * 2) {
				i2 -= 2;
			} else {
				b1 = true;
			}
		}

		int newX = i2;

		int a = (grassHeight * grassHeight) - (newX * newX);
		a = (int) Math.sqrt(a);

		for (int i = 0; i < p1.length; i++) {
			p1[i].x = getX() + (10 * i) + i2;
			p1[i].y = getY() + 29 - a;
		}

	}

	private void spread() {
		for (int iX = -1; iX < 2; iX += 2) {
			for (int iY = -1; iY < 2; iY++) {
				Block b = Block.getBlock(blockX + iX, blockY + iY);
				if (b != null && b.getBlockType().equals("Dirt")
						&& Block.getBlock(blockX + iX, blockY + iY + 1) == null) {
					BlockDirt d = (BlockDirt) b;
					if (d.e == null) {
						d.growGrass(true);
					}
				}
			}
		}
	}

	public void setLocation(int x1, int y1) {

		for (int i = 0; i < p1.length; i++) {
			p1[i].x += x1 - getX();
			p1[i].y += y1 - getY();

			p2[i].x += x1 - getX();
			p2[i].y += y1 - getY();
		}

		super.setLocation(x1, y1);

	}

	public void place() {
		super.place();

		for (int i = 0; i < p1.length; i++) {
			int x = getX() + (10 * i);
			int y1 = getY() + 29 - grassHeight, y2 = getY() + 29;

			p1[i] = new Point(x, y1);
			p2[i] = new Point(x, y2);
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(40, 185, 78));

		for (int i = 0; i < p1.length; i++) {

			g.drawLine(p1[i].x, p1[i].y, p2[i].x, p2[i].y);
		}

	}
	
	EntitySaveable newEntity() {
		return new EntityGrass();
	}

}
