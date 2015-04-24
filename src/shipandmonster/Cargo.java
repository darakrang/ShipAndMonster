/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;

import java.util.Scanner;

/**
 *
 * @author Dara
 */
//Class Cargoship
//
// This class reprsents a container ship
// Data Members: weight, description of the cargo.
//
public class Cargo{

    //This is the Class Space
    private double tonnage;
    protected String description;

    private Scanner systemInput;

    //Create the object
    public Cargo() {
        //This is the Constructor
        this.description = "Bananas";
        this.tonnage = 10;

        systemInput = new Scanner(System.in);
    }
    
    public Cargo(String description, double tonnage) {
        //This is the Constructor
        this.description = description;
        this.tonnage = tonnage;

        systemInput = new Scanner(System.in);
    }
    
    public Cargo(String dataLine) {
        //This is the Constructor        
        String[] token = dataLine.split(",");            
        this.description = token[0];
        this.tonnage = Double.parseDouble(token[1]);        

        systemInput = new Scanner(System.in);
    }
    
    //print out the current information about the cargo
    public String display() {
        return String.format("%.2f of tons of %s\n\n", this.getTonnage(), this.getDescription());
    }

    //update cargo properties
    public void updateCargoProperty(int property) {
        String inputString;
        double inputDouble;
        systemInput = new Scanner(System.in);
        switch (property) {
            case 1:
                System.out.println("Enter cargo decription: ");
                inputString = systemInput.next();
                setDescription(inputString);
                break;
            case 2:
                System.out.println("Enter cargo weight: ");
                inputDouble = systemInput.nextDouble();
                setTonnage(inputDouble);
                break;
        }
    }

    //generate data to one string line
    @Override
    public String toString() {
        String string = "";
        string = this.getDescription() + "," + this.getTonnage();
        return string;
    }

    /**
     * @return the tonnage
     */
    public double getTonnage() {
        return tonnage;
    }

    /**
     * @param tonnage the tonnage to set
     */
    public void setTonnage(double tonnage) {
        this.tonnage = tonnage;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
