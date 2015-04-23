/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipandmonster;

/**
 *
 * @author Dara
 */
public class Godzilla extends SeaMonster {
    
    public void battleCry(){
        //TO DO: occupies the same square as another sea monster
        System.out.println("Baraaaawr-rompf!");
    }

    @Override
    public String displayMonster() {
        String report = new String();
        report = ("Godzilla: " + this.getLabel()+"\n");
        report +=("Location (" + this.getPosition().getLongitude() + "," + this.getPosition().getLatitude() + ")"+"\n");
        report += String.format("Location (%d,%d)\n\n", this.getPosition().getRow(), this.getPosition().getColumn());
        return report;
    }
    
}
