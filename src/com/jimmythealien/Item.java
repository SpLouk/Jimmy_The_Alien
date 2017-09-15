package com.jimmythealien.src;

import java.awt.image.BufferedImage;

public abstract class Item {
	
	protected static final Item[] items = {new ItemDirt(), new ItemStone()};

	
	abstract Item newItem();
	
	static Item create(int i){
		return items[i].newItem();
	}
	
	protected Tool getTool(Inventory i) {
		return null;
	}

	protected BufferedImage getImg() {
		return null;
	}
	
	protected int getInt(){
		for(int i = 0; i < items.length; i++){
			if(getClass() == items[i].getClass()){
				return i;
			}
		}
		
		return -1;
	}
}
