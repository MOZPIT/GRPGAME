
import java.awt.event.KeyEvent;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Sound 
{
	void playMusic(String musiclocation)
	{
		try
		{
			File musicpath =new File (musiclocation);
			if (musicpath.exists())
			{
				AudioInputStream audioInput=AudioSystem.getAudioInputStream(musicpath);
				Clip clip=AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else 
			{
				System.out.println("Cant find file");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	 

}
   