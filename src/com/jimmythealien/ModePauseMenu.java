package com.jimmythealien.src;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ModePauseMenu extends Mode {

	
	BufferedImage b1;
	Mode m;

	boolean invert = false;
	private static BufferedImage button1, button2;
	private static Rectangle button;
	
	static {
			try {
				button1 = ImageIO.read(GameData.instance().getClass().getResource(
						"/Resources/button1.png"));
				button2 = ImageIO.read(GameData.instance().getClass().getResource(
						"/Resources/button2.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			button = new Rectangle(Frame.game.getWidth()/2 - button1.getWidth()/2, Frame.game.getHeight()/2 - button1.getHeight()/2, button1.getWidth(), button1.getHeight());
			System.out.println("3");
			
	}
	
	public ModePauseMenu(Mode m) {
		Frame.game.pause();
		this.m = m;
		drawMenu();
		Frame.game.repaint();
		GameData.instance().save();
		System.out.println("Game saved.");
	}
	
	protected void mouseClicked(java.awt.event.MouseEvent evt) {
		if(button.contains(evt.getPoint())){
			GameData.instance().save();
		}
	}
	
	protected void mouseMoved(java.awt.event.MouseEvent evt) {
		if(button.contains(evt.getPoint()) && !invert){
			invert = true;
			drawMenu();
			Frame.game.repaint();
		} else if (!button.contains(evt.getPoint()) && invert){
			invert = false;
			drawMenu();
			Frame.game.repaint();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
	    if ("connect".equals(e.getActionCommand())) {
	    	System.out.println("AA");
	    }
	} 
	
	private void drawMenu(){
		b1 = new BufferedImage(Frame.game.getWidth(), Frame.game.getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D) b1.getGraphics();

		GradientPaint gp = new GradientPaint(0, 0, new Color(0, 0, 0, 25), 0,
				Frame.game.getHeight(), new Color(0, 0, 0, 0.35f));

		g2d.setPaint(gp);
		g2d.fillRect(0, 0, Frame.game.getWidth(), Frame.game.getHeight());
		
		button.x = Frame.game.getWidth()/2 - button.width/2;
		button.y = Frame.game.getHeight()/2 - button.height/2;
		
		if(invert){
			g2d.drawImage(button2, button.x, button.y, null);
		} else {
			g2d.drawImage(button1, button.x, button.y, null);
		}
		
	}
	
	protected void componentResized(java.awt.event.ComponentEvent evt) {
		drawMenu();
		Frame.game.repaint();
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
