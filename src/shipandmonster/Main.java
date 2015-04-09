/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dara
 */
//Class HelperFunction
//
// This class store all the functions that display all the menues in the program, 
// update ship, cargo and dock information
//
public class Main {

    //This is the Class Space
    private Scanner systemInput;
    private CargoShip shipProperty;
    private Cargo cargoProperty;
    private Dock dockProperty;
    private Map map;
    private Port port;
    private FileHandler fileHandler;

    //Create the object
    public Main() {
        //This is the Constructor
        shipProperty = new CargoShip();
        cargoProperty = new Cargo();
        dockProperty = new Dock();
        map = new Map();
        port = new Port();

        //allocate objects in memory
        fileHandler = new FileHandler();
        fileHandler.setMap(map);
        fileHandler.setPort(port);
        map.setPort(port);

        systemInput = new Scanner(System.in);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main helper = new Main();
        helper.MainMenu();
    }

    // display main menu of the program
    public void MainMenu() {
        boolean flag = true;
        int answer = 0;
        while (flag) {
            System.out.printf("Main Menu\n"
                    + "-------------\n"
                    + "1. Show Student ID\n"
                    + "2. Load System\n"
                    + "3. Ship Menu\n"
                    + "4. Port Menu\n"
                    + "5. Show Map\n"
                    + "6. Display Report\n\n"
                    + "8. Quit\n"
                    + "::>");
            answer = systemInput.nextInt();
            switch (answer) {
                case 1:
                    System.out.printf("Name: Dara Krang\n"
                            + "ID: 1000910872\n"
                            + "CSE 1325-002\n"
                            + "March 20, 2015\n");
                    break;
                case 2:
                    loadMap();
                    break;
                case 3:
                    try {
                        ShipMenu();
                    } catch (FileNotFoundException ex) {
                        System.out.println("The complex.map.txt file is missing!");
                    }
                    break;
                case 4:
                    PortMenu();
                    break;
                case 5:
                    try {
                        showMap();
                    } catch (FileNotFoundException ex) {
                        System.out.println("The complex.map.txt file is missing!");
                    }
                    break;
                case 6:
                    try {
                        displayReport();
                    } catch (FileNotFoundException ex) {
                        System.out.println("The complex.map.txt file is missing!");
                    }
                    break;
                case 8:
                    flag = false;
                    break;
            }
        }
    }

    //display report
    private void displayReport() throws FileNotFoundException {
        int row = 0, col = 0, counter = 1;
        refreshMapData();

        System.out.println("Ship report");
        System.out.println("-----------------------------------");
        for (CargoShip ship : map.getArrayListShip()) {
            row = MapConverter.lat2row(ship.getLatitude());
            col = MapConverter.lon2col(ship.getLongitude());
            if (map.getMapSymbol()[row][col] == 'X') {
                System.out.printf("%d. %s: ", counter, ship.getName());
                System.out.println("unsafe");
            } else if (map.getMapSymbol()[row][col] == '$') {
                System.out.printf("%d. %s: ", counter, ship.getName());
                System.out.println("safe at dock");
            } else {
                System.out.printf("%d. %s: ", counter, ship.getName());
                System.out.println("safe at sea");
            }
            counter++;
        }
        counter = 1;
        System.out.println();
        System.out.println("Dock report");
        System.out.println("-----------------------------------");
        for (Dock dock : port.getArrayListDock()) {
            System.out.printf("%d. %s\n", counter, dock.getName());
            counter++;
        }
        
        counter = 1;
        System.out.println();
        System.out.println("Cargo at port report");
        System.out.println("-----------------------------------");
        for (Cargo cargo : port.getArrayListCargo()) {
            System.out.printf("%d. ", counter);
            cargo.display();
            counter++;
        }
        System.out.println();
    }

    //show Map
    public void showMap() throws FileNotFoundException {
        int row = 0, col = 0;
        // combine ship, dock and map data
        refreshMapData();

        //print map
        for (row = 0; row < 36; row++) {
            for (col = 0; col < 54; col++) {
                System.out.print(map.getMapSymbol()[row][col]);
            }
            System.out.println();
        }

    }

