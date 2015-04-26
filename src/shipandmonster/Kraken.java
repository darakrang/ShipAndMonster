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
public class Kraken extends SeaMonster {
    
    public Kraken()
    {
        super();
        symbol = 'K';
    }
    
    public void battleCry(){
        //TO DO: occupies the same square as any ship
        System.out.println("RELEASE ME!");
    }

    @Override
    public String displayMonster() {
        String report = new String();
        report = ("Kraken: " + this.getLabel()+"\n");
        report +=("Location (" + this.getPosition().getLongitude() + "," + this.getPosition().getLatitude() + ")"+"\n");
        report += String.format("Location (%d,%d)\n\n", this.getPosition().getRow(), this.getPosition().getColumn());
        return report;
    }
    
}
