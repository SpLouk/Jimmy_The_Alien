package jimmyTheAlien;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class HeadsUpDisplay {

	final static int healthW = 50;

	static BufferedImage rGradient, inv, invSlot, select;
	BufferedImage inv2, healthPlate;
	static boolean init = false;

	protected int selX = 24, invY, slot, slot2, health;
	protected boolean invShow;
	EntitySentient entity;

	public HeadsUpDisplay(EntitySentient e1) {

		if (!init) {
			Color[] colors = { new Color(0, 0, 0, 0), new Color(0, 0, 0, 0.05f) };
			float[] dist = { 0.9f, 1f };
			int radius = 200;
			Point2D center = new Point(200, 200);
			RadialGradientPaint paint = new RadialGradientPaint(center, radius,
					dist, colors);
			rGradient = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) rGradient.getGraphics();
			g.setPaint(paint);
			g.fillOval(0, 0, 400, 400);

			g.dispose();

			inv = new BufferedImage(60 * 9 + 30 + 10 * 8, 60 + 30,
					BufferedImage.TYPE_INT_ARGB);
			g = (Graphics2D) inv.getGraphics();

			AlphaComposite a;
			g.setColor(Color.black);
			for (int i = 0; i < 10; i++) {
				a = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) i / 500);
				g.setComposite(a);
				g.fillRect(i, i, inv.getWidth() - 2 * i, inv.getHeight() - 2
						* i);
			}
			a = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
			g.setComposite(a);

			GradientPaint gp = new GradientPaint(0, 0, new Color(0, 0, 0, 0),
					0, inv.getHeight(), new Color(0, 0, 0, 0.15f));

			g.setColor(new Color(193, 218, 214));
			g.fillRect(10, 10, inv.getWidth() - 20, inv.getHeight() - 20);
			g.setPaint(gp);
			g.fillRect(10, 10, inv.getWidth() - 20, inv.getHeight() - 20);

			for (int i = 0; i < 9; i++) {

				g.setColor(new Color(230, 231, 231));
				g.fillRect(15 + 10 * i + 60 * (i), 15, 60, 60);
				g.setColor(new Color(168, 190, 186));
				g.drawRect((15 + 10 * i + 60 * (i)) - 1, 14, 61, 61);
			}

			g.dispose();

			invSlot = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);
			g = (Graphics2D) invSlot.getGraphics();

			g.setColor(new Color(165, 186, 183));
			g.fillRect(0, 0, inv.getWidth(), inv.getHeight());

			g.setColor(new Color(230, 231, 231));
			g.fillRect(5, 5, 60, 60);

			g.dispose();

			select = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);
			g = (Graphics2D) select.getGraphics();
			g.setColor(Color.black);
			g.drawRect(0, 0, 69, 69);
			g.drawRect(5, 5, 59, 59);
			g.setColor(Color.white);
			g.drawRect(1, 1, 67, 67);
			g.drawRect(2, 2, 65, 65);
			g.drawRect(3, 3, 63, 63);
			g.drawRect(4, 4, 61, 61);

			g.dispose();

			init = true;
		}

		entity = e1;

		int i1 = entity.inventory.getSize() - 9;
		if (i1 % 9 == 0) {
			inv2 = new BufferedImage(70 * 9, 70 * (i1 / 9),
					BufferedImage.TYPE_INT_ARGB);
		} else {
			inv2 = new BufferedImage(70 * 9, 70 + 70 * (i1 / 9),
					BufferedImage.TYPE_INT_ARGB);
		}

		Graphics2D g = (Graphics2D) inv2.getGraphics();

		for (int i = 0; i < entity.inventory.getSize() - 9; i++) {
			g.drawImage(invSlot, 70 * (i % 9), 70 * (i / 9), null);
		}

		health = entity.getHealth();
		drawHealthPlate();
	}

	protected void mouseMoved(java.awt.event.MouseEvent evt) {
		Point p = Frame.game.mouseLoc;
		if (p.x <= 25 + inv.getWidth() - 20 && p.x >= 25
				&& p.y <= 25 + inv.getHeight() - 20 && p.y >= 25) {
			invShow = true;
		} else if (invShow && p.x <= 25 + inv2.getWidth() && p.x >= 25
				&& p.y <= 95 + inv2.getHeight() && p.y >= 95) {
			invShow = true;
		} else {
			invShow = false;
		}
	}

	protected void mouseClicked(java.awt.event.MouseEvent evt) {

		if (invShow) {
			Rectangle r1 = new Rectangle(25, 25, inv.getWidth() - 20,
					inv.getHeight() - 20);
			boolean b = false;
			int slot = 0;

			if (r1.contains(evt.getPoint())) {
				for (int i = 0; i < 9; i++) {
					Rectangle r = new Rectangle(30 + 10 * i + 60 * (i), 30, 60,
							60);

					if (r.contains(evt.getPoint())) {
						b = true;
						slot = i;
					}
				}

			} else {

				for (int a = 9; a < entity.inventory.getSize(); a++) {

					int i = 30 + 10 * (a % 9) + 60 * (a % 9);

					Rectangle r = new Rectangle(i, 30 + 60 * (a / 9) + 10
							* (a / 9), 60, 60);

					if (r.contains(evt.getPoint())) {
						b = true;
						slot = a;
					}

				}
			}

			if (b) {
				this.slot = slot;
				if (entity.inventory.inHand.item == null) {
					pickUp(evt.getButton());
				} else {
					drop(evt.getButton());
				}
			}
		}
	}

	private void pickUp(int i) {

		if (entity.inventory.items[slot].item != null) {
			if (i == 1) {
				entity.inventory.inHand = entity.inventory.items[slot];
				entity.inventory.items[slot].remove();
			} else {
				Item item = entity.inventory.items[slot].item;
				int a1 = entity.inventory.items[slot].quantity / 2;
				if (a1 == 0) {
					a1 = 1;
				}
				entity.inventory.inHand = new InventoryItem(item,
						entity.inventory, a1);
				entity.inventory.items[slot].subtract(a1);
			}
		}
	}

	private void drop(int i) {
		InventoryItem hand = entity.inventory.inHand;
		Item item = hand.item;
		if (entity.inventory.items[slot].item != null) {
			if (i == 1) {
				if (entity.inventory.items[slot].item.getClass() == item
						.getClass()) {

					int i1 = entity.inventory.items[slot].add(hand.quantity);
					hand.subtract(hand.quantity - i1);
				} else {

					InventoryItem temp = entity.inventory.items[slot];
					entity.inventory.changeSlot(slot, item, hand.quantity);
					entity.inventory.changeHandSlot(temp.item, temp.quantity);
				}
			} else {
				if (entity.inventory.items[slot].item.getClass() == item
						.getClass()) {

					int i1 = entity.inventory.items[slot].add(1);
					hand.subtract(1 - i1);
				}
			}
		} else {
			if (i == 1) {

				entity.inventory.changeSlot(slot, item, hand.quantity);
				hand.remove();

			} else {

				entity.inventory.changeSlot(slot, item, 1);
				hand.subtract();
			}
		}
	}

	protected void onUpdate() {
		int i1;
		if (invShow) {
			i1 = inv2.getHeight();
		} else {
			i1 = 0;
		}

		if (invY != i1) {
			invY = invY + (i1 - invY) / 2;

			if ((i1 - invY) / 2 == 0) {
				invY = i1;
			}
		}

		if (health != entity.getHealth()) {
			health = entity.getHealth();
			drawHealthPlate();
		}
	}

	protected void drawHealthPlate() {

		healthPlate = new BufferedImage((health / 5 + 1) * healthW, healthW,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) healthPlate.getGraphics();

		float f = (float) entity.getHealth() / entity.maxHealth;

		int i = (int) (145 * f) + 55;
		Color c = new Color(255 - i, i, 0);

		GradientPaint gp = new GradientPaint(0, healthPlate.getHeight() / 2,
				new Color(0, 0, 0, 0), 0, healthPlate.getHeight(), new Color(0,
						0, 0, 0.15f));

		for (int i1 = 0; i1 < health / 5; i1++) {

			g.setColor(c);
			g.fillRect(i1 * healthW, 0, healthW, healthW);
			g.setColor(Color.black);
			g.drawRect(i1 * healthW, 0, healthW - 1, (healthW) - 1);
		}

		g.setPaint(gp);
		g.fillRect(0, 0, (health / 5) * healthW, healthW);

		int i2 = health / 5;
		for (int i1 = 0; i1 < health % 5; i1++) {
			g.setColor(c);
			g.fillRect(i2 * healthW, healthW - (i1 + 1) * (healthW / 5),
					healthW, healthW / 5);
			g.setPaint(gp);
			g.fillRect(i2 * healthW, healthW - (i1 + 1) * (healthW / 5),
					healthW, healthW / 5);
			g.setColor(Color.black);
			g.drawRect(i2 * healthW, healthW - (i1 + 1) * (healthW / 5),
					healthW - 1, healthW / 5 - 1);
		}
	}

	public void paintComponent(Graphics g) {
		if (entity.inventory.getSelectedSlot().tool.active) {

			Point b = entity.getCenter();
			g.drawImage(rGradient, b.x - 200, b.y - 200, null);

		}

		g.drawImage(healthPlate, 25, 15 + inv.getHeight() + 10, null);

		if (invY != 0) {

			int disp = invY - inv2.getHeight();

			BufferedImage b1 = new BufferedImage(inv2.getWidth(),
					inv2.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g2 = b1.getGraphics();
			g2.drawImage(inv2, 0, disp, null);

			for (int a = 9; a < entity.inventory.getSize(); a++) {

				int iX = 5 + 10 * (a % 9) + 60 * (a % 9);
				int iY = 5 + disp + 60 * ((a / 9) - 1) + 10 * ((a / 9) - 1);

				if (entity.inventory.items[a].item != null) {
					BufferedImage b = entity.inventory.items[a].item.getImg();
					if (b != null) {
						g2.drawImage(b, iX, iY, null);

						g2.setColor(Color.white);
						g2.drawString(entity.inventory.items[a].quantity + "",
								iX + 5, iY + 50);
					}
				}

			}

			g.drawImage(b1, 25, 95, null);
		}

		g.drawImage(inv, 15, 15, null);

		int i = 30, i1 = 0;
		for (int a = 0; a < 9; a++) {

			i = 30 + 10 * a + 60 * a;

			if (entity.inventory.items[a].item != null) {
				BufferedImage b = entity.inventory.items[a].item.getImg();
				if (b != null) {
					g.drawImage(b, i, 30, null);
					g.setColor(Color.white);
					g.drawString(entity.inventory.items[a].quantity + "",
							i + 5, 80);
				}
			}

			if (entity.inventory.selected == a) {
				i1 = i;
			}

		}
		int d = 5;
		if (selX != i1 - d) {
			selX = selX + (i1 - selX - d) / 2;

			if ((i1 - selX - d) / 2 == 0) {
				selX = i1 - d;
			}
		}

		g.drawImage(select, selX, 25, null);

		if (entity.inventory.inHand.item != null) {
			BufferedImage b = entity.inventory.inHand.item.getImg();
			g.drawImage(b, Frame.game.mouseLoc.x - 30,
					Frame.game.mouseLoc.y - 30, null);
			g.setColor(Color.white);
			g.drawString(entity.inventory.inHand.quantity + "",
					Frame.game.mouseLoc.x - 25, Frame.game.mouseLoc.y - 30 + 50);
		}

	}
}
