package com.jimmythealien.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class ModeMenu extends Mode {

	EntityLiving e;
	int a = 0, hMove, vMove;
	BufferedImage menu;
	final boolean lock;

	public ModeMenu() {
		e = getLivingEntity();

		int x = Frame.game.getWidth() / 2 - e.getX();
		int y = -e.getY();

		move(x, y);

		Random r = new Random();
		hMove = r.nextInt(9) - 4;
		vMove = r.nextInt(9) - 4;

		try {
			menu = ImageIO.read(getClass().getResource("/Resources/0.5.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		lock = false;
	}
	
	public ModeMenu(EntitySentient e2) {
		e = e2;

		int x = Frame.game.getWidth() / 2 - e.getX();
		int y = -e.getY();

		move(x, y);

		Random r = new Random();
		hMove = r.nextInt(9) - 4;
		vMove = r.nextInt(9) - 4;

		try {
			menu = ImageIO.read(getClass().getResource("/Resources/0.5.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		lock = true;
	}

	@Override
	public void onUpdate() {
		
		if (Frame.game.keys[32]) {
			if(lock){
				Frame.game.m = new ModePlayer((EntitySentient)e);
			} else {
			for(int i = 0; i < GameData.instance().entityList.size(); i++){
				Entity e = GameData.instance().entityList.get(i);
				
				if(e instanceof EntitySentient){
					Frame.game.m = new ModePlayer((EntitySentient)e);
					return;
				}
			}
			
			Frame.game.m = new ModeFreeRoam();
			return;

			}
		}

		if (a == 100) {
			if(!lock){
			e = getLivingEntity();
			}

			int x = Frame.game.getWidth() / 2 - e.getX();
			int y = Frame.game.getHeight() / 2 - e.getY();

			move(x, y);

			Random r = new Random();
			hMove = r.nextInt(9) - 4;
			vMove = r.nextInt(9) - 4;
			a = r.nextInt(50);
		} else {
			a++;
		}

		move(hMove, vMove);

	}

	private EntityLiving getLivingEntity() {

		Entity e = null;
		boolean loop = true;
		while (loop) {
			Random r = new Random();
			e = GameData.instance().entityList.get(r.nextInt(GameData
					.instance().entityList.size()));
			if (e instanceof EntityLiving) {
				return (EntityLiving) e;
			}
		}

		return null;

	}

	@Override
	public void paintComponent(Graphics g) {

		g.drawImage(menu, 25, 0, null);

		Font f = new Font("Helvetica", Font.PLAIN, Frame.game.getWidth() / 64);
		g.setFont(f);
		g.setColor(Color.red);
		g.drawString("Press [SPACE] to Play", Frame.game.getWidth() / 2,
				(Frame.game.getHeight() * 4) / 5);
	}

}
