/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Dara Krang, Hunter Reynolds
 */
//Class HelperFunction
//
// This class store all the functions that display all the menues in the program, 
// update ship, cargo and dock information
//
public class Main extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    //This is the Class Space
    private Scanner systemInput;
    private CargoShip shipProperty;
    private Cargo cargoProperty;
    private Dock dockProperty;
    private Map map;
    private Port port;
    private FileHandler fileHandler;

    //here are the GUI components [Hunter]
    private JLabel stLabel, mtLabel;
    private JTextArea statusTerminal, mouseTerminal;
    private JPanel stPanel;
    private JScrollPane stScrollPane;
    private JButton button1, button2, button3;

    private MapTile[][] bufferMap;

    //private JLabel gridLabel;

    private JMenuBar menuBar;

    private JMenu menuFile;
    private JMenuItem mOpen;
    private JMenuItem mClose;
    private JMenuItem mSnapshot;
    private JMenuItem mExit;

    private JMenu menuShip;
    private JMenuItem mGenerateShips;
    private JMenuItem mUpdateShips;
    private JMenuItem mDisplayShips;
    private JMenuItem mRemoveShips;

    private JMenu menuPort;
    private JMenuItem mUnloadShip;
    private JMenuItem mUpdateDocks;
    private JMenuItem mDisplayDocks;
    private JMenuItem mDisplayCargos;

    private JMenu menuSeaMonster;
    private JMenuItem mGenerateMonsters;
    private JMenuItem mUpdateMonsters;
    private JMenuItem mDisplayMonsters;
    private JMenuItem mRemoveMonsters;
    private JMenuItem mSummonGodzilla;

    private JMenuItem mAbout;
    
    private ArrayList<CargoShip> arrayListUnloadShip = new ArrayList<CargoShip>();
    private ArrayList<Dock> arrayListUnloadDock = new ArrayList<Dock>();
    private Port unloadPort = new Port();

    //Create the object
    public Main() {
        //start initializing the GUI
        super("Monstrous Shipping Simulator");
        
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

        //initialize the GUI [Hunter]
        initializeGUI();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main helper = new Main();
    }

    /**
     * Handles the menu system actions
     *
     * @param e The event triggered by a menu item
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JMenuItem || e.getSource() instanceof JButton)
        {
            String command = e.getActionCommand();

            switch (command) {
                case MenuLibrary.commandExit:
                    System.exit(0);
                    break;
                case MenuLibrary.labelAbout:
                    JOptionPane.showMessageDialog(this, MenuLibrary.commandAbout);
                    break;
                case MenuLibrary.commandGenerateShips:
                    String input = JOptionPane.showInputDialog(null, "Enter Number of Ship:", "Generate Ships", JOptionPane.INFORMATION_MESSAGE);
                    map.generateShip(Integer.valueOf(input));
                    break;
                case MenuLibrary.commandUpdateShips:
                    UpdateShipListForm frmShip = new UpdateShipListForm();
                    frmShip.ShowDialog(map.getArrayListShip());
                    break;
                case MenuLibrary.commandDisplayShips:
                    displayShipsInForm();
                    System.out.println(map.displayShips());
                    break;
                case MenuLibrary.commandUnloadShip:
                    UnloadShipForm frmUnload = new UnloadShipForm();
                    getArrayListUnloadShipsAndDock(arrayListUnloadDock, arrayListUnloadShip);                
                    frmUnload.ShowDiaglog(arrayListUnloadDock,arrayListUnloadShip,port.getArrayListCargo());
                    statusTerminal.append(map.displayShips());
                    break;
                case MenuLibrary.commandUpdateDock:
                    UpdateDockListForm frmDock = new UpdateDockListForm();
                    frmDock.ShowDialog(port.getArrayListDock());
                    break;
                case MenuLibrary.commandDisplayDocks:
                    displayDocksInForm();
                    port.displayDocks();
                    break;
                case MenuLibrary.commandDisplayCargos:
                    displayCargosInForm();
                    port.displayCargoList();
                    break;
                //JButtons
                case MenuLibrary.commandStart:
                    break;
                case MenuLibrary.commandStop:
                    break;
                case MenuLibrary.command3D:
                    break;
            }
        }
        
        this.repaint();
    }

    public void getArrayListUnloadShipsAndDock(ArrayList<Dock> arrayListDock, ArrayList<CargoShip> arrayListShip) {
        int index = 0, startIndex = 0;
        CargoShip unloadShip;
        
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
                    arrayListDock.add(dock);
                    arrayListShip.add(unloadShip);

                    startIndex = index + 1;
                    break;
                }
            }
        }
    }
    
    public void displayShipsInForm() {
        if (map.getArrayListShip().isEmpty()) {            
            statusTerminal.append("There is no ship left in the map.\n");
        } else {
            for (CargoShip ship : map.getArrayListShip()) {
                statusTerminal.append(ship.displayShip());
            }
            System.out.println();
            System.out.println();
        }
    }
    
    public void displayDocksInForm() {
        if (port.getArrayListDock().isEmpty()) {            
            statusTerminal.append("There is no dock left in the port.\n");
        } else {
            for (Dock dock : port.getArrayListDock()) {
//                statusTerminal.append("------------------------------------------------\n");
                statusTerminal.append(dock.displayDock());
            }
            System.out.println();
        }
    }
    
    public void displayCargosInForm() {
        if (port.getArrayListCargo().isEmpty()) {            
            statusTerminal.append("There is no cargo left in the port.\n");
        } else {
            for (Cargo cargo : port.getArrayListCargo()) {
//                statusTerminal.append("------------------------------------------------\n");
                statusTerminal.append(cargo.display());
            }
            System.out.println();
        }
    }
    
    //Mouse Listener Methods
    /**
     * Not used in this particular program
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    /**
     * Records the current pixel location of the cursor (used for implementing
     * drag & drop)
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.repaint();
    }

    /**
     * Relocates the selected ship/monster/etc onto the nearest tile from where
     * it was dragged to and dropped off at.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    /**
     * Changes the selected object if the left mouse button is not compressed
     * (i.e. nothing is being dragged)
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e)
    {
        if(e.getSource() instanceof MapTile)
        {
            MapTile tempTile = (MapTile)e.getSource();
            mouseTerminal.setText(String.format("Column: %d\nRow: %d\n%s", tempTile.getPosition().column, tempTile.getPosition().row, tempTile.getDescription()));
        }
    }

    /**
     * Not used in this particular program
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e)
    {
         //do nothing
    }

    /**
     * Updates and modifies the position of any object currently being dragged,
     * based on the mouse's current position and it's initial position (when the
     * left mouse button was first pressed down)
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e)
    {
        
    }

    /**
     * Not used in this particular program
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e)
    {
        //do nothing
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

        //clear the terminal to make room for report
        statusTerminal.append("Display Report\n-----------------------------------\n");
        for (CargoShip ship : map.getArrayListShip()) {
            row = MapConverter.lat2row(ship.getLatitude());
            col = MapConverter.lon2col(ship.getLongitude());
            if (map.getMapSymbol()[row][col] == 'X') {
                statusTerminal.append(String.format("%d. %s: ", counter, ship.getName()));
                statusTerminal.append("unsafe\n");
            } else if (map.getMapSymbol()[row][col] == '$') {
                statusTerminal.append(String.format("%d. %s: ", counter, ship.getName()));
                statusTerminal.append("safe at dock\n");
            } else {
                statusTerminal.append(String.format("%d. %s: ", counter, ship.getName()));
                statusTerminal.append("safe at sea");
            }
            counter++;
        }
        counter = 1;
        statusTerminal.append("\nDock report\n-----------------------------------\n");
        for (Dock dock : port.getArrayListDock()) {
            statusTerminal.append(String.format("%d. %s\n", counter, dock.getName()));
            counter++;
        }

        counter = 1;
        statusTerminal.append("\nCargo at port report\n-----------------------------------\n");
        for (Cargo cargo : port.getArrayListCargo()) {
            statusTerminal.append(String.format("%d. ", counter));
            statusTerminal.append(cargo.display());
            counter++;
        }
        System.out.println();
    }

    //show Map
    public void showMap() throws FileNotFoundException {
        
        this.repaint();

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
//                    map.generateShip();
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

        unloadDock.setSymbol('$');

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
            } else if (answer == 11) { // display the ship
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
        
        //update images
        for (row = 0; row < 36; row++) {
            for (col = 0; col < 54; col++) {
                bufferMap[col][row].setIcon(symbolToIcon(map.getMapSymbol()[row][col]));
                bufferMap[col][row].setSymbol(map.getMapSymbol()[row][col]);
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

    public void initializeGUI() {
        //menu system set-up
        menuBar = new JMenuBar();

        //file menu
        menuFile = new JMenu(MenuLibrary.labelFile);
        mOpen = new JMenuItem(MenuLibrary.commandOpen);
        mOpen.addActionListener(this);
        mClose = new JMenuItem(MenuLibrary.commandClose);
        mClose.addActionListener(this);
        mSnapshot = new JMenuItem(MenuLibrary.commandSnapshot);
        mSnapshot.addActionListener(this);
        mExit = new JMenuItem(MenuLibrary.commandExit);
        mExit.addActionListener(this);
        menuFile.add(mOpen);
        menuFile.add(mClose);
        menuFile.add(mSnapshot);
        menuFile.add(mExit);

        menuBar.add(menuFile);

        //ship menu
        menuShip = new JMenu(MenuLibrary.labelShip);
        mGenerateShips = new JMenuItem(MenuLibrary.commandGenerateShips);
        mGenerateShips.addActionListener(this);
        mUpdateShips = new JMenuItem(MenuLibrary.commandUpdateShips);
        mUpdateShips.addActionListener(this);
        mDisplayShips = new JMenuItem(MenuLibrary.commandDisplayShips);
        mDisplayShips.addActionListener(this);
        mRemoveShips = new JMenuItem(MenuLibrary.commandRemoveShips);
        mRemoveShips.addActionListener(this);
        menuShip.add(mGenerateShips);
        menuShip.add(mUpdateShips);
        menuShip.add(mDisplayShips);
        menuShip.add(mRemoveShips);

        menuBar.add(menuShip);

        //port menu
        menuPort = new JMenu(MenuLibrary.labelPort);
        mUnloadShip = new JMenuItem(MenuLibrary.commandUnloadShip);
        mUnloadShip.addActionListener(this);
        mUpdateDocks = new JMenuItem(MenuLibrary.commandUpdateDock);
        mUpdateDocks.addActionListener(this);
        mDisplayDocks = new JMenuItem(MenuLibrary.commandDisplayDocks);
        mDisplayDocks.addActionListener(this);
        mDisplayCargos = new JMenuItem(MenuLibrary.commandDisplayCargos);
        mDisplayCargos.addActionListener(this);
        menuPort.add(mUnloadShip);
        menuPort.add(mUpdateDocks);
        menuPort.add(mDisplayDocks);
        menuPort.add(mDisplayCargos);

        menuBar.add(menuPort);

        //sea monster menu
        menuSeaMonster = new JMenu(MenuLibrary.labelSeaMonster);
        mGenerateMonsters = new JMenuItem(MenuLibrary.commandGenerateMonsters);
        mGenerateMonsters.addActionListener(this);
        mUpdateMonsters = new JMenuItem(MenuLibrary.commandUpdateMonsters);
        mUpdateMonsters.addActionListener(this);
        mDisplayMonsters = new JMenuItem(MenuLibrary.commandDisplayMonsters);
        mDisplayMonsters.addActionListener(this);
        mRemoveMonsters = new JMenuItem(MenuLibrary.commandRemoveMonsters);
        mRemoveMonsters.addActionListener(this);
        mSummonGodzilla = new JMenuItem(MenuLibrary.commandSummonGodzilla);
        mSummonGodzilla.addActionListener(this);
        menuSeaMonster.add(mGenerateMonsters);
        menuSeaMonster.add(mUpdateMonsters);
        menuSeaMonster.add(mDisplayMonsters);
        menuSeaMonster.add(mRemoveMonsters);
        menuSeaMonster.add(mSummonGodzilla);

        menuBar.add(menuSeaMonster);

        //about menu[item]
        mAbout = new JMenuItem(MenuLibrary.labelAbout);
        mAbout.addActionListener(this);

        menuBar.add(mAbout);

        //frame initialization
        this.setSize(815, 740); //this calls for 20x20 -> 15x15 icons  (map space = 1080x720 -> 810x540 for a 54x36 grid)
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocation(300, 75);

        stLabel = new JLabel("Status Terminal", JLabel.CENTER);
        stLabel.setBounds(0, 540, 510, 15);
        stLabel.setBackground(Color.white);
        stLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        stLabel.setOpaque(true);
        
        statusTerminal = new JTextArea("");
        statusTerminal.setBounds(0, 0, 512, 140);
        statusTerminal.setEditable(false);
        statusTerminal.setOpaque(true);
        statusTerminal.setBorder(BorderFactory.createLineBorder(Color.black));
        
        stScrollPane = new JScrollPane(statusTerminal);
        stScrollPane.setBounds(0, 555, 512, 140);
        
        mtLabel = new JLabel("Mouse Terminal", JLabel.CENTER);
        mtLabel.setBounds(510, 615, 150, 15);
        mtLabel.setBackground(Color.white);
        mtLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        mtLabel.setOpaque(true);
        
        mouseTerminal = new JTextArea("");
        mouseTerminal.setBounds(510, 630, 150, 60);
        mouseTerminal.setEditable(false);
        mouseTerminal.setOpaque(true);
        mouseTerminal.setBorder(BorderFactory.createLineBorder(Color.black));

        button1 = new JButton(MenuLibrary.commandStart);
        button1.setBounds(510, 540, 150, 75);

        button2 = new JButton(MenuLibrary.commandStop);
        button2.setBounds(660, 540, 150, 75);

        button3 = new JButton(MenuLibrary.command3D);
        button3.setBounds(660, 615, 150, 75);
        
        //gridLabel = new JLabel();
        //gridLabel.setBounds(0, 0, 810, 540);
        //gridLabel.setIcon(new ImageIcon(MenuLibrary.iconPath + "gridOverlay.png"));
        //IMPORTANT: file paths should be changed and verified for the demonstration!!!
        bufferMap = new MapTile[54][36];
        for(int c = 0; c < 54; c++)
        {
            for(int r = 0; r < 36; r++)
            {
                bufferMap[c][r] = new MapTile(c, r);
                bufferMap[c][r].addMouseListener(this);
                bufferMap[c][r].addMouseMotionListener(this);
                this.add(bufferMap[c][r]);
            }
        }

        this.setJMenuBar(menuBar);
        //frame.add(gridLabel);

//        JScrollPane spStatusTerminal = new JScrollPane(statusTerminal);

        this.add(stLabel);
        this.add(stScrollPane);
        this.add(mtLabel);
        this.add(mouseTerminal);
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.setVisible(true);
    }

    public ImageIcon symbolToIcon(char symbol)
    {
            if (symbol == '.')
            {
                return new ImageIcon(MenuLibrary.iconPath + "seamlessWater.jpg");
            }
            else if (symbol == '*')
            {
                return new ImageIcon(MenuLibrary.iconPath + "seamlessShore.jpg");
            }
            else if (symbol == 'D')
            {
                return new ImageIcon(MenuLibrary.iconPath + "emptyDock.jpg");
            }
            else if (symbol == 'C')
            {
                return new ImageIcon(MenuLibrary.iconPath + "emptyCrane.jpg");
            }
            else if (symbol == 'P')
            {
                return new ImageIcon(MenuLibrary.iconPath + "emptyPier.jpg");
            }
            else if (symbol == 'S')
            {
                return new ImageIcon(MenuLibrary.iconPath + "cargoShip.jpg");
            }
            else if (symbol == 'B')
            {
                return new ImageIcon(MenuLibrary.iconPath + "containerShip.jpg");
            }
            else if (symbol == 'T')
            {
                return new ImageIcon(MenuLibrary.iconPath + "oilTanker.jpg");
            }
            else if (symbol == '$')
            {
                return new ImageIcon(MenuLibrary.iconPath + "dockedShip.jpg");
            }
            else if (symbol == 'X')
            {
                return new ImageIcon(MenuLibrary.iconPath + "unsafeShip.jpg");
            }
            else if (symbol == 'G')
            {
                return new ImageIcon(MenuLibrary.iconPath + "godzilla.jpg");
            }
            else if (symbol == 'K')
            {
                return new ImageIcon(MenuLibrary.iconPath + "kraken.jpg");
            }
            else if (symbol == 'L')
            {
                return new ImageIcon(MenuLibrary.iconPath + "leviathan.jpg");
            }
            else if (symbol == 's')
            {
                //CAREFUL! Lowercase 's' is the sea serpent, upper-case 'S' is a cargo ship
                return new ImageIcon(MenuLibrary.iconPath + "seaSerpent.jpg");
            }

            //to make the compiler happy, add in a guaranteed return statement
            return null;
    }
    
    @Override
    public void paint(Graphics g)
    {
        //combine ship, dock, and map data
        //and update images
        try
        {
            refreshMapData();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        
        super.paint(g);
        
        /*print map
        for (int row = 0; row < 36; row++) {
            for (int col = 0; col < 54; col++) {
                g.drawImage(bufferMap[col][row], MenuLibrary.ICON_SIZE*col + MenuLibrary.MAP_ORIGIN_X, MenuLibrary.ICON_SIZE*row + MenuLibrary.MAP_ORIGIN_Y, this);
            }
        }*/
    }
}
