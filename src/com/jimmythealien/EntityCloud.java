package com.jimmythealien.src;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class EntityCloud extends Entity{
	
	static BufferedImage cloud;
	private int vel = 0;
	
	static {
		try {
			cloud = ImageIO.read(GameData.instance().getClass().getResource("/Resources/cloud.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public EntityCloud(){
		
		Random r = new Random();
		int x =  - 20, y = r.nextInt(90) + 100;
		
		setCoordinates(x, y);
		setLocation(Frame.game.m.cordToPos(x, y));
		setSize(cloud.getWidth(), cloud.getHeight());
		
		setPaintLevel((byte) 0);
		
		vel = r.nextInt(5) + 1;
	}
	
	public void onUpdate(){
		moveEntity(vel, 0);
		
		if(getXCord() > GameData.rightBound){
			remove();
		}
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(cloud, getX(), getY(), null);
	}

}
