package com.jimmythealien.src;

import java.util.Random;

public class WorldGen {

	static final int i1 = GameData.worldHeight / 2; // number of layers of stone
													// block.
	Random r = new Random();
	short[] shift = new short[GameData.rightBound];
	Block newBlock;

	public void generateNewWorld() {
		hardRockLayer();
		stoneLayer();
		dirtLayer();
		airLayer();

	}

	private void hardRockLayer() {
		for (short i = 0; i < 3; i++) {
			for (short a = 0; a < GameData.rightBound; a++) {
				Block.create(a, i, 2, false);
			}
		}

	}

	private void stoneLayer() {

		for (short i = 0; i < GameData.rightBound; i++) {
			shift[i] = 0;
			for (short a = 3; a < i1; a++) {
				shift[i] += 1;
				Block.create(i, shift[i], 1, false);
			}
		}

		for (int b = 0; b < r.nextInt(GameData.rightBound / 8)
				+ GameData.rightBound / 16; b++) {
			createHill();
		}

	}

	private void dirtLayer() {
		for (short i = 0; i < GameData.rightBound; i++) {
			for (short a = 0; a < 5; a++) {
				shift[i] += 1;
				Block.create(i, shift[i], 0, false);
			}
		}

		for (int b = 0; b < r.nextInt(GameData.rightBound / 8)
				+ GameData.rightBound / 16; b++) {
			createCave();
		}
		for (int i = 0; i < GameData.instance().blockList.size(); i++) {
			Block b = GameData.instance().blockList.get(i);
			if (b.isTop() && b.getBlockType().equals("Dirt")) {
				if (r.nextInt(12) == 0) {
					EntityTree e = new EntityTree(b);
					BlockDirt c = (BlockDirt) b;
					c.growFlora(false, e);
				} else {
					BlockDirt c = (BlockDirt) b;
					c.growGrass(false);
				}
			}
		}
	}

	private void airLayer() {

		for (int i = 0; i < GameData.rightBound; i++) {
			for (int a = 0; a < GameData.worldHeight; a++) {
				BlockAir.create(i, a, false);
			}
		}
	}

	private void createHill() {
		short start = (short) r.nextInt(GameData.rightBound), chance = 2, i = start, d = 16;
		boolean inv = false, rev = r.nextBoolean(), a = true, b, c = false;

		while (a) {

			if (rev) {
				i++;
			} else {
				i++;
			}
			if (d == 0) {
				d = 1;
			}
			if (r.nextInt(d) == 0 && !c) {
				if (inv) {
					inv = false;
				} else {
					inv = true;
				}

				d = 32;
			} else {
				d--;
			}

			if (i < GameData.rightBound && i >= 0) {

				try {
					if (shift[i] < shift[i - 1]) {
						shift[i] = shift[i - 1];
					}
				} catch (Exception e) {
				}

				b = true;

				while (b) {
					if (r.nextInt(chance) + 1 == chance) {

						if (inv) {
							shift[i] -= 1;
						} else {
							shift[i] += 1;
						}

						chance *= 2;
					} else {
						b = false;
						chance /= 2;
					}
				}

				if (i > GameData.rightBound - 10
						&& shift[i] > GameData.rightBound - i + i1 - 1) {
					shift[i] = (short) (GameData.rightBound - i + i1 - 1);
				}

				if (r.nextInt(shift[i]) >= r.nextInt(64)) {
					d /= 2;
				}

				for (short e = shift[i]; e > 0; e--) {
					if (!Block.collision(i, e, false)) {
						Block.create(i, e, 1, false);
					}
				}

				if (r.nextInt(i - start) > r.nextInt(GameData.rightBound / 2)) {
					if (r.nextBoolean() && r.nextBoolean()) {
						a = false;
					} else {
						c = true;
						inv = true;
					}
				}

				if (c && shift[i] <= i1) {
					a = false;
				}

			} else {
				a = false;
			}
		}
	}

	private void createCave() {
		int start = r.nextInt(GameData.rightBound), height = r.nextInt(2) + 2, chance = 2, i = start, d = 16, l = 0;
		boolean inv = false, rev = r.nextBoolean(), a = true, f = true;

		while (f) {
			l = r.nextInt(64) + 1;
			if (Block.collision(i, l, false)) {
				f = false;
			}
		}

		while (a) {

			if (rev) {
				i--;
			} else {
				i++;
			}

			if (d == 0) {
				d = 1;
			}
			if (r.nextInt(d) == 0) {
				if (inv) {
					inv = false;
				} else {
					inv = true;
				}

				d = 32;
			} else {
				d--;
			}

			if (i < GameData.rightBound && i >= 0) {

				if (r.nextInt(chance) + 1 == chance) {

					if (r.nextInt(4) != 0) {
						if (inv) {
							l -= 1;
						} else {
							l += 1;
						}
					}

					chance *= 2;
				} else {
					chance /= 2;
				}

				for (int e = l; e < l + height; e++) {
					if (Block.collision(i, e, false)
							&& !Block.getBlock(i, e).getBlockType()
									.equals("HardRock")) {
						Block.getBlock(i, e).remove();
					}
				}

				if (i > start) {
					if (r.nextInt(i - start) > r.nextInt(GameData.rightBound)) {
						height -= 1;
					} else if (r.nextInt(i - start) == 0) {
						height += 1;
					}
				} else {
					if (r.nextInt(start - i) > r.nextInt(GameData.rightBound)) {
						height -= 1;
					} else if (r.nextInt(start - i) == 0) {
						height += 1;
					}
				}

				if (height < 3) {
					a = false;
				}
			} else {
				a = false;
			}
		}
	}

}