    // display ship menu
    public void ShipMenu() throws FileNotFoundException {
        boolean flag = true;
        int answer = 0;
        while (flag) {
            System.out.printf("Ship Menu\n"
                    + "--------------------\n"
                    + "1. Generate Ships\n"
                    + "2. Update Ship\n"
                    + "3. Display Ships\n"
                    + "4. Remove all Ships\n"
                    + "5. Previous Menu\n"
                    + "====================\n");
            answer = systemInput.nextInt();
            switch (answer) {
                case 1:
                    map.generateShip();
                    break;
                case 2:
                    ShipPropertiesMenu();
                    break;
                case 3:
                    map.displayShips();
                    break;
                case 4:
                    map.getArrayListShip().clear();
                    map.setMapSymbol(fileHandler.readMapFile("complex.map.txt"));
                    break;
                case 5:
                    flag = false;
                    break;
            }
        }
    }

    // display port menu
    public void PortMenu() {
        boolean flag = true;
        int answer = 0;
        while (flag) {
            System.out.printf("Port Menu\n"
                    + "-----------------------\n"
                    + "1. Update Dock\n"
                    + "2. Unload Ship\n"
                    + "3. Display all Cargos\n"
                    + "4. Display all Docks\n"
                    + "5. Return to previous menu\n");
            answer = systemInput.nextInt();
            switch (answer) {
                case 1:
                    DockPropertiesMenu();
                    break;
                case 2:
                    unloadShip();
                    break;
                case 3:
                    port.displayCargoList();
                    break;
                case 4:
                    port.displayDocks();
                    break;
                case 5:
                    flag = false;
                    break;
            }
        }
    }

    //list all available ships and choose one to unload
    public void unloadShip() {
        int index = 0, startIndex = 0;
        CargoShip unloadShip;
        Dock unloadDock;
        ArrayList<CargoShip> arrayListUnloadShip = new ArrayList<CargoShip>();
        ArrayList<Dock> arrayListUnloadDock = new ArrayList<Dock>();

        System.out.println("Please choose a safety ship to unload:");
        for (Dock dock : port.getArrayListDock()) {
            if (dock.getSymbol() == '$') {
                continue;
            }
            for (index = startIndex; index < map.getArrayListShip().size(); index++) {
                unloadShip = map.getArrayListShip().get(index);
                if (unloadShip.getCargo() != null
                        && unloadShip.getLatitude() == dock.getLatitude()
                        && unloadShip.getLongitude() == dock.getLongitude()
                        && !arrayListUnloadShip.contains(unloadShip)) {
                    arrayListUnloadDock.add(dock);
                    arrayListUnloadShip.add(unloadShip);

                    startIndex = index + 1;
                    break;
                }
            }
        }

        index = 0;
        for (CargoShip ship : arrayListUnloadShip) {
            System.out.printf("%d. %s\n", index++, ship.getName());
        }

        System.out.printf(
                "%d. Cancel\n", index);
        System.out.println(
                "===========================");
        startIndex = systemInput.nextInt();
        if (startIndex == index) {
            return;
        }

        //update data of map, port and ship
        unloadShip = arrayListUnloadShip.get(startIndex);
        unloadDock = arrayListUnloadDock.get(startIndex);

        unloadDock.setSymbol(
                '$');

        unloadShip.setLatitude(unloadDock.getLatitude());
        unloadShip.setLongitude(unloadDock.getLongitude());

        port.getArrayListCargo()
                .add(unloadShip.getCargo());
        unloadShip.setCargo(
                null);
    }

