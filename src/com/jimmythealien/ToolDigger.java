package jimmyTheAlien;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class ToolDigger extends Tool {

	private boolean occupied;
	int tLevel, var1;

	public ToolDigger(int i, Inventory i1) {
		super(i1);
		tLevel = i;
	}

	public void onUpdate() {

		if (occupied && active) {
			Block b = Block.getBlock(p.x, p.y);
			if (Frame.game.leftMouse && p.x == b.getXCord()
					&& p.y == b.getYCord()) {

				EntityBlockPiece b1 = new EntityBlockPiece(loc.x - b.getX(),
						loc.y - b.getY(), tLevel, b);

				if (b.getHardness() > 0 && var1 >= b.getHardness() * 10) {
					if (inventory.addItem(b.getItem())) {
						b.remove();
					}
					var1 = 0;
				} else {
					var1 += 1 + tLevel;
				}

			} else {
				var1 = 0;
			}
		} else {
			var1 = 0;
		}

	}

	protected void active() {

		Point temp = p;

		super.active();

		if (temp.x != p.x || temp.y != p.y) {
			var1 = 0;
		}

		if (!Block.collision(p.x, p.y, false)) {
			occupied = false;
		} else {
			occupied = true;
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		if (active) {

			if (occupied) {
				g.setColor(Color.green);
			} else {
				g.setColor(Color.red);
			}

			g.drawLine(loc.x - 5, loc.y, loc.x, loc.y + 5);
			g.drawLine(loc.x, loc.y + 5, loc.x + 5, loc.y);
			g.drawLine(loc.x + 5, loc.y, loc.x, loc.y - 5);
			g.drawLine(loc.x, loc.y - 5, loc.x - 5, loc.y);

		}
	}
}
