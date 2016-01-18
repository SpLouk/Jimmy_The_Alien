package com.jimmythealien.src;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class EntityDamageValue extends Entity{
	final String damage;
	private int opacity = 100, hVel, vVel;
	

	public EntityDamageValue(int x, int y, int d) {

		Random r = new Random();
		hVel = r.nextInt(9) - 4;
		vVel = r.nextInt(10) + 5;
		
		damage = Integer.toString(d);
		
		setLocation(x, y);
		setCoordinates(Frame.game.m.posToCord(x, y));
		setPaintLevel((byte) 6);
	}

	public void onUpdate() {
		
		move();
		
		opacity -= 10;
		
		if(opacity == 0){
			this.remove();
		}
	}

	public void move() {			
			if (vVel > 2) {
				moveEntity(hVel, vVel);
				
				vVel -= 1;
			}
	}

	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		Composite temp = g2d.getComposite();
		g2d.setColor(Color.red);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity/100f));
		g.drawString(damage, getX(), getY());
		
		g2d.setComposite(temp);
	}
}
