package TheBird;

public class Main 
{
	public static void main(String[] args) 
	{
		
		Game g = new Game();
	   //initialise game objects			
		
		Pipes p = new Pipes();
		Bird b = new Bird(p);
		
		
		// add updatables and rendarables
		g.addRenderable(p);
		g.addUpdatable(p);
		
		g.addRenderable(b);
		g.addUpdatable(b);
		
		//Start!
		
		g.start();
	}

}
