package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	BufferedImage h;

	BufferedImage bg01;
	BufferedImage bg02;
	BufferedImage bg03;
	BufferedImage bonus;

	
	public GamePanel() {
		bi = new BufferedImage(400, 650, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
 
		try{
			h = ImageIO.read(new File("f2/image/heart.png"));
		}
		catch(IOException e){

		}

		try{
			bg01 = ImageIO.read(new File("f2/image/bg01.jpg"));
		}
		catch(IOException e){

		}

		try{
			bg02 = ImageIO.read(new File("f2/image/bg02.jpg"));
		}
		catch(IOException e){

		}

		try{
			bg03 = ImageIO.read(new File("f2/image/bg03.jpg"));
		}
		catch(IOException e){

		}
		try{
			bonus = ImageIO.read(new File("f2/image/bonus_line.png"));
		}
		catch(IOException e){

		}

	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		
		reporter.getScore();

		if(reporter.getScore() < 80000){
			big.setColor(Color.RED);
			big.drawImage(bg01, 0, 0, 400, 650, null);
		}

		else if(reporter.getScore() >= 80000 && reporter.getScore() < 160000){
			big.setColor(Color.WHITE);
			big.drawImage(bg02, 0, 0, 400, 650, null);	
		}

		else{
			big.setColor(Color.CYAN);
			big.drawImage(bg03, 0, 0, 400, 650, null);
		}

		big.drawImage(bonus, 0, 60, 400, 40, null);

		big.drawString(String.format("Score = " + "%08d", reporter.getScore()), 250, 20);
		
		if(reporter.getHeart() == 3){
			big.drawImage(h, 10, 10, 20, 20, null);
			big.drawImage(h, 40, 10, 20, 20, null);
			big.drawImage(h, 70, 10, 20, 20, null);
		}

		else if(reporter.getHeart() == 2){
			big.drawImage(h, 10, 10, 20, 20, null);
			big.drawImage(h, 40, 10, 20, 20, null);
		}

		else if(reporter.getHeart() == 1){
			big.drawImage(h, 10, 10, 20, 20, null);
		}
		
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
