package wackyBird2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class BackDrop {
	private String filename;
	public BufferedImage backdrop = null;
	private int level;
	public int dx = 0;
	public int dy = 0;
	public int dz = 0;
	/* Constructor */
	public BackDrop(int level) {
		this.level = level;
	}

	public void update() {
		if(level == 1) {
			filename = "images/airadventurelevel2.png";
		}else if(level == 2) {
			filename = "images/airadventurelevel4.png";
			
		}
		
		if(Bird.getBirdX() > GameCore.WIDTH && level == 1) {
			dx = 0;
		}else if(Bird.getBirdX() > (GameCore.WIDTH*2) && level == 2) {
			dx = 0;
		}else {
			dx = Bird.getBirdX();
		}
	}

	public void render(Graphics g) {
		if(backdrop != null) {
				g.drawImage(backdrop, (dx - Camera.x/10), -200, null);
		}else {
			try {
			    try {
					backdrop = ImageIO.read(new File(getClass().getResource(filename).toURI()));
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
	}
}