package jimmyTheAlien;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelIntro extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6504682311979280699L;
	private BufferedImage credit;

	PanelIntro() {

		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocusInWindow();

		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentShown(java.awt.event.ComponentEvent evt) {
				formComponentShown(evt);
			}
		});

		try {
			credit = ImageIO.read(getClass().getResource(
					"/Resources/credit.png"));
		} catch (Exception e) {
		}

	}

	private void formComponentShown(java.awt.event.ComponentEvent evt) {
		Frame.game.load();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.white);
		int x = this.getWidth();
		int y = this.getHeight();
		int w2 = credit.getWidth() / 2;
		int h2 = credit.getHeight();
		g2d.fillRect(0, 0, x, y);
		g2d.drawImage(credit, null, x / 2 - w2, y / 2 - h2);

	}
}
