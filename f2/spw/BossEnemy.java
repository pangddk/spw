package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BossEnemy extends Sprite{
	public static final int X_TO_FADE = 300;
	public static final int X_TO_DIE = 400;
	
	private int step = 5;
	private boolean alive = true;
	private int live = 5;

	BufferedImage img;
	
	public BossEnemy(int x, int y) {
		super(x, y, 60, 60);
		try{
			img = ImageIO.read(new File("f2/image/boss_e.png"));
		}
		catch(IOException e){

		}
	}

	@Override
	public void draw(Graphics2D g) {
		if(x < X_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(X_TO_DIE - x)/(X_TO_DIE - X_TO_FADE)));
		}

		g.drawImage(img, x, y, width, height, null);

	}

	public void proceed(){
		x += step;
		if(x > X_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}

	public void notAlive(){ 
		alive = false;
	}

	public void lossLive(){
		live -= 1;
	}

	public int getLive(){
		return live;
	}
}
