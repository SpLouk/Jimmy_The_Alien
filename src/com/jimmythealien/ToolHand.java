package jimmyTheAlien;

import java.awt.Color;
import java.awt.Graphics;

public class ToolHand extends ToolDigger {

	public ToolHand(Inventory i) {
		super(0, i);
	}

	public void use() {
		
		Entity e = Entity.collision(loc);
		if(e != null && e.isLiving() && inventory.entity != e){
			EntityLiving e1 = (EntityLiving)e;
			
			e1.addHealth(-inventory.entity.damage);
		}
	}

	public void paintComponent(Graphics g) {

		if(active){
			g.setColor(Color.white);
			g.drawLine(loc.x - 5, loc.y, loc.x + 5, loc.y);
			g.drawLine(loc.x, loc.y - 5, loc.x, loc.y + 5);
		}
	}
}
