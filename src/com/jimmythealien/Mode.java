package jimmyTheAlien;

import java.awt.Graphics;
import java.awt.Point;

public class Mode {

	private boolean xCamActive = true, yCamActive = true;

	protected void componentResized(java.awt.event.ComponentEvent evt) {
		try {

			xCamActive = true;
			yCamActive = true;
			centerCam();
		} catch (Exception e) {
		}
	}

	protected void keyPressed(java.awt.event.KeyEvent evt) {
	}

	protected void keyReleased(java.awt.event.KeyEvent evt) {
	}

	protected void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
	}

	protected void mouseClicked(java.awt.event.MouseEvent evt) {
	}

	protected void mouseMoved(java.awt.event.MouseEvent evt) {
	}

	protected void mouseDragged(java.awt.event.MouseEvent evt) {
	}

	protected boolean getXCameraState() {
		return xCamActive;
	}

	protected boolean getYCameraState() {
		return yCamActive;
	}

	protected int getFocX() {
		return 0;
	}

	protected int getFocY() {
		return 0;
	}

	protected int getFocWidth() {
		return 0;
	}

	protected int getFocHeight() {
		return 0;
	}

	protected float getFocXCord() {
		return 0;
	}

	protected float getFocYCord() {
		return 0;
	}

	protected void setXCameraState(boolean b) {
		xCamActive = b;
	}

	protected void setYCameraState(boolean b) {
		yCamActive = b;
	}

	public void cameraFollow() {
		int xMove = 0, yMove = 0;

		if (cordToPos(0, 0).x >= 0) {
			xMove = -cordToPos(0, 0).x;
			setXCameraState(false);
		}

		if (cordToPos(GameData.rightBound, 0).x <= Frame.game.getWidth()) {
			xMove = Frame.game.getWidth() - cordToPos(GameData.rightBound, 0).x;
			setXCameraState(false);
		}

		if (!getXCameraState() && getFocX() >= 3 * Frame.game.getWidth() / 4
				&& cordToPos(0, 0).x == 0) {
			setXCameraState(true);
		}

		if (!getXCameraState() && getFocX() <= Frame.game.getWidth() / 4
				&& cordToPos(GameData.rightBound, 0).x == Frame.game.getWidth()) {
			setXCameraState(true);
		}

		if (cordToPos(0, 0).y <= Frame.game.getHeight()) {
			yMove = Frame.game.getHeight() - cordToPos(0, 0).y;
			setYCameraState(false);
		}

		if (!getYCameraState() && getFocY() <= Frame.game.getHeight() / 4
				&& cordToPos(0, 0).y == Frame.game.getHeight()) {
			setYCameraState(true);
		}

		if (xCamActive) {
			int newX = Frame.game.getWidth() / 2 - getFocWidth() / 2;
			xMove = (newX - getFocX()) / 8;

			if ((newX - getFocX()) / 2 == 0) {
				xMove = newX - getFocX();
			}
		}

		if (yCamActive) {
			int newY = Frame.game.getHeight() / 2 - getFocHeight() / 2;
			yMove = (newY - getFocY()) / 4;
			if ((newY - getFocY()) / 2 == 0) {
				yMove = newY - getFocY();
			}

		}

		move(xMove, yMove);

	}

	public void centerCam() {
		int xMove = 0, yMove = 0;

		if (cordToPos(0, 0).x >= 0) {
			xMove = -cordToPos(0, 0).x;
			setXCameraState(false);
		}

		if (cordToPos(GameData.rightBound, 0).x <= Frame.game.getWidth()) {
			xMove = Frame.game.getWidth() - cordToPos(GameData.rightBound, 0).x;
			setXCameraState(false);
		}

		if (!getXCameraState() && getFocX() >= 3 * Frame.game.getWidth() / 4
				&& cordToPos(0, 0).x == 0) {
			setXCameraState(true);
		}

		if (!getXCameraState() && getFocX() <= Frame.game.getWidth() / 4
				&& cordToPos(GameData.rightBound, 0).x == Frame.game.getWidth()) {
			setXCameraState(true);
		}

		if (cordToPos(0, 0).y <= Frame.game.getHeight()) {
			yMove = Frame.game.getHeight() - cordToPos(0, 0).y;
			setYCameraState(false);
		}

		if (!getYCameraState() && getFocY() <= Frame.game.getHeight() / 4
				&& cordToPos(0, 0).y == Frame.game.getHeight()) {
			setYCameraState(true);
		}

		if (xCamActive) {
			int newX = Frame.game.getWidth() / 2 - getFocWidth() / 2;
			xMove = (newX - getFocX());
		}

		if (yCamActive) {
			int newY = Frame.game.getHeight() / 2 - getFocHeight() / 2;
			yMove = newY - getFocY();

		}

		move(xMove, yMove);

	}

	protected void move(int xDist, int yDist) {

		for (int c = 0; c < GameData.instance().blockList.size(); c++) {
			GameData.instance().blockList.get(c).setLocation(
					GameData.instance().blockList.get(c).getX() + xDist,
					GameData.instance().blockList.get(c).getY() + yDist);
		}
		for (int c = 0; c < GameData.instance().entityList.size(); c++) {
			Entity e = GameData.instance().entityList.get(c);
			e.setLocation(e.getX() + xDist, e.getY() + yDist);
		}

		for (int c = 0; c < GameData.instance().airList.size(); c++) {
			BlockAir e = GameData.instance().airList.get(c);
			e.setLocation(e.getX() + xDist, e.getY() + yDist);
		}

		// Frame.game.formMouseMoved();
	}

	public Point cordToPos(float xCord, float yCord) {

		Block b = GameData.instance().blockMap[0][0];
		float newX = b.getX() + (xCord) * 60;
		float newY = b.getY() - (yCord) * 60;

		newX = Math.round(newX);
		newY = Math.round(newY);

		return (new Point((int) newX, (int) newY));

	}

	public Point posToCord(int xPos, int yPos) {
		float newX, newY;

		Point p = cordToPos(0, 0);

		newX = (xPos - p.x) / 60;
		newY = (p.y - yPos) / 60;

		return new Point((int) newX, (int) newY);
	}

	public void onUpdate() {
	}

	public void paintComponent(Graphics g) {
	}

}
