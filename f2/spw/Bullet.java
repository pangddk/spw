package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bullet extends Sprite{

	public static final int Y_TO_DIE = 30;

	private int step = 20;
	private boolean alive = true;

	BufferedImage img;

	public Bullet(int x, int y){
		super(x, y, 15, 28);

		try{
			img = ImageIO.read(new File("f2/image/bl.png"));
		}
		catch(IOException e){

		}
	}

	@Override
	public void draw(Graphics2D b){
		//b.setColor(Color.GREEN);
		//b.fillRect(x, y, width, height);

		b.drawImage(img, x, y, width, height, null);
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