package wackyBird2;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	
	private AudioClip clip;
	private AudioClip clip2;
	
	public static Sound test = new Sound("sound.wav");
	//public static Sound test2 = new Sound("sounds/voice.wav");
	
	public Sound(String path) {
		clip = Applet.newAudioClip(getClass().getResource(path));
	}
	
	public void play() {
		new Thread() {
			public void run() {
				clip.play();
			}
		}.start();
	}
	
//	public void level2() {
//		new Thread() {
//			public void run() {
//				clip2 = Applet.newAudioClip(getClass().getResource("sounds/voice.wav"));
//				clip2.play();
//			}
//		}.start();
//	}
}
