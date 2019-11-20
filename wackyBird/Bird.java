package wackyBird2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

@SuppressWarnings("unused")
public class Bird extends Rectangle{
	
	private static final long serialVersionUID = 1L;
	
	private int speed = 4;
	public boolean keyPressed = false;
	public boolean gameOver = false, restart = false, restartGame = true;
	public boolean flappingUp = false, flappingDown = false;
	private BufferedImage flapUp;
	private BufferedImage flapDown;
	
	
	private ArrayList<Rectangle> pipes;
	
	public Bird(int x, int y, ArrayList<Rectangle> pipes) {
		setBounds(x,y,32,32);
		this.pipes = pipes;
		
		try {
			flapUp = Sprite.getSprite("images/flapUpBird.png");
			flapDown = Sprite.getSprite("images/flapDownBird.png");
			
		}catch(IOException ex){
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		
	}
	
	public void update() {
		
		if(keyPressed) {
			y -= speed;
			flappingUp = true;
			flappingDown = false;
		}else {
			y += speed;
			flappingUp = false;
			flappingDown = true;
		}
		
		//check for collision
		for(int i = 0; i < pipes.size(); i++) {
			if(this.intersects(pipes.get(i))){
					gameOver = true;	
					GameCore.scene = new Scene(80);
					pipes = GameCore.scene.pipes;
					y = GameCore.HEIGHT/2;
					break;
			}
			
//			if(gameOver) {
//				//take a nap
//				try {
//					Thread.sleep(2);
//				    gameOver = false;
//				}
//				catch (InterruptedException ex) {}
//			}
		}                   
		
		
		
		
	}
	
	public void render(Graphics g) {
 		g.setColor(Color.red);
		
		if(flappingUp) { 
			g.drawImage(flapUp,x,y,width,height,null);
		}else {
		    g.drawImage(flapDown,x,y,width,height,null);
		}
	
		if(gameOver) {
			g.drawString("Game Over!", 100, GameCore.HEIGHT / 2 - 40); 
		}
		
//		if(restartGame) {
//			g.drawString("Press R to restart", 100, GameCore.HEIGHT / 2 - 40); 
//		}
	}

}
