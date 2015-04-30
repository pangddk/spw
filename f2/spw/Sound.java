package f2.spw;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import java.applet.*;
   
public class Sound extends JFrame {
   
   Clip clip;
   public Sound() {
   
      try {

         URL url = this.getClass().getClassLoader().getResource("f2/sound/bgs.wav");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         
         clip = AudioSystem.getClip();
                  clip.open(audioIn);
         
      } 
      catch (UnsupportedAudioFileException e) {
         
      } 
      catch (IOException e) {
         
      } 
      catch (LineUnavailableException e) {
         
      }
   }

   public void loop(){
      clip.loop(1);
   }
   
}
