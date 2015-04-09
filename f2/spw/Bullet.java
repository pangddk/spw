package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet extends Sprite{

	public static final int Y_TO_DIE = 30;

	private int step = 20;
	private boolean alive = true;

	public Bullet(int x, int y){
		super(x, y, 5, 15);
	}

	@Override
	public void draw(Graphics2D b){
		b.setColor(Color.GREEN);
		b.fillRect(x, y, width, height);
	}
	
	public void proceed(){
		y -= step;
		if(y < Y_TO_DIE){
			alive = false;
		}
	}

	public boolean isAlive(){
		return alive;
	}
}