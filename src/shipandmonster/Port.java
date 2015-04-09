/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dara
 */
public class Port {

    private String name;
    private ArrayList<Dock> arrayListDock;
    private ArrayList<Cargo> arrayListCargo;
    private FileHandler fileHandler;

    //Constructor
    public Port() {
        this.name = "Liverpool";
        arrayListDock = new ArrayList<Dock>();
        arrayListCargo = new ArrayList<Cargo>();
        fileHandler = new FileHandler();
        
        try {
            fileHandler.readPortFile("complex.port.txt", this.name, arrayListDock, arrayListCargo);
        } catch (FileNotFoundException ex) {
            System.out.println("the simple.port.txt file is missing!");
        }
    }

    //display Dock List
    public void displayDockList() {
        int indexDock;
        if (arrayListDock.size() == 0) {
            System.out.println("There is no dock at the port.\n");
        } else {
            for (indexDock = 0; indexDock < arrayListDock.size(); indexDock++) {
                System.out.printf("%d. %s\n", indexDock, arrayListDock.get(indexDock).getName());
            }
            System.out.printf("%d. Cancel\n", indexDock);
            System.out.println("===========================");
            System.out.print("::>");
        }
    }
    
    //display all Ship data
    public void displayDocks() {
        if (arrayListDock.size() == 0) {
            System.out.println("There is no Dock left in the map.");
        } else {
            for (Dock dock : getArrayListDock()) {
                System.out.println("------------------------------------------------");
                dock.display();
            }
            System.out.println();
        }
    }

    //display Cargo List
    public void displayCargoList() {
        if (arrayListCargo.size() == 0) {
            System.out.println("There is no cargo at the port.\n");
        } else {
            System.out.println("Cargo at Port\n"
                    + "----------------------------------------");
            for (int indexCargo = 0; indexCargo < arrayListCargo.size(); indexCargo++) {
                System.out.print(indexCargo + ". ");
                arrayListCargo.get(indexCargo).display();
            }
        }
    }

    //update Dock Property
    public void updateDockProperty(int index, int dockProperty) {
        Dock dock = arrayListDock.get(index);
        dock.updateDockProperty(dockProperty);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the arrayListDock
     */
    public ArrayList<Dock> getArrayListDock() {
        return arrayListDock;
    }

    /**
     * @param arrayListDock the arrayListDock to set
     */
    public void setArrayListDock(ArrayList<Dock> arrayListDock) {
        this.arrayListDock = arrayListDock;
    }

    /**
     * @return the arrayListCargo
     */
    public ArrayList<Cargo> getArrayListCargo() {
        return arrayListCargo;
    }

    /**
     * @param arrayListCargo the arrayListCargo to set
     */
    public void setArrayListCargo(ArrayList<Cargo> arrayListCargo) {
        this.arrayListCargo = arrayListCargo;
    }

    //generate data to one string line
    @Override
    public String toString() {
        String string = "";
        string = this.name + "," + this.getArrayListDock().size() + "," + this.getArrayListCargo().size();
        return string;
    }

}
