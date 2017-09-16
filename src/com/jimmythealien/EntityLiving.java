package jimmyTheAlien;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public abstract class EntityLiving extends EntityObject {

	private Point heal;
	public static final int BEH_PASSIVE = 0, BEH_NEUTRAL = 1,
			BEH_AGGRESIVE = 3;
	
	protected Model model;
	protected final int jumpHeight, moveSpeed, maxHealth, behaviour, damage, sight;
	private int health;
	protected int moveInt = 0;

	public EntityLiving(int health, int jump, int move, int sight, int behaviour, int damage) {
		maxHealth = health;
		addHealth(health);

		moveSpeed = move;
		jumpHeight = jump;
		this.behaviour = behaviour;
		this.damage = damage;
		this.sight = sight;
		
		heal = new Point(0, 0);
		setPaintLevel((byte) 1);
	}
	
	public void onUpdate(){
		super.onUpdate();
		
		if (GameData.instance().isTime(heal)) {
			addHealth(1);
			heal = new Point(0, 0);
		}
		
		if(health != maxHealth && heal.x == 0 && heal.y == 0){
			heal = GameData.instance().getTime(120);
		} else if (health == maxHealth){
			heal = new Point(0, 0);
		}
	}

	public void setModel(Model m) {
		model = m;
	}

	protected void addHealth(int i) {
		
		if(i < 0){
			EntityDamageValue e = new EntityDamageValue(getX() + getWidth()/2, getY(), i);
		}
		
		i += health;

		if (i <= maxHealth && i > 0) {
			health = i;
		} else if (i <= 0) {
			this.kill();
		}
	}

	protected int getHealth() {
		return health;
	}
	
	protected void spawnOffScreen() {
		boolean loop = true;
		Random r = new Random();
		while (loop) {
			Block b = GameData.instance().blockList.get(r.nextInt(GameData
					.instance().blockList.size()));

			if (!b.onScreen && b.isTop()) {
				placeOnBlock(b);

				if (checkCollisionX(0) == 0) {
					loop = false;
				}
			}
		}
		
		load();
	}

	public void placeOnBlock(Block b) {
		float newX, newY;

		newX = b.getXCord() + (float) (b.getWidth() / 2 - getWidth() / 2) / 60;
		newY = b.getYCord();
		setCoordinates(newX, newY);
		moveEntity(0, getHeight());
	}

	public void up() {

		if (checkCollisionY(-1) == 2) {
			vVel += jumpHeight;
			fall = true;

		}
	}

	public void down() {

	}

	public void left() {
		moveInt = 1;
	}

	public void right() {
		moveInt = 2;
	}

	public void moveLeft() {

		int i = moveWithCheck(-moveSpeed, 0).x;

		if (i == 0) {
			model.setActInt(1);

			model.setOrientation(0);
		} else {
			model.setActInt(0);
			model.setOrientation(1);
		}

	}

	public void moveRight() {

		int i = moveWithCheck(moveSpeed, 0).x;

		if (i == 0) {
			model.setActInt(1);

			model.setOrientation(2);
		} else {
			model.setActInt(0);
			model.setOrientation(1);
		}
	}

	public void fall() {
		super.fall();

		if (vVel < 0) {
			model.setActInt(2);
		} else {
			model.setActInt(0);
		}

	}

	protected void endFall(int i) {
		if (i < 0) {

			float f = maxHealth / 100f;
			int i1 = (int) Math.pow(f, i / 10);
			i1 /= 200;

			addHealth(-i1);
		}
	}
	
	protected boolean canSee(Entity e){
		float x = getXCord() - e.getXCord();
		float y = getYCord() - e.getYCord();
		
		double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		float m = y/x;
		if(dist < sight){
			for(int i = 0; i < dist; i++){
				int x1 = (int)getXCord() + i;
				int y1 = (int)(getYCord() + (m*i));
				System.out.println(x1 + ", " + y1);
				if(GameData.instance().blockMap[x1][y1] != null){
					return false;
				}
			}
		}
		
		return true;
	}

	protected void kill() {
		remove();

	}

	public boolean isLiving() {
		return true;
	}

	public boolean isSolid() {
		return true;
	}

	@Override
	public void paintComponent(Graphics g) {
		model.paintComponent(g, Frame.game);
	}
}
