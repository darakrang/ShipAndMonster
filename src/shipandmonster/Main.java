/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.WindowConstants;

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

    // AI Members
    private boolean runningAI;

    //drag and drop members
    private MapTile selectedTile;
    private CargoShip selectedShip;
    private SeaMonster selectedMonster;
    private int startx, starty, startLayer, origx, origy;

    //here are the GUI components [Hunter]
    private JLayeredPane pane;
    private JLabel stLabel, mtLabel;
    private JTextArea statusTerminal, mouseTerminal;
    private JPanel stPanel;
    private JScrollPane stScrollPane;
    private JButton button1, button2, button3;
    private JLabel backgroundLabel;
    private boolean closed;

    //private JLabel[][] geoMap; //a background / geographical map including sea, land, and docks
    private MapTile[][] bufferMap; //the full map including ships & monsters

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
    private ArrayList<SeaMonster> arrayListMonster = new ArrayList<SeaMonster>();
    private Timer timer;

    private boolean godzillaExists;
    private Godzilla godzilla;

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
        selectedTile = null;
        godzillaExists = false;

        //Initialize AI 
        runningAI = true;
        timer = new Timer(1000, this);
        closed = false;
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
        if (e.getSource() instanceof JMenuItem || e.getSource() instanceof JButton) {
            String command = e.getActionCommand();
            Position newTargetPosition;
            switch (command) {
                case MenuLibrary.commandExit:
                    System.exit(0);
                    break;
                case MenuLibrary.labelAbout:
                    JOptionPane.showMessageDialog(this, MenuLibrary.commandAbout);
                    break;
                case MenuLibrary.commandGenerateShips:
                    String input = JOptionPane.showInputDialog(null, "Enter Number of Ship:", "Generate Ships", JOptionPane.INFORMATION_MESSAGE);
                    map.generateShip(Integer.valueOf(input), port.getArrayListDock());
                    for (CargoShip s : map.getArrayListShip()) {
                        bufferMap[s.getPosition().getColumn()][s.getPosition().getRow()].setTargetPosition(s.getTargetDock().getPosition());
                    }
                    break;
                case MenuLibrary.commandUpdateShips:
                    UpdateShipListForm frmShip = new UpdateShipListForm();
                    frmShip.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//                    frmShip.addWindowListener(new WindowAdapter() {
//                        @Override
//                        public void windowClosed(WindowEvent e) {
//                            System.out.println("jdialog window closed");
////                            try {
////                                refreshMapData();
//////                                generateTargetDockForShip(s);
//////                                newTargetPosition = new Position();
//////                                newTargetPosition.setColumn(MapConverter.lon2col(s.getTargetDock().getLongitude()));
//////                                newTargetPosition.setRow(MapConverter.lat2row(s.getTargetDock().getLatitude()));
//////                                dropTile.setTargetPosition(newTargetPosition);
////                            } catch (FileNotFoundException ex) {
////                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
////                            }
//                        }
//                    });
                    frmShip.ShowDialog(map.getArrayListShip(), port.getArrayListDock(), bufferMap);
                    break;
                case MenuLibrary.commandDisplayShips:
                    statusTerminal.setText("");//clear terminal
                    displayShipsInForm();
//                    System.out.println(map.displayShips());
                    break;
                case MenuLibrary.commandRemoveShips:
                    map.getArrayListShip().clear();
                    break;
                case MenuLibrary.commandUnloadShip:
                    UnloadShipForm frmUnload = new UnloadShipForm();
                    getArrayListUnloadShipsAndDock(arrayListUnloadDock, arrayListUnloadShip);
                    frmUnload.ShowDiaglog(arrayListUnloadDock, arrayListUnloadShip, port.getArrayListCargo());
//                    statusTerminal.append(map.displayShips());
                    break;
                case MenuLibrary.commandUpdateDock:
                    UpdateDockListForm frmDock = new UpdateDockListForm();
                    frmDock.ShowDialog(port.getArrayListDock());
                    break;
                case MenuLibrary.commandDisplayDocks:
                    statusTerminal.setText("");//clear terminal
                    displayDocksInForm();
                    port.displayDocks();
                    break;
                case MenuLibrary.commandDisplayCargos:
                    statusTerminal.setText("");//clear terminal
                    displayCargosInForm();
                    port.displayCargoList();
                    break;
                case MenuLibrary.commandGenerateMonsters:
                    String inputMonster = JOptionPane.showInputDialog(null, "Enter Number of Monster:", "Generate Monsters", JOptionPane.INFORMATION_MESSAGE);
                    generateMonster(Integer.valueOf(inputMonster));
                    break;
                case MenuLibrary.commandUpdateMonsters:
                    UpdateMonsterListForm frmMonster = new UpdateMonsterListForm();
                    frmMonster.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    frmMonster.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            System.out.println("jdialog window closed");
                            try {
                                refreshMapData();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    frmMonster.ShowDialog(arrayListMonster);
                    break;
                case MenuLibrary.commandDisplayMonsters:
                    statusTerminal.setText("");//clear terminal
                    displayMonstersInForm();
                    break;
                case MenuLibrary.commandRemoveMonsters:
                    arrayListMonster.clear();
                    godzillaExists = false;
                    break;
                case MenuLibrary.commandSummonGodzilla:
                    generateGodzilla();
                    break;
                //JButtons
                case MenuLibrary.commandStart: {
                    //only start the threading if ships exist to go to docks, or if monsters & ships exist, or if godzilla and monsters exist
                    //otherwise there is nothing to target
                    if (!map.getArrayListShip().isEmpty() || (!arrayListMonster.isEmpty() && godzillaExists)) {
                        try {
                            startAI();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        statusTerminal.setText("There are no ships or monsters on the map.");
                    }
                }
                break;
                case MenuLibrary.commandStop:
                    timer.stop();
                    break;
                case MenuLibrary.command3D:
                    break;
                case MenuLibrary.commandOpen: {
                    try {
                        loadMapInForm();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
//                case MenuLibrary.commandOpen:
//                    String filename = JOptionPane.showInputDialog(null, "Type in the filename:", "Open Fileset", JOptionPane.INFORMATION_MESSAGE);
//                    char readInPort = ' ';
//                    do {
//                        readInPort = JOptionPane.showInputDialog(null, "Is there a port file to read in?\n(Enter 'y' for yes and 'n' for no)", "Open Fileset", JOptionPane.INFORMATION_MESSAGE).charAt(0);
//                        if (readInPort != 'Y' && readInPort != 'y' && readInPort != 'N' && readInPort != 'n') {
//                            JOptionPane.showMessageDialog(null, "Only enter 'y' for yes or 'n' for no! Try again.");
//                        }
//                    } while (readInPort != 'Y' && readInPort != 'y' && readInPort != 'N' && readInPort != 'n');
//                    if (readInPort != 'Y' && readInPort != 'y') {
//                        map = new Map(filename, true);
//                    } else {
//                        map = new Map(filename);
//                    }
//                    pane.moveToBack(backgroundLabel);
//                    closed = false;
//                    break;
                case MenuLibrary.commandClose:
                    pane.moveToFront(backgroundLabel);
                    closed = true;
                    arrayListMonster.clear();
                    map.getArrayListShip().clear();
                    port.getArrayListCargo().clear();
                    port.getArrayListDock().clear();
                    selectedTile = null;
                    godzillaExists = false;
                    repaint();
                    break;
                case MenuLibrary.commandSnapshot:
                    saveMapInForm();
                    break;
            }
        }

        this.repaint();
    }

    //read map, ship, monster and port file and store data in memory
    public void saveMapInForm() {
        boolean flag = true;
        int result = 0;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        File selectedFile = new File("");

        fileChooser.setDialogTitle("Save Ship");
        result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileHandler.writetoFileShip(map.getArrayListShip(), selectedFile.getAbsolutePath());
        }
        
        fileChooser.setDialogTitle("Save Monster");
        result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileHandler.writetoFileMonster(arrayListMonster, selectedFile.getAbsolutePath());
        }
        
        fileChooser.setDialogTitle("Save Port And Cargo");
        result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileHandler.writetoFilePort(port, selectedFile.getAbsolutePath());
        }
    }

    //read map, ship, monster and port file and store data in memory
    public void loadMapInForm() throws FileNotFoundException {
        boolean flag = true;
        int result = 0;

        JFileChooser fileChooser = new JFileChooser();
        File selectedFile = new File("");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

//        //open map file
//        while (flag) {
//
//            try {
//                fileChooser.setDialogTitle("Open Map File");
//                result = fileChooser.showOpenDialog(this);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    selectedFile = fileChooser.getSelectedFile();
//                    map.setMapSymbol(fileHandler.readMapFile(selectedFile.getAbsolutePath()));
//                    break;
//                } else {
//                    break;
//                }
//            } catch (FileNotFoundException ex) {
//                statusTerminal.setText("");
//                statusTerminal.append(String.format("Files, \'%s,\' is not the right one!\n", selectedFile.getAbsolutePath()));
//                flag = true;
//            } catch (ArrayIndexOutOfBoundsException ex) {
//                statusTerminal.setText("");
//                statusTerminal.append(String.format("Files, \'%s,\' is not the right one!\n", selectedFile.getAbsolutePath()));
//                flag = true;
//            }
//        }
        // open port file
        flag = true;
        while (flag) {

            try {
                fileChooser.setDialogTitle("Open Port File");
                result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    fileHandler.readPortFile(selectedFile.getAbsolutePath(), port.getName(), port.getArrayListDock(), port.getArrayListCargo());
                    break;
                } else {
                    break;
                }
            } catch (FileNotFoundException ex) {
                statusTerminal.setText("");
                statusTerminal.append(String.format("Files, \'%s,\' is not the right one!\n", selectedFile.getAbsolutePath()));
                flag = true;
            } catch (ArrayIndexOutOfBoundsException ex) {
                statusTerminal.setText("");
                statusTerminal.append(String.format("Files, \'%s,\' is not the right one!\n", selectedFile.getAbsolutePath()));
                flag = true;
            }
        }

        //open ship file
        flag = true;
        while (flag) {

            try {
                fileChooser.setDialogTitle("Open Ship File");
                result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    map.setArrayListShip(fileHandler.readShipFile(selectedFile.getAbsolutePath()));
                    break;
                } else {
                    break;
                }
            } catch (FileNotFoundException ex) {
                statusTerminal.setText("");
                statusTerminal.append(String.format("Files, \'%s,\' is not the right one!\n", selectedFile.getAbsolutePath()));
                flag = true;
            } catch (ArrayIndexOutOfBoundsException ex) {
                statusTerminal.setText("");
                statusTerminal.append(String.format("Files, \'%s,\' is not the right one!\n", selectedFile.getAbsolutePath()));
                flag = true;
            }
        }
        
        //open monster file
        flag = true;
        while (flag) {

            try {
                fileChooser.setDialogTitle("Open Monster File");
                result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    arrayListMonster = fileHandler.readMonsterFile(selectedFile.getAbsolutePath());
                    break;
                } else {
                    break;
                }
            } catch (FileNotFoundException ex) {
                statusTerminal.setText("");
                statusTerminal.append(String.format("Files, \'%s,\' is not the right one!\n", selectedFile.getAbsolutePath()));
                flag = true;
            } catch (ArrayIndexOutOfBoundsException ex) {
                statusTerminal.setText("");
                statusTerminal.append(String.format("Files, \'%s,\' is not the right one!\n", selectedFile.getAbsolutePath()));
                flag = true;
            }
        }
        pane.moveToBack(backgroundLabel);
        closed = false;
        this.repaint();
    }

    public void startAI() throws InterruptedException {
        int delay = 500;// wait for a half-second

        timer = new Timer(delay, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                shipAI();
                monsterAI();
            }
        });
        timer.start();
    }

    public void shipAI() {
        int newCol = 0;
        int newRow = 0;
        boolean flag = true;
        for (CargoShip ship : map.getArrayListShip()) {
            int col = (MapConverter.lon2col(ship.getLongitude()));
            int row = (MapConverter.lat2row(ship.getLatitude()));
            if (ship.getTargetDock() == null) {
                generateTargetDockForShip(ship);
            }
            int targetCol = (MapConverter.lon2col(ship.getTargetDock().getLongitude()));
            int targetRow = (MapConverter.lat2row(ship.getTargetDock().getLatitude()));

            char symbol;
            boolean hasError = false, failNorth = false, failNortheast = false, failEast = false, failSoutheast = false, failSouth = false, failSouthwest = false, failWest = false, failNorthwest = false;

            if (targetCol == col && targetRow == row) {
                continue;
            }
            while (flag) {
                newCol = col;
                newRow = row;
                if (targetCol > newCol && !hasError) {
                    newCol++;
                } else if (targetCol < newCol && !hasError) {
                    newCol--;
                }

                if (targetRow > newRow && !hasError) {
                    newRow++;
                } else if (targetRow < newRow && !hasError) {
                    newRow--;
                }

                /////////// Test when it has error
                if (targetCol == col && targetRow > row && failSouth) {// test case oilTank
                    newCol--;
                    newRow++;
                } else if (targetCol > col && targetRow > row && (failSoutheast && failEast)) {
                    newCol = col;
                    newRow++;
                } else if (targetCol < col && targetRow > row && failSouthwest) {
                    if (failWest && failSouth) {
                        newCol++;
                        newRow++;
                    } else if (failSouth) {
                        newCol--;
                        newRow = row;
                    } else if (failWest) {
                        newCol = col;
                        newRow++;
                    } else {
                        newCol = col;
                        newRow++;
                    }
                } else if (targetCol > col && targetRow < row && failNortheast) {
                    if (failNorth && failNorthwest) {
                        newCol--;
                        newRow = row;
                    } else if (failNorth) {
                        newCol--;
                        newRow--;
                    } else if (failEast) { //2.5
                        newCol = col;
                        newRow--;
                    }
                } //test case cargo ship
                else if (targetCol == col && targetRow < row && failNorth && failNorthwest && failWest) {
                    symbol = bufferMap[newCol - 2][newRow + 1].getSymbol();
                    if (symbol != '*') { //2.2
                        newCol -= 2;
                        newRow++;
                    } else { //////////////3.1
                        newCol++;
                        newRow--;
                    }
                } else if (targetCol == col && targetRow < row && failNorth && failNorthwest) { // test case CargoShip: 1
                    newCol--;
                    newRow = row;
                } else if (targetCol > col && targetRow < row && failNortheast) { //1.2
                    if (failNorth && failNorthwest) {
                        newCol--;
                        newRow = row;
                    } else if (failNorth) {
                        newCol--;
                        newRow--;
                    } else if (failEast) {
                        newCol = col;
                        newRow--;
                    }
                } else if (targetCol < col && targetRow < row && failNorth && failNorthwest && failWest) { //case 2.1
                    newCol--;
                    newRow++;
                } else if (targetCol < col && targetRow < row && failNorthwest && failWest) { //case 3.2
                    newCol = col;
                    newRow--;
                } else if (targetCol > col && targetRow < row && failNorth && failNorthwest) {//2.3
                    newCol--;
                    newRow = col;
                } else if (targetCol > col && targetRow < row && failNortheast && failNorth) {//2.4
                    newCol--;
                    newRow--;
                } else if (((targetCol > col && targetRow < row) || (targetCol < col && targetRow < row)) && failSouth && failSouthwest && failNorth && failNorthwest) { // test case oiltanker
                    newCol--;
                    newRow = row;
                }
//                        if (symbol != '.' && symbol != '*' && symbol != 'D' && symbol != 'C' && symbol != 'P') {
                symbol = bufferMap[newCol][newRow].getSymbol();
                if (symbol != '*') {
                    flag = true;
                    hasError = false;

                    failNorth = false;
                    failNorthwest = false;
                    failNortheast = false;
                    failEast = false;
                    failSoutheast = false;
                    failSouth = false;
                    failSouthwest = false;
                    failWest = false;

                    break;
                } else {
                    hasError = true;
                    try {
                        if (bufferMap[col + 1][row].getSymbol() == '*') {
                            failEast = true;
                        }
                    } catch (NullPointerException ex) {
                        failEast = true;
                    }
                    try {
                        if (bufferMap[col - 1][row].getSymbol() == '*') {
                            failWest = true;
                        }
                    } catch (NullPointerException ex) {
                        failWest = true;
                    }
                    try {
                        if (bufferMap[col][row + 1].getSymbol() == '*') {
                            failSouth = true;
                        }
                    } catch (NullPointerException ex) {
                        failSouth = true;
                    }
                    try {
                        if (bufferMap[col][row - 1].getSymbol() == '*') {
                            failNorth = true;
                        }
                    } catch (NullPointerException ex) {
                        failNorth = true;
                    }
                    try {
                        if (bufferMap[col + 1][row + 1].getSymbol() == '*') {
                            failSoutheast = true;
                        }
                    } catch (NullPointerException ex) {
                        failSoutheast = true;
                    }
                    try {
                        if (bufferMap[col + 1][row - 1].getSymbol() == '*') {
                            failNortheast = true;
                        }
                    } catch (NullPointerException ex) {
                        failNortheast = true;
                    }
                    try {
                        if (bufferMap[col - 1][row + 1].getSymbol() == '*') {
                            failSouthwest = true;
                        }
                    } catch (NullPointerException ex) {
                        failSouthwest = true;
                    }
                    try {
                        if (bufferMap[col - 1][row - 1].getSymbol() == '*') {
                            failNorthwest = true;
                        }
                    } catch (NullPointerException ex) {
                        failNorthwest = true;
                    }
                }
            }
            Icon newIcon = bufferMap[newCol][newRow].getIcon();
            bufferMap[newCol][newRow].setIcon(bufferMap[col][row].getIcon());
            bufferMap[col][row].setIcon(newIcon);
            char oldSymbol = bufferMap[col][row].getSymbol();
            bufferMap[col][row].setSymbol(bufferMap[newCol][newRow].getSymbol());
            bufferMap[newCol][newRow].setSymbol(oldSymbol);
            ship.getPosition().setColumn(newCol);
            ship.getPosition().setRow(newRow);
            repaint();
        }
    }

    public void monsterAI() {
        int newCol = 0;
        int newRow = 0;
        int step = 1;
        boolean flag = true;
        for (SeaMonster monster : arrayListMonster) {
            int col = (MapConverter.lon2col(monster.getPosition().getLongitude()));
            int row = (MapConverter.lat2row(monster.getPosition().getLatitude()));
            if (monster.getTargetPosition() == null) {
                if (monster instanceof Godzilla) {
                    generateTargetForGodzilla(monster);
                } else {
                    generateTargetShipForMonster(monster);
                }
                if (monster.getTargetPosition() == null) {
                    continue;
                }
            }
            int targetCol = monster.getTargetPosition().getColumn();
            int targetRow = monster.getTargetPosition().getRow();

            char symbol;
            boolean hasError = false, failNorth = false, failNortheast = false, failEast = false, failSoutheast = false, failSouth = false, failSouthwest = false, failWest = false, failNorthwest = false;
            if (targetCol == col && targetRow == row) {
                monster.setTargetPosition(null);
                continue;
            }
//            if (monster instanceof SeaSerpent) {
//                step = 2;
//            } else {
//                step = 1;
//            }
            while (flag) {
                newCol = col;
                newRow = row;
                if (targetCol > newCol && !hasError) {
                    newCol += step;
                } else if (targetCol < newCol && !hasError) {
                    newCol -= step;
                }

                if (targetRow > newRow && !hasError) {
                    newRow += step;
                } else if (targetRow < newRow && !hasError) {
                    newRow -= step;
                }

                /////////// Test when it has error
                if (targetCol == col && targetRow > row && failSouth) {// test case oilTank
                    newCol -= step;
                    newRow += step;
                } else if (targetCol > col && targetRow > row && (failSoutheast && failEast)) {
                    newCol = col;
                    newRow += step;
                } else if (targetCol < col && targetRow > row && failSouthwest) {
                    if (failWest && failSouth) {
                        newCol += step;
                        newRow += step;
                    } else if (failSouth) {
                        newCol -= step;
                        newRow = row;
                    } else if (failWest) {
                        newCol = col;
                        newRow += step;
                    } else {
                        newCol = col;
                        newRow += step;
                    }
                } else if (targetCol > col && targetRow < row && failNortheast && failNorth && failWest && failSouthwest && failSouth) { // test case monster 3.1
                    newCol += step;
                    newRow = row;
                } else if (targetCol > col && targetRow < row && failNortheast) {
                    if (failNorth && failNorthwest) {
                        newCol -= step;
                        newRow = row;
                    } else if (failNorth) {
                        newCol -= step;
                        newRow -= step;
                    } else if (failEast) { //2.5
                        newCol = col;
                        newRow -= step;
                    }
                } //test case cargo ship
                else if (targetCol == col && targetRow < row && failNorth && failNorthwest && failWest) {
                    symbol = bufferMap[newCol - 2][newRow + 1].getSymbol();
                    if (symbol != '*') { //2.2
                        newCol -= 2;
                        newRow += step;
                    } else { //////////////3.1
                        newCol++;
                        newRow -= step;
                    }
                } else if (targetCol == col && targetRow < row && failNorth && failNorthwest) { // test case CargoShip: 1
                    newCol -= step;;
                    newRow = row;
                } else if (targetCol < col && targetRow < row && failNorth && failNorthwest && failWest) { //case 2.1
                    newCol -= step;;
                    newRow += step;
                } else if (targetCol < col && targetRow < row && failNorthwest && failWest) { //case 3.2
                    newCol = col;
                    newRow -= step;
                } else if (targetCol > col && targetRow < row && failNorth && failNorthwest) {//2.3
                    newCol -= step;;
                    newRow = col;
                } else if (targetCol > col && targetRow < row && failNortheast && failNorth) {//2.4
                    newCol -= step;;
                    newRow -= step;
                } else if (((targetCol > col && targetRow < row) || (targetCol < col && targetRow < row)) && failSouth && failSouthwest && failNorth && failNorthwest) { // test case oiltanker
                    newCol -= step;;
                    newRow = row;
                } else if (targetCol > col && targetRow == row && failEast && failSouth) { //test case monster
                    if (failSoutheast) {
                        newCol += step;
                        newRow -= step;
                    } // 4.1.1
                    else {//4.2.1
                        newCol += step;
                        newRow += step;
                    }
                } else if (targetCol > col && targetRow > row && failEast && failSouth) {
                    newCol += step;
                    newRow += step;
                } else if (targetCol < col && targetRow < row && failNorth && failNorthwest && failSoutheast) { //case 4.2
                    newCol -= step;
                    newRow = row;
                } else if (targetCol == col && targetRow < row && failNorthwest && failNorth && failNortheast) {
                    newCol -= step;
                    newRow = row;
                }
//                        if (symbol != '.' && symbol != '*' && symbol != 'D' && symbol != 'C' && symbol != 'P') {
                symbol = bufferMap[newCol][newRow].getSymbol();
                if (symbol != '*') {
                    flag = true;
                    hasError = false;

                    failNorth = false;
                    failNorthwest = false;
                    failNortheast = false;
                    failEast = false;
                    failSoutheast = false;
                    failSouth = false;
                    failSouthwest = false;
                    failWest = false;

                    break;
                } else {
                    hasError = true;
                    try {
                        if (bufferMap[col + 1][row].getSymbol() == '*') {
                            failEast = true;
                        }
                    } catch (NullPointerException ex) {
                        failEast = true;
                    }
                    try {
                        if (bufferMap[col - 1][row].getSymbol() == '*') {
                            failWest = true;
                        }
                    } catch (NullPointerException ex) {
                        failWest = true;
                    }
                    try {
                        if (bufferMap[col][row + 1].getSymbol() == '*') {
                            failSouth = true;
                        }
                    } catch (NullPointerException ex) {
                        failSouth = true;
                    }
                    try {
                        if (bufferMap[col][row - 1].getSymbol() == '*') {
                            failNorth = true;
                        }
                    } catch (NullPointerException ex) {
                        failNorth = true;
                    }
                    try {
                        if (bufferMap[col + 1][row + 1].getSymbol() == '*') {
                            failSoutheast = true;
                        }
                    } catch (NullPointerException ex) {
                        failSoutheast = true;
                    }
                    try {
                        if (bufferMap[col + 1][row - 1].getSymbol() == '*') {
                            failNortheast = true;
                        }
                    } catch (NullPointerException ex) {
                        failNortheast = true;
                    }
                    try {
                        if (bufferMap[col - 1][row + 1].getSymbol() == '*') {
                            failSouthwest = true;
                        }
                    } catch (NullPointerException ex) {
                        failSouthwest = true;
                    }
                    try {
                        if (bufferMap[col - 1][row - 1].getSymbol() == '*') {
                            failNorthwest = true;
                        }
                    } catch (NullPointerException ex) {
                        failNorthwest = true;
                    }
                }
            }
            Icon newIcon = bufferMap[newCol][newRow].getIcon();
            bufferMap[newCol][newRow].setIcon(bufferMap[col][row].getIcon());
            bufferMap[col][row].setIcon(newIcon);
            char oldSymbol = bufferMap[col][row].getSymbol();
            bufferMap[col][row].setSymbol(bufferMap[newCol][newRow].getSymbol());
            bufferMap[newCol][newRow].setSymbol(oldSymbol);
            monster.getPosition().setColumn(newCol);
            monster.getPosition().setRow(newRow);
            repaint();
        }
    }

    public void displayMonstersInForm() {
        if (arrayListMonster.isEmpty()) {
            statusTerminal.append("There is no monster left in the map.\n");
        } else {
            for (SeaMonster monster : arrayListMonster) {
                statusTerminal.append(monster.displayMonster());
            }
            System.out.println();
            System.out.println();
        }
    }

    //gererate monsters and add it to map object
    public void generateMonster(int monsterNumber) {
        ArrayList<SeaMonster> arrayListTypeMonster = new ArrayList<SeaMonster>();
        SeaMonster monster;
        int col, row, counter;
        char symbol;
        boolean flag = true;
        Random random = new Random();

        arrayListTypeMonster.add(new SeaSerpent());
        arrayListTypeMonster.add(new Kraken());
        arrayListTypeMonster.add(new Leviathan());
//        arrayListTypeMonster.add(new Godzilla());
        for (counter = 0; counter < monsterNumber; counter++) {
            //generate location of the ship
            flag = true;

            //random ship object
            int indexMonster = random.nextInt(3);
            if (arrayListTypeMonster.get(indexMonster) instanceof SeaSerpent) {
                monster = new SeaSerpent();
            } else if (arrayListTypeMonster.get(indexMonster) instanceof Kraken) {
                monster = new Kraken();
            } else {
                monster = new Leviathan();
            }
            while (flag) {
                col = Main.randomIntInRange(0, 53);
                row = Main.randomIntInRange(0, 35);

                // check: location of ship is out of map
                if (row > 35 || col > 53) {
                    continue;
                }
                symbol = map.getMapSymbol()[row][col];
                switch (symbol) {
                    case '.':
                    case 'D':
                        monster.setPosition(new Position(col, row));
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
            monster.setLabel(firstName + " " + lastName);

            //add to array list of map
            arrayListMonster.add(monster);
        }

//        for (SeaMonster s : arrayListMonster) {
//            bufferMap[s.getPosition().getColumn()][s.getPosition().getRow()].setTargetPosition(s.getTargetShip().getPosition());
//        }
        this.repaint();
    }

    //gererate monsters and add it to map object
    public void generateGodzilla() {
        if (!godzillaExists) {
            double longitude;
            double latitude;
            int col, row;
            char symbol;
            boolean flag = true;
            Random random = new Random();
            godzilla = new Godzilla();
            while (flag) {
                longitude = Main.randomDoubleInRange(-3.035000, -2.988478);
                latitude = Main.randomDoubleInRange(53.396700, 53.457561);
                col = MapConverter.lon2col(longitude);
                row = MapConverter.lat2row(latitude);

                // check: location of ship is out of map
                if (row > 35 || col > 53) {
                    continue;
                }
                symbol = map.getMapSymbol()[row][col];
                switch (symbol) {
                    case '.':
                    case 'D':
                        godzilla.setPosition(new Position(col,row));
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
            godzilla.setLabel(firstName + " " + lastName);

            //add to array list of map
            for (SeaMonster godzilla : arrayListMonster) {
                if (godzilla instanceof Godzilla) {
                    arrayListMonster.remove(godzilla);
                }
            }
            arrayListMonster.add(godzilla);
            godzillaExists = true;

            this.repaint();
        } else {
            statusTerminal.setText("Godzilla already exists.");
        }
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
                statusTerminal.append("\n");
            }
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
        if (e.getSource() instanceof MapTile) {
            selectedTile = (MapTile) e.getSource();
            char symbol = selectedTile.getSymbol();

            if (symbol != '.' && symbol != '*' && symbol != 'D' && symbol != 'C' && symbol != 'P') {
                //the user has picked up a ship or monster
                startx = e.getX();
                starty = e.getY();
                origx = selectedTile.getX();
                origy = selectedTile.getY();

                startLayer = pane.getLayer(selectedTile);
                pane.moveToFront(selectedTile);

                //check if it is a docked ship
                int targetColumn = selectedTile.getPosition().getColumn();
                int targetRow = selectedTile.getPosition().getRow();
                if (symbol == '$' || symbol == 'S' || symbol == 'B' || symbol == 'T' || symbol == 'X') {
                    for (CargoShip ship : map.getArrayListShip()) {
                        if (ship.getPosition().getColumn() == targetColumn && ship.getPosition().getRow() == targetRow) {
                            selectedShip = ship;
                            selectedMonster = null;

                            //if the ship is on top of another ship, or in a dock, change the image to just the ship
                            if (symbol == '$' || symbol == 'X') {
                                if (ship instanceof OilTanker) {
                                    selectedTile.setIcon(new ImageIcon(MenuLibrary.iconPath + "oilTanker.jpg"));
                                } else if (ship instanceof ContainerShip) {
                                    selectedTile.setIcon(new ImageIcon(MenuLibrary.iconPath + "containerShip.jpg"));
                                } else {
                                    selectedTile.setIcon(new ImageIcon(MenuLibrary.iconPath + "cargoShip.jpg"));
                                }
                            }
                            return;
                        }
                    }
                } else {
                    //the player has targeted a monster
                    for (SeaMonster monster : arrayListMonster) {
                        if (monster.getPosition().getColumn() == targetColumn && monster.getPosition().getRow() == targetRow) {
                            selectedMonster = monster;
                            selectedShip = null;
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Relocates the selected ship/monster/etc onto the nearest tile from where
     * it was dragged to and dropped off at.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof MapTile) {
            if (selectedShip != null || selectedMonster != null) {
                //the user is dropping something they just dragged
                //find where they dropped it
                int dropx = selectedTile.getX() + MenuLibrary.MAP_ORIGIN_X;
                int dropy = selectedTile.getY() + MenuLibrary.MAP_ORIGIN_Y;

                if (dropx >= 0 && dropx < (bufferMap.length * MenuLibrary.ICON_SIZE) && dropy >= 0 && dropy < (bufferMap[0].length * MenuLibrary.ICON_SIZE)) {
                    //the user dropped the tile inside the map
                    //determine which map column / row he wishes to drop it onto
                    int dropCol = (selectedTile.getX() + (selectedTile.getWidth() / 2)) / MenuLibrary.ICON_SIZE;
                    int dropRow = selectedTile.getY() / MenuLibrary.ICON_SIZE - 1;
                    MapTile dropTile = bufferMap[dropCol][dropRow];
                    Position newTargetPosition;

                    //now, update the map tiles to reflect the drag/drop change
                    if (dropTile.getSymbol() != '*' || selectedTile.getSymbol() == 'G') {
                        //the ship / monster was moved to a tile of open water
                        //swap the tiles' symbols and return the dragged tile to it's original position
                        if (selectedShip != null) {
                            for (CargoShip s : map.getArrayListShip()) {
                                if (selectedShip == s) {
                                    s.setPosition(dropTile.getPosition());
                                    generateTargetDockForShip(s);
                                    newTargetPosition = new Position();
                                    newTargetPosition.setColumn(MapConverter.lon2col(s.getTargetDock().getLongitude()));
                                    newTargetPosition.setRow(MapConverter.lat2row(s.getTargetDock().getLatitude()));
                                    dropTile.setTargetPosition(newTargetPosition);
                                    selectedTile.setTargetPosition(null);//clear the target from the old tile
                                    break;
                                }
                            }
                        } else {
                            for (SeaMonster s : arrayListMonster) {
                                if (selectedMonster == s) {
                                    s.setPosition(dropTile.getPosition());
                                    break;
                                }
                            }
                        }
                    }
                }

                //return the dragged tile to it's original position
                selectedTile.setLocation(origx, origy);

                selectedTile = null;
                selectedShip = null;
                selectedMonster = null;

                this.repaint();
            }
        }
    }

    //generate Target Dock For Ship
    public void generateTargetDockForShip(CargoShip ship) {
        ArrayList<Dock> arrayListTargetDock = new ArrayList<Dock>();
        int col, row, colDock, rowDock;
        col = MapConverter.lon2col(ship.getLongitude());
        row = MapConverter.lat2row(ship.getLatitude());
        //get the right docks for the ship
        for (Dock dock : port.getArrayListDock()) {
            if (ship instanceof OilTanker && dock instanceof Pier && dock.isTargeted() == false) {
                arrayListTargetDock.add(dock);
            } else if (ship instanceof ContainerShip && dock instanceof Crane && dock.isTargeted() == false) {
                arrayListTargetDock.add(dock);
            } else if (!(ship instanceof OilTanker)
                    && !(ship instanceof ContainerShip)
                    && !(dock instanceof Pier)
                    && !(dock instanceof Crane)
                    && dock.isTargeted() == false) {
                {
                    arrayListTargetDock.add(dock);
                }
            }
        }

        //find the nearest dock
        //Set target Dock
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
            } else if (!dock2.isTargeted()) {
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

    //generate Target ship For monster
    public void generateTargetShipForMonster(SeaMonster monster) {
        int col, row, colDock, rowDock;
        col = MapConverter.lon2col(monster.getPosition().getLongitude());
        row = MapConverter.lat2row(monster.getPosition().getLatitude());

        //find the nearest ship
        //Set target ship
        for (CargoShip targetShip : map.getArrayListShip()) {
            int distanceRow, distanceCol, totalDistance;
            int targetRow, targetCol, totalDistanceTarget;
            colDock = MapConverter.lon2col(targetShip.getLongitude());
            rowDock = MapConverter.lat2row(targetShip.getLatitude());
            distanceCol = Math.abs(colDock - col);
            distanceRow = Math.abs(rowDock - row);
            totalDistance = distanceCol + distanceRow;

            if (monster.getTargetPosition() == null) {
                monster.setTargetPosition(targetShip.getPosition());
                targetShip.setTargeted(true);
            } else if (!targetShip.isTargeted()) {
                targetCol = Math.abs(monster.getTargetPosition().getColumn() - col);
                targetRow = Math.abs(monster.getTargetPosition().getRow() - row);
                totalDistanceTarget = targetCol + targetRow;
                if (totalDistanceTarget >= totalDistance) {
                    monster.setTargetPosition(targetShip.getPosition());
                    targetShip.setTargeted(true);
                }
            }
        }
    }

    //generate Target monster or ship For monster
    public void generateTargetForGodzilla(SeaMonster monster) {
        int col, row, colDock, rowDock;
        col = MapConverter.lon2col(monster.getPosition().getLongitude());
        row = MapConverter.lat2row(monster.getPosition().getLatitude());

        //find the nearest ship
        //Set target ship
        if (arrayListMonster != null && arrayListMonster.size() >= 2) {
            for (SeaMonster targetMonster : arrayListMonster) {
                int distanceRow, distanceCol, totalDistance;
                int targetRow, targetCol, totalDistanceTarget;
                if (targetMonster instanceof Godzilla) {
                    continue;
                }
                colDock = MapConverter.lon2col(targetMonster.getPosition().getLongitude());
                rowDock = MapConverter.lat2row(targetMonster.getPosition().getLatitude());
                distanceCol = Math.abs(colDock - col);
                distanceRow = Math.abs(rowDock - row);
                totalDistance = distanceCol + distanceRow;

                if (monster.getTargetPosition() == null) {
                    monster.setTargetPosition(targetMonster.getPosition());
                    targetMonster.setTargeted(true);
                } else if (!targetMonster.isTargeted()) {
                    targetCol = Math.abs(monster.getTargetPosition().getColumn() - col);
                    targetRow = Math.abs(monster.getTargetPosition().getRow() - row);
                    totalDistanceTarget = targetCol + targetRow;
                    if (totalDistanceTarget >= totalDistance) {
                        monster.setTargetPosition(targetMonster.getPosition());
                        targetMonster.setTargeted(true);
                    }
                }
            }
        } else {
            for (CargoShip targetShip : map.getArrayListShip()) {
                int distanceRow, distanceCol, totalDistance;
                int targetRow, targetCol, totalDistanceTarget;
                colDock = MapConverter.lon2col(targetShip.getLongitude());
                rowDock = MapConverter.lat2row(targetShip.getLatitude());
                distanceCol = Math.abs(colDock - col);
                distanceRow = Math.abs(rowDock - row);
                totalDistance = distanceCol + distanceRow;

                if (monster.getTargetPosition() == null) {
                    monster.setTargetPosition(targetShip.getPosition());
                    targetShip.setTargeted(true);
                } else if (!targetShip.isTargeted()) {
                    targetCol = Math.abs(monster.getTargetPosition().getColumn() - col);
                    targetRow = Math.abs(monster.getTargetPosition().getRow() - row);
                    totalDistanceTarget = targetCol + targetRow;
                    if (totalDistanceTarget >= totalDistance) {
                        monster.setTargetPosition(targetShip.getPosition());
                        targetShip.setTargeted(true);
                    }
                }
            }
        }
    }

    /**
     * Changes the selected object if the left mouse button is not compressed
     * (i.e. nothing is being dragged)
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof MapTile) {
            MapTile tempTile = (MapTile) e.getSource();

            if (tempTile.getTargetPosition() != null) {
                mouseTerminal.setText(String.format("Column: %d\t\tTarget Column: %d\nRow: %d\t\tTarget Row: %d\n%s", tempTile.getPosition().getColumn(), tempTile.getTargetPosition().getColumn(), tempTile.getPosition().getRow(), tempTile.getTargetPosition().getRow(), tempTile.getDescription()));

            } else {
                mouseTerminal.setText(String.format("Column: %d\nRow: %d\n%s", tempTile.getPosition().getColumn(), tempTile.getPosition().getRow(), tempTile.getDescription()));
            }
        }
    }

    /**
     * Not used in this particular program
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
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
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() instanceof MapTile) {
            selectedTile = (MapTile) e.getSource();
            char symbol = selectedTile.getSymbol();
            if (symbol != '.' && symbol != '*' && symbol != 'D' && symbol != 'C' && symbol != 'P') {
                selectedTile.setLocation(selectedTile.getX() + e.getX() - startx, selectedTile.getY() + e.getY() - starty);
                System.out.println(selectedTile.getX() + e.getX() - startx);
            }
        }
    }

    /**
     * Not used in this particular program
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
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
    public void refreshMapData() throws FileNotFoundException {
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

        // combine monster and map
        if (arrayListMonster != null) {
            for (SeaMonster monster : arrayListMonster) {
                row = monster.getPosition().getRow();
                col = monster.getPosition().getColumn();
                if (monster instanceof Kraken) {
                    map.getMapSymbol()[row][col] = 'K';
                } else if (monster instanceof Leviathan) {
                    map.getMapSymbol()[row][col] = 'L';
                } else if (monster instanceof SeaSerpent) {
                    map.getMapSymbol()[row][col] = 's';
                } else {
                    map.getMapSymbol()[row][col] = 'G';
                }
            }
        }

        // combine not unload ship and map
        for (CargoShip ship : map.getArrayListShip()) {
            if (ship.getCargo() == null) {
                continue;
            }
            row = ship.getPosition().getRow();
            col = ship.getPosition().getColumn();
            if (map.getMapSymbol()[row][col] == '$'
                    || map.getMapSymbol()[row][col] == 'S'
                    || map.getMapSymbol()[row][col] == 'T'
                    || map.getMapSymbol()[row][col] == 'B') {
                map.getMapSymbol()[row][col] = 'X';
            } else if (map.getMapSymbol()[row][col] == 'D'
                    || map.getMapSymbol()[row][col] == 'P'
                    || map.getMapSymbol()[row][col] == 'C') {
                //check for the correct/matching dock type
                char shipSymbol = 'S';
                if (ship instanceof OilTanker) {
                    shipSymbol = 'T';
                } else if (ship instanceof ContainerShip) {
                    shipSymbol = 'B';
                }
                switch (map.getMapSymbol()[row][col]) {
                    case 'D':
                        if (shipSymbol == 'S') {
                            map.getMapSymbol()[row][col] = '$';
                        } else {
                            map.getMapSymbol()[row][col] = 'X';
                        }
                        break;
                    case 'P':
                        if (shipSymbol == 'T') {
                            map.getMapSymbol()[row][col] = '$';
                        } else {
                            map.getMapSymbol()[row][col] = 'X';
                        }
                        break;
                    case 'C':
                        if (shipSymbol == 'B') {
                            map.getMapSymbol()[row][col] = '$';
                        } else {
                            map.getMapSymbol()[row][col] = 'X';
                        }
                        break;
                }
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

        //does a monster occupy the same space as a ship?
        //if so destroy the ship(s)
        if (arrayListMonster != null) {
            for (SeaMonster monster : arrayListMonster) {
                row = monster.getPosition().getRow();
                col = monster.getPosition().getColumn();
                if (map.getMapSymbol()[row][col] == '$' || map.getMapSymbol()[row][col] == 'X' || map.getMapSymbol()[row][col] == 'S' || map.getMapSymbol()[row][col] == 'B' || map.getMapSymbol()[row][col] == 'T') {
                    //there is a ship in danger in the same spot as the monster
                    //destroy all ships at the monsters location
                    statusTerminal.setText(monster.battleCry());
                    for (int i = 0; i < map.getArrayListShip().size(); i++) {
                        CargoShip s = map.getArrayListShip().get(i);
                        if (s.getPosition().getRow() == row && s.getPosition().getColumn() == col) {
                            map.getArrayListShip().remove(s);
                            i--;
                        }
                    }
                    map.getMapSymbol()[row][col] = monster.getSymbol();
                    bufferMap[col][row].setTargetPosition(null);
                }
            }
        }

        if (godzillaExists) {
            row = godzilla.getPosition().getRow();
            col = godzilla.getPosition().getColumn();
            boolean battleCryed = false;
            for (int i = 0; i < arrayListMonster.size(); i++) {
                SeaMonster monster = arrayListMonster.get(i);
                if (!(monster instanceof Godzilla) && monster.getPosition().getRow() == row && monster.getPosition().getColumn() == col) {
                    //destroy the monster
                    if (!battleCryed) {
                        //only battlecry once, no matter how many monsters he eats at once
                        statusTerminal.setText(godzilla.battleCry());
                        battleCryed = true;
                    }
                    arrayListMonster.remove(monster);
                    i--;
                    map.getMapSymbol()[row][col] = 'G';
                    bufferMap[col][row].setTargetPosition(null);
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

    public static int randomIntInRange(int min, int max) {
        Random random = new Random();
        int randomValue = min + random.nextInt(max - min + 1);
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
        this.setLocation(300, 0);

        pane = getLayeredPane();

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
        mtLabel.setBounds(510, 615, 300, 15);
        mtLabel.setBackground(Color.white);
        mtLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        mtLabel.setOpaque(true);

        mouseTerminal = new JTextArea("");
        mouseTerminal.setBounds(510, 630, 300, 60);
        mouseTerminal.setEditable(false);
        mouseTerminal.setOpaque(true);
        mouseTerminal.setBorder(BorderFactory.createLineBorder(Color.black));

        button1 = new JButton(MenuLibrary.commandStart);
        button1.addActionListener(this);
        button1.setBounds(510, 540, 150, 75);

        button2 = new JButton(MenuLibrary.commandStop);
        button2.addActionListener(this);
        button2.setBounds(660, 540, 150, 75);

        //button3 = new JButton(MenuLibrary.command3D);
        //button3.setBounds(660, 615, 150, 75);
        //gridLabel = new JLabel();
        //gridLabel.setBounds(0, 0, 810, 540);
        //gridLabel.setIcon(new ImageIcon(MenuLibrary.iconPath + "gridOverlay.png"));
        //IMPORTANT: file paths should be changed and verified for the demonstration!!!
        //geoMap = new JLabel[54][36];
        bufferMap = new MapTile[1000][1000];
        for (int c = 0; c < 54; c++) {
            for (int r = 0; r < 36; r++) {
                //geoMap[c][r] = new JLabel();
                //geoMap[c][r].setLayout(new BorderLayout());
                bufferMap[c][r] = new MapTile(c, r);
                bufferMap[c][r].setLocation(bufferMap[c][r].getX() + MenuLibrary.MAP_ORIGIN_X, bufferMap[c][r].getY() + MenuLibrary.MAP_ORIGIN_Y);
                bufferMap[c][r].addMouseListener(this);
                bufferMap[c][r].addMouseMotionListener(this);
                pane.add(bufferMap[c][r], 1);
            }
        }

        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 23, 810, 540);
        backgroundLabel.setIcon(new ImageIcon(MenuLibrary.iconPath + "background.jpg"));
        backgroundLabel.setOpaque(true);
        pane.add(backgroundLabel);

        this.setJMenuBar(menuBar);

        this.add(stLabel);
        this.add(stScrollPane);
        this.add(mtLabel);
        this.add(mouseTerminal);
        this.add(button1);
        this.add(button2);
        //this.add(button3);
        this.setVisible(true);
        this.requestFocus();
    }

    public ImageIcon symbolToIcon(char symbol) {
        if (symbol == '.') {
            return new ImageIcon(MenuLibrary.iconPath + "seamlessWater.jpg");
        } else if (symbol == '*') {
            return new ImageIcon(MenuLibrary.iconPath + "seamlessShore.jpg");
        } else if (symbol == 'D') {
            return new ImageIcon(MenuLibrary.iconPath + "emptyDock.jpg");
        } else if (symbol == 'C') {
            return new ImageIcon(MenuLibrary.iconPath + "emptyCrane.jpg");
        } else if (symbol == 'P') {
            return new ImageIcon(MenuLibrary.iconPath + "emptyPier.jpg");
        } else if (symbol == 'S') {
            return new ImageIcon(MenuLibrary.iconPath + "cargoShip.png");
        } else if (symbol == 'B') {
            return new ImageIcon(MenuLibrary.iconPath + "containerShip.png");
        } else if (symbol == 'T') {
            return new ImageIcon(MenuLibrary.iconPath + "oilTanker.png");
        } else if (symbol == '$') {
            return new ImageIcon(MenuLibrary.iconPath + "dockedShip.jpg");
        } else if (symbol == 'X') {
            return new ImageIcon(MenuLibrary.iconPath + "unsafeShip.jpg");
        } else if (symbol == 'G') {
            return new ImageIcon(MenuLibrary.iconPath + "godzilla.png");
        } else if (symbol == 'K') {
            return new ImageIcon(MenuLibrary.iconPath + "kraken.png");
        } else if (symbol == 'L') {
            return new ImageIcon(MenuLibrary.iconPath + "leviathan.png");
        } else if (symbol == 's') {
            //CAREFUL! Lowercase 's' is the sea serpent, upper-case 'S' is a cargo ship
            return new ImageIcon(MenuLibrary.iconPath + "seaSerpent.png");
        }

        //to make the compiler happy, add in a guaranteed return statement
        return null;
    }

    @Override
    public void paint(Graphics g) {
        if (!closed) {
            //combine ship, dock, and map data
            //and update images
            try {
                refreshMapData();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            super.paint(g);

            if (selectedTile != null) {
                selectedTile.paint(g);
            }
        } else {
            //don't mess with map data if map doesn't exist
            pane.moveToFront(backgroundLabel);
            super.paint(g);
        }
    }
}
