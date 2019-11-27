
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;


public class Bird extends Sprite 
{
	private double yvel;
	private double gravity;
	private double rotation;
	
	
	
	public Bird()
	{
		initBird();
	}

	private void initBird() 
	{
		// TODO Auto-generated method stub
		x=100;
		y=150;
		yvel=0;
		gravity=0.9;
		rotation=0.0;
		image =new ImageIcon("bird-4.png").getImage();
		
	}
	
	public void update()
	{
		yvel+=gravity;
		y+=(int) yvel;
	}
	public AffineTransform getTransform()
	{
		rotation=(90*(yvel+10)/20)-90;
		rotation=rotation*Math.PI/2/180;
		if (rotation>Math.PI/2) 
		{
			rotation=Math.PI/2;
		}
	
	var aft =new AffineTransform();
	aft.translate(x+GameConstant.BIRD_WIDTH/2,
			y+GameConstant.BIRD_HEIGHT/2);
	aft.rotate(rotation); 
	aft.translate(-GameConstant.BIRD_WIDTH/2,
			-GameConstant.BIRD_HEIGHT/2);
	return aft;
	}
	public void setVelocity(int val)
	{
		yvel=val;
	}
	public static int getWidth()
	{
		return GameConstant.BIRD_WIDTH;
	}
	public int getHeight()
	{
		return GameConstant.BIRD_HEIGHT;
	}
	public void KeyPressed(KeyEvent e)
	{
		if (e.getKeyCode()==KeyEvent.VK_SPACE);
		
		
	}

}
