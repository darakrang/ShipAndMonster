/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipandmonster;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Dara
 */
public class Kraken extends SeaMonster {

    public Kraken() {
        super();
        symbol = 'K';
    }

    public Kraken(String dataLine) {
        super(dataLine);
    }

    public String battleCry() {
        //TO DO: occupies the same square as any ship
        System.out.println("RELEASE ME!");
        try {
            FileInputStream in = new FileInputStream("sounds/kraken.wav");
            // create an audiostream from the inputstream
            AudioStream audioStream = new AudioStream(in);
            // play the audio clip with the audioplayer class
            AudioPlayer.player.start(audioStream);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Godzilla.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Godzilla.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "RELEASE ME!";
    }

    @Override
    public String displayMonster() {
        String report = new String();
        report = ("Kraken: " + this.getLabel() + "\n");
        report += ("Location (" + this.getPosition().getLongitude() + "," + this.getPosition().getLatitude() + ")" + "\n");
        report += String.format("Location (%d,%d)\n\n", this.getPosition().getRow(), this.getPosition().getColumn());
        return report;
    }

}
