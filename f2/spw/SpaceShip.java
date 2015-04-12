package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class SpaceShip extends Sprite{

	int step = 10;
	BufferedImage img;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		try{
			img = ImageIO.read(new File("f2/image/sps.png"));
		}
		catch(IOException e){

		}

	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, x, y, width, height, null);
	}


	public void move_LR(int direction){ 
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}
	public void move_UD(int direction){ 
		y += (step * direction);
		if(y < 0)
			y = 0;
		if(y > 600 - height)
			y = 600 - height;
	}
}
