import javax.swing.ImageIcon;
public class Pipe extends Sprite
{
	private int  speed =2 ;
	private Orientation orientation;
	
	public Pipe(Orientation orientation)
	{
		initPipe(orientation);
	}

	private void initPipe(Orientation orientation) 
	{
		// TODO Auto-generated method stub
		this.orientation=orientation;
		
		String pipeOrient;
		if (orientation==Orientation.NORTH)
		{
			pipeOrient="north";
		}
		else
		{
			pipeOrient="south";
		}
		image= new ImageIcon("pipe.png").getImage();
		
	}
	public void update()	
	{
		x-=speed;
	}
	public boolean collides(int _x, int _y,int _width ,int _height)
	{
		int margin=2;	
		if (_x+_width-margin>x && _x+margin<x+GameConstant.PIPE_WIDTH)
		{
			if (orientation==Orientation.SOUTH && _y<y+GameConstant.PIPE_HEIGHT)
			{
				return true;
			}else if(orientation==Orientation.NORTH&&_y+_height>y)
			{
				return true;
			}
		}
		return false;
	}
	public Orientation getOrientation()
	{
		return orientation; 
	}
	public int getHeight()
	{
		return GameConstant.PIPE_HEIGHT;
	}
	public int getWidth()
	{
		return GameConstant.PIPE_WIDTH;
	}
	public int getSpeed()
	{
		return speed;
	}
	@Override
	public String toString()
	{
		return "Pipe{"+"x="+getX()+",y ="+getY()+"orientation="
	+getOrientation()+'}';
	}
}
