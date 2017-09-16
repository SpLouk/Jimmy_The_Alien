package jimmyTheAlien;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class ModePlayer extends Mode {

	private EntitySentient focus;
	protected int invSlot = 0;
	boolean space = false;

	public ModePlayer(EntitySentient f) {
		setFocus(f);
	}

	@Override
	protected void mouseMoved(java.awt.event.MouseEvent evt) {
		focus.hud.mouseMoved(evt);
	}

	protected void mouseDragged(java.awt.event.MouseEvent evt) {
		focus.hud.mouseMoved(evt);
	}

	protected void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
		if (evt.getWheelRotation() < 0) {
			focus.inventory.cycleSlot(true);
		} else {
			focus.inventory.cycleSlot(false);
		}
	}

	@Override
	protected void mouseClicked(java.awt.event.MouseEvent evt) {

		if (evt.getButton() == 1) {
			focus.inventory.getSelectedSlot().tool.use();
		} else {
			for (int i = 0; i < GameData.instance().entityList.size(); i++) {
				Entity e = GameData.instance().entityList.get(i);

				if (e instanceof EntitySentient) {
					Rectangle r1 = e.getBounds();
					if (r1.contains(evt.getPoint())) {
						if(e.isPlayer()){
							focus.setPlayer(false);
							Frame.game.m = new ModeFreeRoam();
						} else {
						setFocus((EntitySentient) e);
						}
					}
				}
			}
		}

		focus.hud.mouseClicked(evt);
	}

	protected void keyPressed(java.awt.event.KeyEvent evt) {
		
		if (evt.getKeyCode() == 27) {
			Frame.game.m = new ModePauseMenu(this);
		}

		switch (evt.getKeyCode()) {
		case KeyEvent.VK_1:
		case 97:
			focus.inventory.cycleSlot(0);
			break;
		case KeyEvent.VK_2:
		case 98:
			focus.inventory.cycleSlot(1);
			break;
		case KeyEvent.VK_3:
		case 99:
			focus.inventory.cycleSlot(2);
			break;
		case KeyEvent.VK_4:
		case 100:
			focus.inventory.cycleSlot(3);
			break;
		case KeyEvent.VK_5:
		case 101:
			focus.inventory.cycleSlot(4);
			break;
		case KeyEvent.VK_6:
		case 102:
			focus.inventory.cycleSlot(5);
			break;
		case KeyEvent.VK_7:
		case 103:
			focus.inventory.cycleSlot(6);
			break;
		case KeyEvent.VK_8:
		case 104:
			focus.inventory.cycleSlot(7);
			break;
		case KeyEvent.VK_9:
		case 105:
			focus.inventory.cycleSlot(8);
			break;
		}
	}

	@Override
	public void onUpdate() {

		
		if (Frame.game.keys[37] || Frame.game.keys[KeyEvent.VK_A]) { // left arrow
			focus.left();
		}
		if (Frame.game.keys[39] || Frame.game.keys[KeyEvent.VK_D]) { // right arrow
			focus.right();
		}
		if (Frame.game.keys[38] || Frame.game.keys[KeyEvent.VK_W]) { // up arrow
			focus.up();
		}

		if (Frame.game.keys[40] || Frame.game.keys[KeyEvent.VK_S]) { // down arrow
			focus.down();
		}

		if (!Frame.game.keys[37] && !Frame.game.keys[39]
				&& !Frame.game.keys[KeyEvent.VK_A] && !Frame.game.keys[KeyEvent.VK_D]) {
			focus.moveInt = 0;
		}

		cameraFollow();

	}

	@Override
	protected int getFocX() {
		return focus.getX();
	}

	@Override
	protected int getFocY() {
		return focus.getY();
	}

	@Override
	protected int getFocWidth() {
		return focus.getWidth();
	}

	@Override
	protected int getFocHeight() {
		return focus.getHeight();
	}

	@Override
	protected float getFocXCord() {
		return focus.getXCord();
	}

	@Override
	protected float getFocYCord() {
		return focus.getYCord();
	}

	public Entity getFocus() {
		return focus;
	}

	public void setFocus(EntitySentient newFocus) {
		if (focus != null) {
			focus.setPlayer(false);
		}

		focus = newFocus;

		if (focus != null) {
			focus.setPlayer(true);
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		focus.hud.paintComponent(g);
	}

}
