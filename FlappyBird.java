import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.JFrame;
public class FlappyBird extends JFrame{

	private static final long serialVersionUID = 1L;
	public FlappyBird() 
	{
		initUI();
	}
	private void initUI() 
	{
		
		var panel =new Board(); add(panel);
		setResizable(false);    pack();
		
		setTitle("Flappy Bird");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[]arg) 
	{
		
		String filepath1="jump.wav";
		String filepath="BackGroundAudio.wav";
		
		Object Sound = Sound=new Sound();
		((Sound) Sound).playMusic(filepath);
		
		
		//Object SoundBird=SoundBird=new SoundBird();
		//((SoundBird)SoundBird).playMusic(filepath1);
		
		
		
		EventQueue.invokeLater(()->{
			var game=new FlappyBird(); 
			game.setVisible(true);
		});
		//public void KeyPressed (KeyEvent e)
		//{
		//	if (e.getKeyCode()==KeyEvent.VK_SPACE)
		//		filepath1.play()
				
		//}
	}

}
