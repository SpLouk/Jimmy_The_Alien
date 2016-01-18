package com.jimmythealien.src;

import java.awt.Point;

public abstract class EntityFlora extends EntitySaveable{

	Point grow;
	short blockX, blockY;
	
	
	
	public String toFile(){
		return super.toFile() + "&" + blockX + "&" + blockY + "&" + grow.x + "&" + grow.y;
	}
	
	protected void fromFile(String[] args){
		super.fromFile(args);
		
		BlockDirt b = (BlockDirt)Block.getBlock(Integer.parseInt(args[4]), Integer.parseInt(args[5]));
		placeOnBlock(b);

		blockX = b.getXCord();
		blockY = b.getYCord();
		
		b.growFlora(false, this);
		
		grow = new Point(Integer.parseInt(args[6]), Integer.parseInt(args[7]));
		
		this.place();
	}
}
