/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;

/**
 *
 * @author Dara
 */
public class Oil extends Cargo{
    private int barrels;
    
    public Oil(){
        this.description = "Light Crude";
        this.barrels = 700000;
    }
    
    public Oil(String description, int barrels){
        this.description = description;
        this.barrels = barrels;
    }
    
    public Oil(String dataLine) {
        //This is the Constructor        
        String[] token = dataLine.split(",");            
        this.description = token[0];
        this.barrels = Integer.parseInt(token[1]);    
    }
    
    //generate data to one string line    
    @Override
    public String toString() {
        String string = "";
        string = this.description + "," + this.getBarrels();
        return string;
    }

    //print out the current information about the cargo
    @Override
    public void display() {
        System.out.format("%d barrels of %s\n", this.getBarrels(), this.getDescription());
    }

    /**
     * @return the barrels
     */
    public int getBarrels() {
        return barrels;
    }

    /**
     * @param barrels the barrels to set
     */
    public void setBarrels(int barrels) {
        this.barrels = barrels;
    }
}
