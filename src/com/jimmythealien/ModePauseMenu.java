package jimmyTheAlien;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ModePauseMenu extends Mode {

	static BufferedImage b1;
	static boolean init;
	Mode m;

	public ModePauseMenu(Mode m) {
		Frame.game.pause();
		this.m = m;
		Frame.game.repaint();

		if (!init) {

			b1 = new BufferedImage(Frame.game.getWidth(),
					Frame.game.getHeight(), BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = (Graphics2D) b1.getGraphics();

			GradientPaint gp = new GradientPaint(0, 0, new Color(0, 0, 0, 25),
					0, Frame.game.getHeight(), new Color(0, 0, 0, 0.35f));

			g2d.setPaint(gp);
			g2d.fillRect(0, 0, Frame.game.getWidth(), Frame.game.getHeight());

			init = true;
		}
	}

	protected void keyPressed(java.awt.event.KeyEvent evt) {

		if (evt.getKeyCode() == 27) {
			Frame.game.resume();
			Frame.game.m = this.m;
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(b1, 0, 0, null);
	}
}
