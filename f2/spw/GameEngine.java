package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Food> food = new ArrayList<Food>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<StrongEnemy> sEnemy = new ArrayList<StrongEnemy>();
	private ArrayList<BossEnemy> bEnemy = new ArrayList<BossEnemy>();

	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.2;

	private int heart = 3;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}

	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateFood(){								
		Food f = new Food((int)(Math.random()*390), 30); 
		gp.sprites.add(f);
		food.add(f);
	}

	private void generateBullet(){								
		Bullet b = new Bullet((v.x + (v.width / 2)), v.y); 
		gp.sprites.add(b);
		bullets.add(b);
	}
	
	private void generateStrongEnemy(){								
		StrongEnemy s = new StrongEnemy((int)(Math.random()*390), 30); 
		gp.sprites.add(s);
		sEnemy.add(s);
	}

	private void generateBossEnemy(){								
		BossEnemy be = new BossEnemy(0, (int)(Math.random()*500)); 
		gp.sprites.add(be);
		bEnemy.add(be);
	}

	private void process(){
		
		if(v.y <= 60){
			score += 10000;  // bonus score
			v.x = 180;		 // back to start point
			v.y = 550;
		}

		if(Math.random() < difficulty){
			generateEnemy();
		}

		if(Math.random() < 0.05){   					
			generateFood();
		}

		if(Math.random() < (difficulty - 0.15)){   					
			generateStrongEnemy();
		}
		if((Math.random() < (difficulty - 0.18)) && (score > 50000)){   					
			generateBossEnemy();
		}

		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 1000;
			}
		}

		Iterator<Food> f_iter = food.iterator();
		while(f_iter.hasNext()){
			Food f = f_iter.next();
			f.proceed();

			if(!f.isAlive()){
				f_iter.remove();
				gp.sprites.remove(f);
			}
		}
		
		Iterator<Bullet> b_iter = bullets.iterator();
		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();

			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}

		Iterator<StrongEnemy> s_iter = sEnemy.iterator();
		while(s_iter.hasNext()){
			StrongEnemy s = s_iter.next();
			s.proceed();
			
			if(!s.isAlive()){
				s_iter.remove();
				gp.sprites.remove(s);
				score += 3000;
			}
		}
		Iterator<BossEnemy> be_iter = bEnemy.iterator();
		while(be_iter.hasNext()){
			BossEnemy be = be_iter.next();
			be.proceed();
			
			if(!be.isAlive()){
				be_iter.remove();
				gp.sprites.remove(be);
				score += 5000;
			}
		}

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				if(heart > 0){
					e.notAlive();
					score -= 1000;
					heart--;
				}
				else
					die();
				return;
			}
			Rectangle2D.Double br;			
			for(Bullet b : bullets){			
				br = b.getRectangle();
				if(br.intersects(er)){
					score += 1000;
					e.notAlive();
					return;
				}
			}
		}

		Rectangle2D.Double fr;			
		for(Food f : food){			
			fr = f.getRectangle();
			if(fr.intersects(vr)){
				score += 500;
				f.notAlive();
				return;
			}
		}
		
		Rectangle2D.Double sr;
		for(StrongEnemy s : sEnemy){
			sr = s.getRectangle();
			if(sr.intersects(vr)){
				if(heart > 0){
					s.notAlive();
					score -= 1000;
					heart--;
				}
				else
					die();
				return;
			}
			Rectangle2D.Double br;			
			for(Bullet b : bullets){			
				br = b.getRectangle();
				if(br.intersects(sr)){
					if(s.getLive() > 0){
						s.lossLive();
						score += 3000;
					}
					else{
						score += 5000;
						s.notAlive();
					}
					return;
				}
			}
		}
		Rectangle2D.Double ber;
		for(BossEnemy be : bEnemy){
			ber = be.getRectangle();
			if(ber.intersects(vr)){
				if(heart > 0){
					be.notAlive();
					score -= 1000;
					heart--;
				}
				else
					die();
				return;
			}
			Rectangle2D.Double br;			
			for(Bullet b : bullets){			
				br = b.getRectangle();
				if(br.intersects(ber)){
					if(be.getLive() > 0){
						be.lossLive();
						score += 5000;
					}
					else{
						score += 5000;
						be.notAlive();
					}
					return;
				}
			}
		}
	}
	
	
	public void die(){
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move_LR(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.move_LR(1);
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break; 
		case KeyEvent.VK_UP:		
			v.move_UD(-1);
			break;
		case KeyEvent.VK_DOWN:			
			v.move_UD(1);
			break;

		case KeyEvent.VK_P:	 		
			timer.stop();
			break;

		case KeyEvent.VK_S:	 		
			timer.start();
			break;

		case KeyEvent.VK_SPACE:	 	
			generateBullet();
			break;
		}
		

	}

	public long getScore(){
		return score;
	}

	public int getHeart(){
		return heart;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
