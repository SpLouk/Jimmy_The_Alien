package jimmyTheAlien;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class ToolBlock extends Tool {

	private ItemBlock item;
	private boolean occupied = false;
	private int x1, y1;

	public ToolBlock(ItemBlock i, Inventory i1) {
		super(i1);
		item = i;
	}

	@Override
	protected void active() {
		super.active();

		Point p1 = Frame.game.m.cordToPos(p.x, p.y);

		if (!Block.collision(p.x, p.y, true)) {
			occupied = false;
		} else {
			occupied = true;
		}

		x1 = p1.x;
		y1 = p1.y;
	}

	@Override
	protected void use() {
		if (!occupied && active) {
			if (Block.create((byte) p.x, (byte) p.y, item.getBlock(), true)) {
				inventory.items[inventory.selected].subtract();
				Block.getBlock(p.x, p.y).updateLight();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		if (active) {

			Graphics2D g2d = (Graphics2D) g;
			BufferedImage b1 = Block.getTexture(item.getBlock()
					.getTextureLocation());

			Composite temp = g2d.getComposite();
			g2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.15f));

			if (occupied) {
				g2d.setColor(Color.red);
			} else {
				g2d.setColor(Color.green);
			}

			g2d.fillRect(x1, y1, 60, 60);

			g2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.25f));

			g2d.drawImage(b1, null, x1, y1);

			g2d.setComposite(temp);
		}
	}
}
