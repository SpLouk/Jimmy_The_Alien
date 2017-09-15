package com.jimmythealien.src;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameData {
	int windLevel, t, d;
	byte sunLight = 100;
	ArrayList<Block> blockList;
	ArrayList<BlockAir> airList;
	ArrayList<Entity> entityList;
	LightEngine lighting;

	static final short rightBound = 128, worldHeight = 128;
	static final int day = 4800;
	private static final String blockFile = "BlockDat.txt", entityFile = "EntityDat.txt", 
			gameFile = "gameDat.txt", split = "/";

	Block[][] blockMap = new Block[rightBound][worldHeight];
	BlockAir[][] airMap = new BlockAir[rightBound][worldHeight];

	public GameData(int windLevel, int t, int d, ArrayList<Block> blockList,
			ArrayList<BlockAir> airList, ArrayList<Entity> entityList,
			LightEngine lighting) {
		this.windLevel = windLevel;
		this.t = t;
		this.d = d;
		this.blockList = blockList;
		this.airList = airList;
		this.entityList = entityList;
		this.lighting = lighting;
	}

	public GameData() {
		this.windLevel = 0;
		this.t = 0;
		this.d = 0;
		this.blockList = new ArrayList<Block>();
		this.airList = new ArrayList<BlockAir>();
		this.entityList = new ArrayList<Entity>();
		this.lighting = new LightEngine();
	}

	public Point getTime(int i) {
		int t1 = t + i, d1 = d;

		if (t1 >= day) {
			d1 += t1 / day;
			t1 %= day;
		}

		return new Point(d1, t1);
	}

	public boolean isTime(Point p) {
		if (t == p.y && d == p.x)
			return true;
		 else 
			return false;
	}

	public static boolean isValid(int x, int y) {
		if (x > -1 && x < GameData.rightBound && y > -1
				&& y < GameData.worldHeight) {
			return true;
		} else return false;
	}

	public static GameData instance() {
		return Frame.game.data;
	}
	
	public void save(){
		try {
            File write = new File(blockFile);
            
            if (!write.exists()) {
                write.createNewFile();
            }

            FileWriter fw = new FileWriter(write);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = worldHeight - 1; i >= 0; i--){
            	for(int i1 = 0; i1 < rightBound; i1++){
            		if(blockMap[i1][i]!= null){
            			Block b1 = blockMap[i1][i];
            			
            			for(int i2 = 0; i2 < Block.blocks.length; i2++){
            				if(b1.getClass() == Block.blocks[i2].getClass()){
            					String s = i2 + "";
            					
            					s += split;
            					bw.write(s);
            				}
            			}
            		} else {
            			bw.write("*" + split);
            		}
            	}
            	bw.newLine();
            }
            bw.close();
            
            write = new File(entityFile);
            
            if (!write.exists()) {
                write.createNewFile();
            }
            
            fw = new FileWriter(write);
            bw = new BufferedWriter(fw);
            
            for(int i = 0; i < entityList.size(); i++){
            	Entity e = entityList.get(i);
            	if(e instanceof EntitySaveable){
            		EntitySaveable e1 = (EntitySaveable)e;
            		bw.write(e1.toFile());
            		bw.newLine();
            	}
            }
            
            bw.close();
            
            write = new File(gameFile);
            
            if (!write.exists()) {
                write.createNewFile();
            }
            
            fw = new FileWriter(write);
            bw = new BufferedWriter(fw);
            
            bw.write(d + "");
            bw.newLine();
            bw.write(t + "");
            
            bw.close();

        } catch (IOException e) {
        }
	}
	
	public void load(){
		File file1 = new File(blockFile);
		File file2 = new File(entityFile);
		File file3 = new File(gameFile);
		if (file1.exists() && file2.exists() && file3.exists()) {
            openFile();
        } else {
        	new WorldGen().generateNewWorld();
        	Frame.game.newJimmy();
        	Frame.game.newJimmy();
        	save();
        }
	}
	
	private void openFile(){
        try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(new File(blockFile)));
            String s;
            int i1 = worldHeight - 1;
            while ((s = reader.readLine()) != null) {
            	String[] row = s.split(split);
            	
            	for(int i = 0; i < rightBound; i++){
            		if(!row[i].equals("*")){
            			Block.create((short)i, (short)i1, Integer.parseInt(row[i]), false);
            		} else {
            			BlockAir.create((short)i, (short)i1, false);
            		}
            	}
            	
            	i1 --;
            }
            reader.close();
            
            reader = new BufferedReader(new FileReader(new File(entityFile)));
            while ((s = reader.readLine()) != null) {
            	int i = s.indexOf("&");
            	String arg1 = s.substring(0, i);
            	s = s.substring(i + 1);
            	String[] row = s.split("&");
            	EntitySaveable e = EntitySaveable.entities[Integer.parseInt(arg1)].newEntity();
            	e.fromFile(row);
            }
            reader.close();
            
            reader = new BufferedReader(new FileReader(new File(gameFile)));
            s = reader.readLine();
            d = Integer.parseInt(s);
            s = reader.readLine();
            t = Integer.parseInt(s);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}