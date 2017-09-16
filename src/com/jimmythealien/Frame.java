package jimmyTheAlien;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame {

	static GraphicsDevice myDevice;
	static Rectangle screenSize;
	static JFrame frame = new JFrame("Jimmy The Alien");
	static JPanel main = new JPanel(new CardLayout());;
	static PanelGame game = new PanelGame();
	static PanelIntro intro = new PanelIntro();

	public static void addComponent(Container pane, String s) {
		main.add(pane, s);
		frame.add(main);
	}

	public static void createAndShowGUI() {
		
		screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(480, 360));
		frame.pack();
		Dimension d = new Dimension(screenSize.width - frame.getInsets().left
				- frame.getInsets().right, screenSize.height
				- frame.getInsets().top - frame.getInsets().bottom);

		intro.setPreferredSize(d);

		addComponent(game, "Main");
		addComponent(intro, "Intro");
		setShown("Intro");
		frame.pack();
		frame.setVisible(true);

		// Display the window.

	}

	public static void setShown(String window) {
		CardLayout cl = (CardLayout) (main.getLayout());
		cl.show(main, window);
	}
}
