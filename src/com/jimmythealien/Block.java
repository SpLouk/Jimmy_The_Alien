package com.jimmythealien.src;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public abstract class Block extends Object {
	
	protected static BufferedImage terrainMap;
	protected static final Block[] blocks = { new BlockDirt(),
			new BlockStone(), new BlockHardRock()};

	private byte lightValue;
	private short xCord, yCord;
	private float aB, aT, aL, aR;
	protected BufferedImage b1;
	protected GradientPaint gp, gp1;

	static {
		try {
			terrainMap = ImageIO.read(GameData.instance().getClass().getResource(
					"/Resources/TerrainMap.png"));
		} catch (Exception e) {
		}
	}
	
	public Block() {
		setSize(60, 60);
		GameData.instance().blockList.add(this);
		setPaintLevel((byte) 3);
	}

	public abstract int getHardness();

	public abstract String getBlockType();

	protected abstract Item getItem();

	public abstract Block newBlock(int x, int y);

	public abstract Point getTextureLocation();

	public Block(short xCord, short yCord) {
		this();
		setCoordinates(xCord, yCord);
		GameData.instance().blockMap[xCord][yCord] = this;
	}

	public static boolean create(short xCord, short yCord, int i, boolean b1) {
		if (!Block.collision(xCord, yCord, false)
				&& yCord < GameData.worldHeight) {
			if (BlockAir.collision(xCord, yCord)) {
				BlockAir.getBlock(xCord, yCord).remove();
			}
			blocks[i].newBlock(xCord, yCord);
			return true;
		} else {
			return false;
		}
	}

	public static boolean create(short xCord, short yCord, Block block,
			boolean b1) {
		if (!Block.collision(xCord, yCord, false)
				&& yCord < GameData.worldHeight && yCord >= 0) {
			if (BlockAir.collision(xCord, yCord)) {
				BlockAir.getBlock(xCord, yCord).remove();
			}

			Block b = block.newBlock(xCord, yCord);
			if(b1){
				b.load();
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean isSolid() {
		return true;
	}

	public byte getLightValue() {
		return lightValue;
	}

	public void updateLight() {

		byte temp = lightValue;

		if (this.isTop()) {
			lightValue = GameData.instance().sunLight;
		} else {
			lightValue = 0;
		}

		for (int a = -1; a < 2; a++) {
			for (int b = -1; b < 2; b++) {
				if (getBlock(a, b, this) != null
						&& getBlock(a, b, this).getLightValue() / 3 > lightValue) {
					lightValue = (byte) (getBlock(a, b, this).getLightValue() / 3);
				}
				BlockAir b1 = BlockAir.getBlock(getXCord() + a, getYCord() + b);
				if (b1 != null && b1.getLightValue() / 2 > lightValue) {
					lightValue = (byte) (b1.getLightValue() / 2);
				}
			}
		}

		if (lightValue != temp) {
			for (int i1 = -1; i1 < 2; i1++) {
				for (int i2 = -1; i2 < 2; i2++) {
					if (getBlock(i1, i2, this) != null) {
						getBlock(i1, i2, this).updateLight();
					}
				}
			}
		}

		updateNeighbourValues();
	}

	protected void initLight() {

		if (getBlock(0, 1, this) != null) {
			getBlock(0, 1, this).updateLight();
		} else if (BlockAir.getBlock(xCord, yCord + 1) != null) {
			BlockAir.getBlock(xCord, yCord + 1).updateLight();
		}

		if (getBlock(0, -1, this) != null) {
			getBlock(0, -1, this).updateLight();
		} else if (BlockAir.getBlock(xCord, yCord - 1) != null) {
			BlockAir.getBlock(xCord, yCord - 1).updateLight();
			if (BlockAir.getBlock(xCord, yCord - 1).isDirectLight()) {
				BlockAir.getBlock(xCord, yCord - 1).setDirectLight(false);
			}
		}

		if (getBlock(1, 0, this) != null) {
			getBlock(1, 0, this).updateLight();
		} else if (BlockAir.getBlock(xCord + 1, yCord) != null) {
			BlockAir.getBlock(xCord + 1, yCord).updateLight();
		}

		if (getBlock(-1, 0, this) != null) {
			getBlock(-1, 0, this).updateLight();
		} else if (BlockAir.getBlock(xCord - 1, yCord) != null) {
			BlockAir.getBlock(xCord - 1, yCord).updateLight();
		}

		if (isTop()) {

			for (int i = 0; i < GameData.worldHeight; i++) {
				Block b = GameData.instance().blockMap[getXCord()][i];

				if (b != null && b.lightValue == GameData.instance().sunLight) {
					b.updateLight();
				}
			}
		}
	}

	protected void updateNeighbourValues() {

		if (getBlock(0, -1, this) != null) {
			aB = getBlock(0, -1, this).getLightValue();

		} else if (BlockAir.getBlock(getXCord(), getYCord() - 1) != null) {
			aB = BlockAir.getBlock(getXCord(), getYCord() - 1).getLightValue();
		} else {
			aB = lightValue;
		}

		aB = (aB - 100) * -1;
		aB = aB / 100f;

		if (getBlock(0, 1, this) != null) {
			aT = getBlock(0, 1, this).getLightValue();

		} else if (BlockAir.getBlock(getXCord(), getYCord() + 1) != null) {
			aT = BlockAir.getBlock(getXCord(), getYCord() + 1).getLightValue();
		} else {
			aT = lightValue;
		}

		aT = (aT - 100) * -1;
		aT = aT / 100f;

		if (getBlock(1, 0, this) != null) {
			aR = getBlock(1, 0, this).getLightValue();

		} else if (BlockAir.getBlock(getXCord() + 1, getYCord()) != null) {
			aR = BlockAir.getBlock(getXCord() + 1, getYCord()).getLightValue();
		} else {
			aR = lightValue;
		}

		aR = (aR - 100) * -1;
		aR = aR / 100f;

		if (getBlock(-1, 0, this) != null) {
			aL = getBlock(-1, 0, this).getLightValue();

		} else if (BlockAir.getBlock(getXCord() - 1, getYCord()) != null) {
			aL = BlockAir.getBlock(getXCord() - 1, getYCord()).getLightValue();
		} else {
			aL = lightValue;
		}

		aL = (aL - 100) * -1;
		aL = aL / 100f;

		gp = new GradientPaint(0, 0, new Color(0, 0, 0, aL), 60, 0, new Color(
				0, 0, 0, aR));

		gp1 = new GradientPaint(0, 0, new Color(0, 0, 0, aT), 0, 60, new Color(
				0, 0, 0, aB));

		if (onScreen) {

			BufferedImage buf = new BufferedImage(60, 60,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) buf.getGraphics();

			g.setPaint(gp);
			g.fillRect(0, 0, width, height);

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));

			g.setPaint(gp1);
			g.fillRect(0, 0, width, height);

			g.dispose();

			b1 = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
			g = (Graphics2D) b1.getGraphics();

			try {
				g.drawImage(terrainMap, 0, 0, 60, 60,
						60 * getTextureLocation().x,
						60 * getTextureLocation().y,
						60 * getTextureLocation().x + 60,
						60 * getTextureLocation().y + 60, null);

			} catch (Exception e) {
				System.err.println("IOException: " + e.getMessage());
			}

			g.drawImage(buf, 0, 0, null);

		} else if (getY() < -10 * 60 || getY() > 10 * 60) {
			b1 = null;
		} else if (getX() < -20 * 60 || getX() > 20 * 60) {
			b1 = null;
		}

	}

	public void setLightValue(byte v) {
		if (v <= 100 && v >= 0) {
			lightValue = v;
		}
	}

	public void setCoordinates(short x, short y) {
		xCord = x;
		xCord2 = x*60;
		yCord = y;
		xCord2 = y*60;
	}

	public short getXCord() {
		return xCord;
	}

	public short getYCord() {
		return yCord;
	}

	public boolean isTop() {

		for (int i = yCord + 1; i < GameData.worldHeight; i++) {
			if (GameData.instance().blockMap[xCord][i] != null) {
				return false;
			}
		}

		return true;
	}

	public boolean isFloating() {
		boolean f = true;

		if (getBlock(-1, 0, this) != null) {
			f = false;
		}

		if (getBlock(1, 0, this) != null) {
			f = false;
		}

		if (getBlock(0, 1, this) != null) {
			f = false;
		}

		if (getBlock(0, -1, this) != null) {
			f = false;
		}

		return f;

	}

	// Finds a block relative to block in parameter.
	protected static Block getBlock(int xShift, int yShift, Block b1) {

		int x = b1.getXCord() + xShift, y = b1.getYCord() + yShift;

		return getBlock(x, y);
	}

	// Returns block at given coords.
	public static Block getBlock(int x, int y) {
		if (GameData.isValid(x, y)) {
			return GameData.instance().blockMap[x][y];
		} else {
			return null;
		}
	}

	public void load() {
		setLocation(Frame.game.m.cordToPos(this.getXCord(), this.getYCord()));
		
		initLight();
	}

	public static boolean collision(int xCord, int yCord, boolean b) {

		if (GameData.isValid(xCord, yCord)
				&& GameData.instance().blockMap[xCord][yCord] != null) {
			return true;
		}

		if (b) {

			Point p = Frame.game.m.cordToPos(xCord, yCord);
			Rectangle r1 = new Rectangle(p.x, p.y, 60, 60);

			for (int i = 0; i < GameData.instance().entityList.size(); i++) {
				Entity e = GameData.instance().entityList.get(i);

				if (e.isSolid()) {
					Rectangle r2 = e.getBounds();

					if (r1.intersects(r2)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public void remove() {
		GameData.instance().blockList.remove(this);
		GameData.instance().blockMap[xCord][yCord] = null;

		for (int a = -1; a < 2; a++) {
			for (int b = -1; b < 2; b++) {
				if (getBlock(a, b, this) != null) {
					getBlock(a, b, this).updateLight();
				}
			}
		}
		if (isTop()) {

			for (int i = 0; i < GameData.worldHeight; i++) {
				if (GameData.instance().blockMap[xCord][i] != null
						&& GameData.instance().blockMap[xCord][i].isTop()) {
					GameData.instance().blockMap[xCord][i].updateLight();
				}
			}
		}

		BlockAir.create(xCord, yCord, true);
		BlockAir.getBlock(xCord, yCord).updateLight();

	}

	public static BufferedImage getTexture(Point p) {
		BufferedImage b = new BufferedImage(60, 60, terrainMap.getType());
		Graphics g = b.getGraphics();
		g.drawImage(terrainMap, 0, 0, 60, 60, 60 * p.x, 60 * p.y,
				60 * p.x + 60, 60 * p.y + 60, null);

		return b;
	}

	protected void screenState(boolean b) {
		if (onScreen != b) {
			if (b) {
				BufferedImage buf = new BufferedImage(60, 60,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = (Graphics2D) buf.getGraphics();

				g.setPaint(gp);
				g.fillRect(0, 0, width, height);

				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_IN));

				g.setPaint(gp1);
				g.fillRect(0, 0, width, height);

				g.dispose();

				b1 = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
				g = (Graphics2D) b1.getGraphics();

				try {
					g.drawImage(terrainMap, 0, 0, 60, 60,
							60 * getTextureLocation().x,
							60 * getTextureLocation().y,
							60 * getTextureLocation().x + 60,
							60 * getTextureLocation().y + 60, null);

				} catch (Exception e) {
					System.err.println("IOException: " + e.getMessage());
				}

				g.drawImage(buf, 0, 0, null);

			} else if (getY() < -10 * 60 || getY() > 10 * 60) {
				b1 = null;
			} else if (getX() < -20 * 60 || getX() > 20 * 60) {
				b1 = null;
			}
		}

		onScreen = b;
	}

	public void paintComponent(Graphics g) {

		g.drawImage(b1, getX(), getY(), null);
	}
}
