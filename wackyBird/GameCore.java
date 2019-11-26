package wackyBird2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Renderer;

public class GameCore extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800, HEIGHT = 600;
	private boolean started = false;
	private Thread thread;
	
	public static Pipe pipe;
	public Bird bird;
	public BackDrop backdrop;
	public static double score = 0;
	
	//Constructor
	public GameCore() {
		Dimension d = new Dimension(GameCore.WIDTH, GameCore.HEIGHT);
		setPreferredSize(d);
		addKeyListener(this);
		
		pipe = new Pipe(60);
		bird = new Bird(20,GameCore.HEIGHT/2,pipe.pipes);
	}
	
	public synchronized void start() {
		if(started) return;
		started = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!started) return;
		started = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

	public static void main(String[] args) {
		JFrame jFrame = new JFrame();
		GameCore game = new GameCore();
		jFrame.add(game);
		
		jFrame.setResizable(false);
		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setTitle("Wacky Bird");
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		
		game.start();
		
	}

	@Override
	public void run() {
		int fps = 0;
		long lastUpdated = System.nanoTime();
		double timer = System.currentTimeMillis();
		double delta = 0;
		double nanosecs = 1000000000 / 60;
		
		/* game loop */
		while(started) {
			long now = System.nanoTime();
			delta += (now - lastUpdated) / nanosecs;
			lastUpdated = now;
			
			while(delta >= 1) {
				update();
				render();
				fps++;
				delta--;
				
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + fps);
			}
			
			if(score <= 3) {
				backdrop = new BackDrop(1);
			}else {
				backdrop = new BackDrop(2);
			}
		}
		
		stop();
		
	}

	private void render() {	
		BufferStrategy buf = getBufferStrategy();
		if(buf == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = buf.getDrawGraphics();
		
		backdrop.render(g);
		
		g.setColor(Color.orange);
		g.fillRect(0,  HEIGHT - 140, WIDTH, 150);
		
		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - 140, WIDTH, 20);
	
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana",Font.BOLD, 21));
		g.drawString("Score: "+ (int)score, 10, 20);
		
		
		pipe.render(g);
		bird.render(g); 
		
		
		g.dispose();
		buf.show();
	}
	
//	public static BufferedImage loadImages() {
////		return bgImgLevel1 = loadImage("airadventurelevel2.png");
//	}

	private void update() {
		backdrop.update();
		pipe.update();
		bird.update();
//		this.loadImages();
	}
	
//	public static BufferedImage loadImage(String fileName){
//		//return new ImageIcon(fileName).getImage();
//		return new BufferedImage(fileName).getSource();
//	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			bird.keyPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			bird.keyPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R) {
			bird.restart = true;
		}
	}
}
