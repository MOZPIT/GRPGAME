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
	}
	
	public void render(Graphics g) {
		if(backdrop != null) {
				g.drawImage(backdrop, 0, 0, GameCore.WIDTH, GameCore.HEIGHT, null);
		}else {
			try {
			    try {
					backdrop = ImageIO.read(new File(getClass().getResource(filename).toURI()));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		}
	}
}
