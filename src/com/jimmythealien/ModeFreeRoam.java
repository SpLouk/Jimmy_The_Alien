package jimmyTheAlien;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ModeFreeRoam extends Mode {

	private int moveX = 0, moveY = 0;
	private static final int maxSpeed = 104;
	
	protected void componentResized(java.awt.event.ComponentEvent evt) {
	}

	protected void mouseClicked(java.awt.event.MouseEvent evt) {
		
		for (int i = 0; i < GameData.instance().entityList.size(); i++) {
			Entity e = GameData.instance().entityList.get(i);

			if (e instanceof EntitySentient) {
				Rectangle r1 = e.getBounds();
				if (r1.contains(evt.getPoint())) {
					Frame.game.m = new ModePlayer((EntitySentient) e);
					return;
				}
			}
		}
	}
	
	protected void keyPressed(java.awt.event.KeyEvent evt) {
		if (evt.getKeyCode() == 27) {
			Frame.game.m = new ModePauseMenu(this);
		}
		
		if (evt.getKeyCode() == 32) {
			Frame.game.newJimmy().load();
		}
	}

	public void onUpdate() {
		if (Frame.game.keys[37]) { // left arrow
			if (moveX < maxSpeed) {
				moveX += 8;
			}
		} else if (Frame.game.keys[39]) { // right arrow
			if (moveX > -maxSpeed) {
				moveX -= 8;
			}
		} else {
			if (moveX > 0) {
				moveX -= 4;
			} else if (moveX < 0) {
				moveX += 4;
			}
		}

		if (Frame.game.keys[38]) { // up arrow
			if (moveY < maxSpeed) {
				moveY += 8;
			}
		} else if (Frame.game.keys[40]) { // down arrow
			if (moveY > -maxSpeed) {
				moveY -= 8;
			}
		} else {
			if (moveY > 0) {
				moveY -= 4;
			} else if (moveY < 0) {
				moveY += 4;
			}
		}

		if (cordToPos(0, 0).x + moveX >= 0) {
			if (moveX > 0) {
				moveX = - cordToPos(0 , 0).x;
			}
		}

		if (cordToPos(GameData.rightBound, 0).x + moveX <= Frame.game
				.getWidth()) {
			if (moveX < 0) {
				moveX = Frame.game.getWidth() - cordToPos(GameData.rightBound, 0).x;
			}
		}

		if (cordToPos(0, 0).y + moveY <= Frame.game.getHeight()) {
			if (moveY < 0) {
				moveY = Frame.game.getHeight() - cordToPos(0, 0).y;
			}
		}

		move(moveX, moveY);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Font f = new Font("Helvetica", Font.PLAIN, Frame.game.getWidth() / 64);
		g.setFont(f);
		g.setColor(Color.red);
		g.drawString("Press [SPACE] to spawn a Jimmy.", Frame.game.getWidth() / 3,
				(Frame.game.getHeight() * 4) / 5);
	}
}