    //Chec: is ship safe to unload?
    private boolean isShipSafeAtDock(CargoShip ship, Dock dock) {
        CargoShip checkingShip = ship;
        Dock checkingDock = dock;
        if (checkingShip.getCargo() != null
                && checkingShip.getCargoCapacity() < checkingShip.getCargo().getTonnage()) {
            return false;
        } else if (checkingShip.getDraft() > checkingDock.getDepth()) {
            return false;
        } else if (checkingShip.getDraft() <= checkingDock.getDepth()) {
            if (checkingShip.getBeam() > checkingDock.getWidth()) {
                return false;
            } else if (checkingShip.getLength() > checkingDock.getLength()) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    // display ship property menu and choose one to update
    public void ShipPropertiesMenu() {
        boolean flag = true, flag2 = true;
        int answer = 0;
        int shipIndex = 0;

        System.out.println("Please choose a ship to update:");
        map.displayShipList();
        if (map.getArrayListShip().size() > 0) {
            shipIndex = systemInput.nextInt();
        } else {
            flag = false;
        }

        while (flag) {
            System.out.printf("Ship Properties Menu\n"
                    + "--------------------\n"
                    + "1. Update Name\n"
                    + "2. Update Registration\n"
                    + "3. Update Transponder\n"
                    + "4. Update Capacity\n"
                    + "5. Update Length\n"
                    + "6. Update Beam\n"
                    + "7. Update Draft\n"
                    + "8. Update Longitude and Latitude\n"
                    + "9. Update Row and Column\n"
                    + "10. Update Cargo\n"
                    + "11. Display the Ship\n"
                    + "12. Previous Menu\n");
            answer = systemInput.nextInt();
            if (answer == 12) {
                flag = false;
            } else if (answer >= 1 && answer <= 9) {
                while (flag2) {
                    try {
                        map.updateShipProperty(shipIndex, answer);
                        break;
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid Data! Please try again.");
                        flag2 = true;
                    }

                }
            } 
            else if (answer == 11) { // display the ship
                map.getArrayListShip().get(shipIndex).display();
            }

        }
    }

    // display cargo property menu
    public void CargoPropertiesMenu(OilTanker ship) {
        boolean flag = true, flag2 = true;
        int answer = 0;
        while (flag) {
            System.out.printf("Cargo Properties Menu\n"
                    + "---------------------\n"
                    + "1. Update Description\n"
                    + "2. Update Tonnage\n"
                    + "3. Display Cargo\n"
                    + "4. Previous Menu\n");
            answer = systemInput.nextInt();
            if (answer == 4) {
                flag = false;
            } else if (answer >= 1 && answer <= 2) {
                while (flag2) {
                    try {
                        ship.getCargo().updateCargoProperty(answer);
                        break;
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid Data! Please try again.");
                        flag2 = true;
                    }
                }
            } else if (answer == 3) {
                ship.getCargo().display();
            }
        }
    }

    //update cargo properties
    public void updateCargoProperty(int property) {
        String inputString;
        double inputDouble;
        switch (property) {
            case 1:
                System.out.println("Enter cargo decription: ");
                inputString = systemInput.next();
                cargoProperty.setDescription(inputString);
                break;
            case 2:
                System.out.println("Enter cargo weight: ");
                inputDouble = systemInput.nextDouble();
                cargoProperty.setTonnage(inputDouble);
                break;
        }
    }

    // display dock property menu
    public void DockPropertiesMenu() {
        boolean flag = true, flag2 = true;
        int answer = 0;

        int dockIndex = 0;

        System.out.println("Please choose a dock to update:");
        port.displayDockList();
        if (port.getArrayListDock().size() > 0) {
            dockIndex = systemInput.nextInt();
        } else {
            flag = false;
        }

        while (flag) {
            System.out.printf("Dock Properties Menu\n"
                    + "--------------------\n"
                    + "1. Set the name\n"
                    + "2. Set the section\n"
                    + "3. Set the number\n"
                    + "4. Set the length\n"
                    + "5. Set the width\n"
                    + "6. Set the depth\n"
                    + "7. Set longitude and latitude\n"
                    + "8. Display the Dock\n"
                    + "9. Previous Menu\n");
            answer = systemInput.nextInt();

            if (answer == 9) {
                flag = false;
            } else if (answer >= 1 && answer <= 7) {
                while (flag2) {
                    try {
                        port.updateDockProperty(dockIndex, answer);
                        break;
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid Data! Please try again.");
                        flag2 = true;
                    }
                }

            } else if (answer == 8) {
                port.getArrayListDock().get(dockIndex).display();
            }
        }
    }

    //read map, ship and port file and store data in memory
    public void loadMap() {
        int row = 0, col = 0;
        boolean flag = true;

        String inputString = "";
        while (flag) {
            try {
                System.out.println("Please enter a tag for your load files, for example \'complex' : ");
                inputString = systemInput.next();
                map.setMapSymbol(fileHandler.readMapFile(inputString + ".map.txt"));
                fileHandler.readPortFile(inputString + ".port.txt", port);
                break;
            } catch (FileNotFoundException ex) {
                System.out.printf("Files, \'%s.map.txt\' and \'%s.port.txt\' did not found!\n", inputString, inputString);
                flag = true;
                systemInput = new Scanner(System.in);
            }
        }
    }

    // combine dock, ship and map data
    private void refreshMapData() throws FileNotFoundException {
        int row = 0, col = 0;
        int index = 0, startIndex = 0;
        CargoShip checkingShip = new CargoShip();

        //load map
        map.setMapSymbol(fileHandler.readMapFile("complex.map.txt"));

        // combine dock and map
        for (Dock dock : port.getArrayListDock()) {
            row = MapConverter.lat2row(dock.getLatitude());
            col = MapConverter.lon2col(dock.getLongitude());
            if (dock instanceof Crane) {
                map.getMapSymbol()[row][col] = 'C';
            } else if (dock instanceof Pier) {
                map.getMapSymbol()[row][col] = 'P';
            } else {
                map.getMapSymbol()[row][col] = 'D';
            }
        }

        // combine not unload ship and map
        for (CargoShip ship : map.getArrayListShip()) {
            if (ship.getCargo() == null) {
                continue;
            }
            row = MapConverter.lat2row(ship.getLatitude());
            col = MapConverter.lon2col(ship.getLongitude());
            if (map.getMapSymbol()[row][col] == '$'
                    || map.getMapSymbol()[row][col] == 'S'
                    || map.getMapSymbol()[row][col] == 'T'
                    || map.getMapSymbol()[row][col] == 'B') {
                map.getMapSymbol()[row][col] = 'X';
            } else if (map.getMapSymbol()[row][col] == 'D'
                    || map.getMapSymbol()[row][col] == 'P'
                    || map.getMapSymbol()[row][col] == 'C') {
                map.getMapSymbol()[row][col] = '$';
            } else {
                if (ship instanceof ContainerShip) {
                    map.getMapSymbol()[row][col] = 'B';
                } else if (ship instanceof OilTanker) {
                    map.getMapSymbol()[row][col] = 'T';
                } else {
                    map.getMapSymbol()[row][col] = 'S';
                }
            }
        }

        //The ship is at the same coordinates as a dock, and it does not fit in the dock
        for (Dock dock : port.getArrayListDock()) {
            for (index = startIndex; index < map.getArrayListShip().size(); index++) {
                checkingShip = map.getArrayListShip().get(index);
                //check ship is safe at the dock
                if (checkingShip.getLatitude() == dock.getLatitude()
                        && checkingShip.getLongitude() == dock.getLongitude()
                        && !isShipSafeAtDock(checkingShip, dock)) {
                    //mark X at the map
                    row = MapConverter.lat2row(checkingShip.getLatitude());
                    col = MapConverter.lon2col(checkingShip.getLongitude());
                    map.getMapSymbol()[row][col] = 'X';
                    startIndex = index + 1;
                    break;
                }
            }
        }

        //The unloaded ship is at a dock of the wrong type
        for (Dock dock : port.getArrayListDock()) {
            for (index = startIndex; index < map.getArrayListShip().size(); index++) {
                checkingShip = map.getArrayListShip().get(index);
                //check ship is safe at the dock
                if (checkingShip.getLatitude() == dock.getLatitude()
                        && checkingShip.getLongitude() == dock.getLongitude()
                        && checkingShip.getCargo() == null
                        && !isRightTypeToUnload(checkingShip, dock)) {
                    //mark X at the map
                    row = MapConverter.lat2row(checkingShip.getLatitude());
                    col = MapConverter.lon2col(checkingShip.getLongitude());
                    map.getMapSymbol()[row][col] = 'X';
                    startIndex = index + 1;
                    break;
                }
            }
        }
    }

    private boolean isRightTypeToUnload(CargoShip ship, Dock dock) {
        if (ship instanceof OilTanker && dock instanceof Pier) {
            return true;
        } else if (ship instanceof ContainerShip && dock instanceof Crane) {
            return true;
        } else if (ship instanceof CargoShip
                && !(ship instanceof OilTanker)
                && !(ship instanceof ContainerShip)
                && dock instanceof Dock
                && !(dock instanceof Pier)
                && !(dock instanceof Crane)) {
            return true;
        } else {
            return false;
        }
    }

    // generate random double number in range
    public static double randomDoubleInRange(double min, double max) {
        DecimalFormat decimalFormat = new DecimalFormat("#.######");

        Random random = new Random();
        double randomValue = Double.parseDouble(decimalFormat.format(min + (random.nextDouble() * (max - min))));
        return randomValue;
    }

    // generate random long number in range
    public static long randomLongInRange(long min, long max) {
        Random random = new Random();
        long randomValue = min + (long) (random.nextDouble() * (max - min));
        return randomValue;
    }
}
