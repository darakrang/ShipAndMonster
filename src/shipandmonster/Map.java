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
    
    public Map(String filename)
    {
        systemInput = new Scanner(System.in);
        arrayListShip = new ArrayList<CargoShip>();
        arrayTypeShip = new ArrayList<CargoShip>();
        fileHandler = new FileHandler();
        mapSymbol = new char[36][54];
        try {
            mapSymbol = fileHandler.readMapFile(filename + ".map.txt");
        } catch (FileNotFoundException ex) {
            System.out.println("the " + filename + " fileset is missing!");
        }
    }
    
    public Map(String filename, boolean readInPort)
    {
        systemInput = new Scanner(System.in);
        arrayListShip = new ArrayList<CargoShip>();
        arrayTypeShip = new ArrayList<CargoShip>();
        fileHandler = new FileHandler();
        mapSymbol = new char[36][54];
        try {
            mapSymbol = fileHandler.readMapFile(filename + ".map.txt");
            if(readInPort)
            {
                port = new Port();
                fileHandler.readPortFile(filename, port);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("the " + filename + " fileset is missing!");
        }
    }

    //gererate ships and add it to map object
    public void generateShip(int shipNumber, ArrayList<Dock> arrayListDock) {
//        int shipNumber = 0;
        double longitude;
        double latitude;
        int col, row, colDock, rowDock, counter;
        char symbol;
        ArrayList<Dock> arrayListTargetDock = new ArrayList<Dock>();
        ArrayList<Dock> arrayListTargetPier = new ArrayList<Dock>();
        ArrayList<Dock> arrayListTargetCrane = new ArrayList<Dock>();
        ArrayList<Dock> tempArrayListTargetDock = new ArrayList<Dock>();
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
             //find the all right target dock
            if (!(arrayListTargetDock.isEmpty())) {
                arrayListTargetDock.clear();
            }
            for (Dock dock : arrayListDock) {
                if (ship instanceof OilTanker && dock instanceof Pier) {
                    arrayListTargetDock.add(dock);
                } else if (ship instanceof ContainerShip && dock instanceof Crane) {
                    arrayListTargetDock.add(dock);
                } else if (!(ship instanceof OilTanker)
                        && !(ship instanceof ContainerShip)
                        && !(dock instanceof Pier)
                        && !(dock instanceof Crane)) {
                    {
                        arrayListTargetDock.add(dock);
                    }
                }
            }

            while (flag) {
                col = Main.randomIntInRange(0, 54);
                row = Main.randomIntInRange(0, 36);
                longitude = MapConverter.col2lon(col);
                latitude = MapConverter.row2lat(row);

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

                //find the nearest dock
                //Set target Dock
//                if (ship instanceof OilTanker) {
//                    tempArrayListTargetDock = arrayListTargetPier;
//                } else if (ship instanceof ContainerShip) {
//                    tempArrayListTargetDock = arrayListTargetCrane;
//                } else if (!(ship instanceof OilTanker)
//                        && !(ship instanceof ContainerShip)) {
//                    tempArrayListTargetDock = arrayListTargetDock;
//                }
                for (Dock dock2 : arrayListTargetDock) {
                    int distanceRow, distanceCol, totalDistance;
                    int targetRow, targetCol, totalDistanceTarget;
                    colDock = MapConverter.lon2col(dock2.getLongitude());
                    rowDock = MapConverter.lat2row(dock2.getLatitude());
                    distanceCol = Math.abs(colDock - col);
                    distanceRow = Math.abs(rowDock - row);
                    totalDistance = distanceCol + distanceRow;

                    if (ship.getTargetDock() == null) {
                        ship.setTargetDock(dock2);
                        dock2.setTargeted(true);
                    } else //if (ship.getTargetDock() != null && !dock2.isTargeted()) 
                    {
                        targetCol = Math.abs(MapConverter.lon2col(ship.getTargetDock().getLongitude()) - col);
                        targetRow = Math.abs(MapConverter.lat2row(ship.getTargetDock().getLatitude()) - row);
                        totalDistanceTarget = targetCol + targetRow;
                        if (totalDistanceTarget >= totalDistance) {
                            ship.getTargetDock().setTargeted(false);
                            ship.setTargetDock(dock2);
                            dock2.setTargeted(true);
                        }
                    }
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
