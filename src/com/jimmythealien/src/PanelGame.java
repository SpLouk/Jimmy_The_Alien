package com.jimmythealien.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelGame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2815005340941697612L;
	static Timer gameClock;
	boolean[] keys = new boolean[KeyEvent.KEY_LAST];
	boolean leftMouse, rightMouse;
	protected Point mouseLoc = new Point(0, 0);
	public Mode m = new Mode();
	Point lUpdate;
	int tLight = 100;
	WorldEvents manager;

	protected GameData data = new GameData();

	public PanelGame() {
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				formMouseClicked(evt);
			}

			public void mouseReleased(MouseEvent evt) {
				formMouseReleased(evt);
			}
		});

		this.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
			@Override
			public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
				formMouseWheelMoved(evt);
			}
		});

		this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			public void mouseMoved(MouseEvent evt) {
				formMouseMoved(evt);
			}

			public void mouseDragged(MouseEvent evt) {
				formMouseDragged(evt);
			}

		});

		this.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				formKeyPressed(evt);
			}

			@Override
			public void keyReleased(java.awt.event.KeyEvent evt) {
				formKeyReleased(evt);
			}
		});

		this.addComponentListener(new java.awt.event.ComponentAdapter() {

			@Override
			public void componentResized(java.awt.event.ComponentEvent evt) {
				formComponentResized(evt);
			}
		});

		this.setFocusable(true);

		gameClock = new Timer(25, new TimerAction());

	}

	public void load() {
		
		data.load();
		
		for (int i = 0; i < data.blockList.size(); i++) {
			data.blockList.get(i).load();
		}

		for (int i = 0; i < data.airList.size(); i++) {
			data.airList.get(i).load();
		}

		for (int i = 0; i < data.entityList.size(); i++) {
			if (!data.entityList.get(i).isPlayer()) {
				data.entityList.get(i).place();
			}

		}
		newMenu();
		manager = new WorldEvents();
		lUpdate = GameData.instance().getTime(120);
		//data.lighting.init();
		gameClock.start();
		Frame.setShown("Main");
		requestFocusInWindow();

	}
	
	private void newMenu(){
		for(int i = 0; i < data.entityList.size(); i++){
			Entity e = data.entityList.get(i);
			
			if(e instanceof EntitySentient){
				EntitySentient e1 = (EntitySentient)e;
				
				if(e1.isPlayer){
					Frame.game.m = new ModeMenu(e1);
					return;
				}
			}
		}
		
		Frame.game.m = new ModeMenu();
	}
	
	EntityJimmy newJimmy(){
		EntityLiving entity1 = new EntityJimmy();

		boolean loop = true;
		Random r = new Random();
		while (loop) {
			Block b = data.blockList.get(r.nextInt(data.blockList.size()));
			if (b.isTop() && b.getBlockType().equals("Dirt")) {

				entity1.placeOnBlock(b);
				if (entity1.checkCollisionX(0) == 0) {
					
					loop = false;
				}
			}
		}
		
		return (EntityJimmy) entity1;
	}

	private void formComponentResized(java.awt.event.ComponentEvent evt) {
		m.componentResized(evt);
	}

	private void formKeyPressed(java.awt.event.KeyEvent evt) {

		m.keyPressed(evt);
		keys[evt.getKeyCode()] = true;

	}

	protected void formKeyReleased(java.awt.event.KeyEvent evt) {
		m.keyReleased(evt);
		keys[evt.getKeyCode()] = false;
	}

	protected void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
		m.mouseWheelMoved(evt);
	}

	private void formMouseClicked(MouseEvent evt) {
		m.mouseClicked(evt);

		if (evt.getButton() == 1) {
			leftMouse = true;
		}
		if (evt.getButton() == 3) {
			rightMouse = true;
		}
	}

	private void formMouseReleased(MouseEvent evt) {
		if (evt.getButton() == 1){
			leftMouse = false;
		}
		if (evt.getButton() == 3) {
			rightMouse = false;
		}
	}

	protected void formMouseMoved(java.awt.event.MouseEvent evt) {
		m.mouseMoved(evt);
		mouseLoc = new Point(evt.getX(), evt.getY());
	}

	protected void formMouseDragged(java.awt.event.MouseEvent evt) {

		m.mouseDragged(evt);
		mouseLoc = new Point(evt.getX(), evt.getY());
	}

	class TimerAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			data.t += 1;
			if (data.t == GameData.day) {
				data.t = 0;
				data.d += 1;
			}

			if (data.t == (GameData.day / 4) * 3) {
				tLight = 100;
			} else if (data.t == GameData.day / 4) {
				tLight = 100;
			}

			if (data.isTime(lUpdate)) {
				lUpdate = data.getTime(120);

				if (data.sunLight < tLight) {
					data.sunLight += 2;

					for (int i = 0; i < GameData.rightBound; i++) {
						if (data.airMap[i][GameData.worldHeight - 1] != null) {
							data.airMap[i][GameData.worldHeight - 1]
									.updateLight();
						}
					}
				} else if (data.sunLight > tLight) {
					data.sunLight -= 2;

					for (int i = 0; i < GameData.rightBound; i++) {
						if (data.airMap[i][GameData.worldHeight - 1] != null) {
							data.airMap[i][GameData.worldHeight - 1]
									.updateLight();
						}
					}
				}
			}

			for (int i = 0; i < data.entityList.size(); i++) {
				data.entityList.get(i).onUpdate();
			}

			repaint();
			m.onUpdate();
			manager.onUpdate();
		}
	}

	public int getWindLevel() {
		return data.windLevel;
	}

	public Point getMouseLoc() {
		return mouseLoc;
	}

	protected void pause() {
		gameClock.stop();
	}

	protected void resume() {
		gameClock.start();
	}

	/**
	 * The checkCollision method is used to check for blocks that are not
	 * adjacent to the entity. int x and int y are the coordinates of the area
	 * to be checked, relative to the player.
	 */

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(113, 196, 234));
		g.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i <= Object.getMaxPLevel(); i++) {

			if (i == 3) {
				for (int a = 0; a < data.blockList.size(); a++) {
					Block b = data.blockList.get(a);
					if (b.onScreen()) {
						b.paintComponent(g);
					}
				}
			}

			if (i == 5) {
				for (int a = 0; a < data.airList.size(); a++) {
					BlockAir b = data.airList.get(a);
					if (b.onScreen()) {
						b.paintComponent(g);
					}
				}
			}

			for (int a = 0; a < data.entityList.size(); a++) {
				Entity e = data.entityList.get(a);
				if (e.getPaintlevel() == i && e.onScreen()) {
					e.paintComponent(g);
				}
			}
		}

		m.paintComponent(g);
	}

}
