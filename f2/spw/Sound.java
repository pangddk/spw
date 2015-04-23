package f2.spw;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
   
public class Sound extends JFrame {
   
   public Sound() {
   
      try {

         URL url = this.getClass().getClassLoader().getResource("f2/sound/bgs.wav");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         
         Clip clip = AudioSystem.getClip();
                  clip.open(audioIn);
         clip.start();
      } 
      catch (UnsupportedAudioFileException e) {
         
      } 
      catch (IOException e) {
         
      } 
      catch (LineUnavailableException e) {
         
      }
   }
   
}
