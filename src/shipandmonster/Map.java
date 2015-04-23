/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Dara
 */
public class Map {

    private char[][] mapSymbol;
    protected ArrayList<CargoShip> arrayListShip;
    private ArrayList<CargoShip> arrayTypeShip;
    private Port port;

    private CargoShip ship;
    private Scanner systemInput;
    private FileHandler fileHandler;

    private final static char water = '.';
    private final static char land = '*';
    private final static char cargoShipSafeAtSea = 'S';
    private final static char containerShipSafeAtSea = 'B';
    private final static char tankerSafeAtSea = 'T';
    private final static char shipReadyToUnload = '$';
    private final static char shipInDanger = 'X';
    private final static char emptyDock = 'D';
    private final static char emptyPier = 'P';
    private final static char emptyCrane = 'C';

    //Constructor
    public Map() {
        systemInput = new Scanner(System.in);
        arrayListShip = new ArrayList<CargoShip>();
        arrayTypeShip = new ArrayList<CargoShip>();
        fileHandler = new FileHandler();
        mapSymbol = new char[36][54];
        try {
            mapSymbol = fileHandler.readMapFile("complex.map.txt");
        } catch (FileNotFoundException ex) {
            System.out.println("the complex.map.txt file is missing!");
        }
    }

    //gererate ships and add it to map object
    public void generateShip(int shipNumber) {
//        int shipNumber = 0;
        double longitude;
        double latitude;
        int col, row, counter;
        char symbol;
        boolean flag = true;
        Random random = new Random();

        arrayTypeShip.add(new CargoShip());
        arrayTypeShip.add(new ContainerShip());
        arrayTypeShip.add(new OilTanker());
        for (counter = 0; counter < shipNumber; counter++) {
            //generate location of the ship
            flag = true;

            //random ship object
            int indexShip = random.nextInt(3);
            if (arrayTypeShip.get(indexShip) instanceof OilTanker) {
                ship = new OilTanker();
            } else if (arrayTypeShip.get(indexShip) instanceof ContainerShip) {
                ship = new ContainerShip();
            } else {
                ship = new CargoShip();
            }
            while (flag) {
                longitude = Main.randomDoubleInRange(-3.035000, -2.988478);
                latitude = Main.randomDoubleInRange(53.396700, 53.457561);
                col = MapConverter.lon2col(longitude);
                row = MapConverter.lat2row(latitude);

                // check: location of ship is out of map
                if (row > 35 || col > 53) {
                    continue;
                }
                symbol = mapSymbol[row][col];
                switch (symbol) {
                    case water:
                    case emptyDock:
                        ship.setLongitude(longitude);
                        ship.setLatitude(latitude);                        
                        flag = false;
                        break;
                    default:
                        flag = true;
                        break;
                }
            }

            // generate ship name        
            int indexFirstName = random.nextInt(10);
            int indexLastName = random.nextInt(10);
            String firstNames[] = {"Red", "Green", "Dark", "Light", "Day", "Night", "Savanah", "Mountain", "Captain’s", "Admiral’s"};
            String lastNames[] = {"Buffalo", "Pastures", "Knight", "Wave", "Star", "Moon", "Lion", "Goat", "Pride", "Joy"};
            String firstName = firstNames[indexFirstName];
            String lastName = lastNames[indexLastName];
            ship.setName(firstName + " " + lastName);

            //generate Transponder
            long transponder = Main.randomLongInRange(1000000, 9999999);
            ship.setTransponder(transponder);

            //add to array list of map
            getArrayListShip().add(ship);
        }
    }

    //display Ship List
    public void displayShipList() {
        int indexShip;
        if (arrayListShip.isEmpty()) {
            System.out.println("There is no ship left in the map.\n");
        } else {
            for (indexShip = 0; indexShip < arrayListShip.size(); indexShip++) {
                System.out.printf("%d. %s\n", indexShip, arrayListShip.get(indexShip).getName());
            }
            System.out.printf("%d. Cancel\n", indexShip);
            System.out.println("===========================");
            System.out.print("::>");
        }
    }

    //display all Ship data
    public String displayShips() {
        String result = "";
        if (arrayListShip.isEmpty()) {
            result += "There is no ship left in the map.\n";
        } else {
            for (CargoShip ship : getArrayListShip()) {
                result += "------------------------------------------------\n";
                result += ship.display();
            }
            result += "\n";
        }
        return result;
    }

    
    
    //update Ship Property
    public void updateShipProperty(int index, int shipProperty) {
        CargoShip ship = arrayListShip.get(index);
        ship.updateShipProperty(shipProperty);
    }
//    
    /**
     * @return the mapSymbol
     */
    public char[][] getMapSymbol() {
        return mapSymbol;
    }

    /**
     * @param mapSymbol the mapSymbol to set
     */
    public void setMapSymbol(char[][] mapSymbol) {
        this.mapSymbol = mapSymbol;
    }
    /**
     * @return the port
     */
    public Port getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Port port) {
        this.port = port;
    }

    /**
     * @return the arrayListShip
     */
    public ArrayList<CargoShip> getArrayListShip() {
        return arrayListShip;
    }

    /**
     * @param arrayListShip the arrayListShip to set
     */
    public void setArrayListShip(ArrayList<CargoShip> arrayListShip) {
        this.arrayListShip = arrayListShip;
    }
}
