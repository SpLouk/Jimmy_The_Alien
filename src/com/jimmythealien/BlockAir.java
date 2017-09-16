package jimmyTheAlien;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BlockAir extends Object {

	private int xCord, yCord;
	private byte lightValue;
	private boolean directLight = false;
	protected BufferedImage b1;
	protected GradientPaint gp, gp1;

	public BlockAir(int xCord, int yCord) {
		setCoordinates(xCord, yCord);
		setSize(60, 60);
		GameData.instance().airList.add(this);
		GameData.instance().airMap[xCord][yCord] = this;

		setPaintLevel((byte) 5);
	}

	public static void create(int xCord, int yCord, boolean light) {
		if (xCord < GameData.rightBound && yCord < GameData.worldHeight
				&& !Block.collision(xCord, yCord, false)
				&& !collision(xCord, yCord)) {

			BlockAir b = new BlockAir(xCord, yCord);
			b.load(light);
		}
	}

	public void setLightValue(int v) {
		if (v <= 100 && v >= 5) {
			lightValue = (byte) v;
		} else if (v < 5) {
			lightValue = 5;
		}
	}

	public void setCoordinates(int x, int y) {
		xCord = x;
		yCord = y;
	}

	public int getXCord() {
		return xCord;
	}

	public int getYCord() {
		return yCord;
	}

	public int getLightValue() {
		return lightValue;
	}

	public boolean isDirectLight() {
		return directLight;
	}

	public boolean isTop() {

		for (int i = yCord + 1; i < GameData.worldHeight; i++) {
			if (GameData.instance().airMap[xCord][i] != null) {
				return false;
			}
		}

		return true;
	}

	public static boolean collision(int xCord, int yCord) {
		if (GameData.isValid(xCord, yCord)
				&& GameData.instance().airMap[xCord][yCord] != null) {
			return true;
		} else {
			return false;
		}
	}

	public void updateLight() {
		BlockAir b = updateLight(0);

		while (b != null) {
			b = b.updateLight(0);
		}
	}

	private BlockAir updateLight(int i) {

		BlockAir rBlock = null;

		if (directLight && getYCord() != GameData.worldHeight - 1) {
			if (getBlock(0, 1, this) == null) {
				directLight = false;
			} else if (!getBlock(0, 1, this).directLight) {
				directLight = false;
			}
		}
		int temp = lightValue;

		if (!directLight) {

			setLightValue(0);

			if (this.isTop()) {
				directLight = true;
				setLightValue(GameData.instance().sunLight);
			}

			if (getBlock(0, 1, this) != null
					&& getBlock(0, 1, this).directLight) {
				directLight = true;
				setLightValue(GameData.instance().sunLight);
			}

			for (int a = -1; a < 2; a++) {
				for (int b = -1; b < 2; b++) {
					if (getBlock(a, b, this) != null
							&& getBlock(a, b, this).getLightValue() - 10 > lightValue) {
						setLightValue(getBlock(a, b, this).getLightValue() - 10);
					}
				}
			}
		} else {
			setLightValue(GameData.instance().sunLight);
		}

		if (lightValue != temp) {
			for (int i1 = -1; i1 < 2; i1++) {
				for (int i2 = -1; i2 < 2; i2++) {
					if (getBlock(i1, i2, this) != null) {
						if (i < 1000) {
							rBlock = getBlock(i1, i2, this).updateLight(i + 1);
						} else {
							rBlock = this;
						}

					}

					if (Block.getBlock(xCord + i1, yCord + i2) != null) {
						Block.getBlock(xCord + i1, yCord + i2).updateLight();
					}
				}
			}
		}

		updateNeighbourValues();

		return rBlock;
	}

	protected void updateNeighbourValues() {

		if (!directLight) {
			float aB, aT, aL, aR;

			if (getBlock(0, -1, this) != null) {
				aB = getBlock(0, -1, this).getLightValue();
			} else {
				aB = lightValue;
			}

			aB = (aB - 100) * -1;
			aB = aB / 100f;

			if (getBlock(0, 1, this) != null) {
				aT = getBlock(0, 1, this).getLightValue();
			} else {
				aT = lightValue;
			}

			aT = (aT - 100) * -1;
			aT = aT / 100f;

			if (getBlock(1, 0, this) != null) {
				aR = getBlock(1, 0, this).getLightValue();
			} else {
				aR = lightValue;
			}

			aR = (aR - 100) * -1;
			aR = aR / 100f;

			if (getBlock(-1, 0, this) != null) {
				aL = getBlock(-1, 0, this).getLightValue();
			} else {
				aL = lightValue;
			}

			aL = (aL - 100) * -1;
			aL = aL / 100f;

			gp = new GradientPaint(0, 0, new Color(0, 0, 0, aL), 60, 0,
					new Color(0, 0, 0, aR));

			gp1 = new GradientPaint(0, 0, new Color(0, 0, 0, aT), 0, 60,
					new Color(0, 0, 0, aB));

			if (onScreen) {

				b1 = new BufferedImage(60, 60, BufferedImage.TYPE_4BYTE_ABGR);
				Graphics2D g = (Graphics2D) b1.getGraphics();

				g.setPaint(gp);
				g.fillRect(0, 0, width, height);

				g.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC_IN));

				g.setPaint(gp1);
				g.fillRect(0, 0, width, height);

			} else if (getY() < -10 * 60 || getY() > 10 * 60) {
				b1 = null;
			} else if (getX() < -20 * 60 || getX() > 20 * 60) {
				b1 = null;
			}

		} else if (onScreen) {
			b1 = new BufferedImage(60, 60, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = (Graphics2D) b1.getGraphics();

			float aL = (lightValue - 100) * -1;
			aL = aL / 100f;

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					aL));

			g.setColor(Color.black);
			g.fillRect(0, 0, width, height);

		}
	}

	public void setDirectLight(boolean b) {
		directLight = b;

		if (b) {
			setLightValue(GameData.instance().sunLight);

			if (getBlock(1, 0, this) != null) {
				getBlock(1, 0, this).updateLight();
			} else if (Block.getBlock(getXCord() + 1, getYCord()) != null) {
				Block.getBlock(getXCord() + 1, getYCord()).updateLight();
			}

			if (getBlock(-1, 0, this) != null) {
				getBlock(-1, 0, this).updateLight();
			} else if (Block.getBlock(getXCord() - 1, getYCord()) != null) {
				Block.getBlock(getXCord() - 1, getYCord()).updateLight();
			}

			if (getBlock(0, -1, this) != null
					&& !getBlock(0, -1, this).directLight) {
				getBlock(0, -1, this).setDirectLight(true);
			}

			this.updateNeighbourValues();

		} else {
			updateLight();
		}
	}

	// Finds a block relative to block in parameter.
	protected static BlockAir getBlock(int xShift, int yShift, BlockAir b1) {
		int x = b1.getXCord() + xShift, y = b1.getYCord() + yShift;

		return getBlock(x, y);
	}

	// Returns block at given coords.
	public static BlockAir getBlock(int x, int y) {
		if (GameData.isValid(x, y)) {
			return GameData.instance().airMap[x][y];
		} else {
			return null;
		}
	}

	public void remove() {
		GameData.instance().airList.remove(this);
		GameData.instance().airMap[xCord][yCord] = null;

		for (int i1 = -1; i1 < 2; i1++) {
			for (int i2 = -1; i2 < 2; i2++) {
				if (getBlock(i1, i2, this) != null) {
					getBlock(i1, i2, this).updateLight();
				}
			}
		}
	}

	public void load(boolean light) {
		setLocation(Frame.game.m.cordToPos(this.getXCord(), this.getYCord()));
		if (light) {
			updateLight();
		}
	}

	protected void screenState(boolean b) {
		if (onScreen != b) {
			if (b) {

				if (!directLight) {
					b1 = new BufferedImage(60, 60,
							BufferedImage.TYPE_4BYTE_ABGR);
					Graphics2D g = (Graphics2D) b1.getGraphics();

					g.setPaint(gp);
					g.fillRect(0, 0, width, height);

					g.setComposite(AlphaComposite
							.getInstance(AlphaComposite.SRC_IN));

					g.setPaint(gp1);
					g.fillRect(0, 0, width, height);
				} else {
					b1 = new BufferedImage(60, 60,
							BufferedImage.TYPE_4BYTE_ABGR);
					Graphics2D g = (Graphics2D) b1.getGraphics();

					float aL = (lightValue - 100) * -1;
					aL = aL / 100f;

					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, aL));

					g.setColor(Color.black);
					g.fillRect(0, 0, width, height);
				}

			} else if (getY() < -10 * 60 || getY() > 10 * 60) {
				b1 = null;
			} else if (getX() < -20 * 60 || getX() > 20 * 60) {
				b1 = null;
			}
		}

		onScreen = b;
	}

	public void paintComponent(Graphics g) {
		if (lightValue != 100) {
			g.drawImage(b1, getX(), getY(), null);
		}
	}
}
