/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;
/**
 *
 * @author Dara
 */
public class Box extends Cargo{
//    private String description;
    private int teus;
    
    public Box(){
        this.description = "Marble";
        this.teus = 10000;
    }
    
    public Box(String description, int teus){
        this.description = description;
        this.teus = teus;
    }
    
    public Box(String dataLine) {
        //This is the Constructor        
        String[] token = dataLine.split(",");            
        this.description = token[0];
        this.teus = Integer.parseInt(token[1]);  
    }
    
    //generate data to one string line
    @Override
    public String toString() {
        String string = "";
        string = this.description + "," + this.getTeus();
        return string;
    }

    /**
     * @return the teus
     */
    public int getTeus() {
        return teus;
    }

    /**
     * @param teus the teus to set
     */
    public void setTeus(int teus) {
        this.teus = teus;
    }
    
    //print out the current information about the cargo
    @Override
    public String display() {
        return String.format("%d teus of %s\n", this.getTeus(), this.getDescription());
    }
}