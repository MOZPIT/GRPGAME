package wackyBird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Scene {

	public ArrayList<Rectangle> pipes;
	private int time;
	private int currentTime = 0;
	
	private Random rand = new Random(300);
	private int speed = 5;
	
	public Scene(int time) {
		pipes = new ArrayList<>();
		this.time = time;
	}
	
	public void update() {
		currentTime++;
		if(currentTime == time) {
			currentTime = 0;
			
			int space = 250;
			int width = 150;
			int height = rand.nextInt(GameCore.HEIGHT/2);
			
			pipes.add(new Rectangle(GameCore.WIDTH + width + pipes.size() * 300, GameCore.HEIGHT - height -120, width, height));
			pipes.add(new Rectangle(GameCore.WIDTH + width + (pipes.size() - 1) * 300, 0, width, GameCore.HEIGHT - height - space));
		}
		
		for(int i = 0; i < pipes.size(); i++) {
			Rectangle rect = pipes.get(i);
			rect.x -= speed;
			
			if(rect.x + rect.width <= 0) {
				pipes.remove(i--);
				continue;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.blue);
		
		for(int i = 0; i < pipes.size(); i++) {
			Rectangle rect = pipes.get(i);
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
	}
}
