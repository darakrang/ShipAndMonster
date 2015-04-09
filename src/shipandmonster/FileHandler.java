/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/**
 *
 * @author Dara
 */
public class FileHandler {

    private File file;
    private Map map;
    private Port port;

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    // read file return as scanner object
    public Scanner readFile(String fileInfo) throws FileNotFoundException {
        Scanner scanner = null;
        file = new File(fileInfo);
        scanner = new Scanner(file);
        return scanner;
    }

    //read map file and return as 2 dimensional array
    public char[][] readMapFile(String fileInfo) throws FileNotFoundException {
        char[][] mapSymbol = new char[36][54];
        Scanner scanner = readFile(fileInfo);
        while (scanner.hasNext()) {
            String dataLine = scanner.nextLine();
            String[] parts = dataLine.split(",");
            mapSymbol[Integer.parseInt(parts[1])][Integer.parseInt(parts[0])] = parts[2].charAt(0);
        }
        scanner.close();
        return mapSymbol;
    }

    // read port file and store data in reference port object
    public void readPortFile(String fileInfo, Port port) throws FileNotFoundException {
        ArrayList<Dock> dockList = new ArrayList<Dock>();
        ArrayList<Cargo> cargoList = new ArrayList<Cargo>();
        Dock dock;
        Cargo cargo;
        int numberDock, numberCrane, numberPier, numberCargo, indexLoop;
        Scanner scanner = readFile(fileInfo);
        String dataLine = scanner.nextLine();
        String token[] = dataLine.split(",");

        port.setName(token[0]);
        numberDock = Integer.parseInt(token[1].replaceAll("\\s+", ""));
        numberCrane = Integer.parseInt(token[2].replaceAll("\\s+", ""));
        numberPier = Integer.parseInt(token[3].replaceAll("\\s+", ""));
        numberCargo = Integer.parseInt(token[4].replaceAll("\\s+", ""));

        for (indexLoop = 0; indexLoop < numberDock; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            dock = new Dock(dataLine);
            dockList.add(dock);
        }

        for (indexLoop = 0; indexLoop < numberCrane; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            dock = new Crane(dataLine);
            dockList.add(dock);
        }

        for (indexLoop = 0; indexLoop < numberPier; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            dock = new Pier(dataLine);
            dockList.add(dock);
        }

        for (indexLoop = 0; indexLoop < numberCargo; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            cargo = new Cargo(dataLine);
            cargoList.add(cargo);
        }

        port.setArrayListDock(dockList);
        port.setArrayListCargo(cargoList);
        scanner.close();
    }

    // read port file and store data in reference port object
    public void readPortFile(String fileInfo, String portName, ArrayList<Dock> arrayListDock, ArrayList<Cargo> arrayListCargo) throws FileNotFoundException {
        Dock dock;
        Cargo cargo;
        int numberDock, numberCrane, numberPier, numberCargo, indexLoop;
        Scanner scanner = readFile(fileInfo);
        String dataLine = scanner.nextLine();
        String token[] = dataLine.split(",");

        portName = (token[0]);
        numberDock = Integer.parseInt(token[1].replaceAll("\\s+", ""));
        numberCrane = Integer.parseInt(token[2].replaceAll("\\s+", ""));
        numberPier = Integer.parseInt(token[3].replaceAll("\\s+", ""));
        numberCargo = Integer.parseInt(token[4].replaceAll("\\s+", ""));

        for (indexLoop = 0; indexLoop < numberDock; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            dock = new Dock(dataLine);
            arrayListDock.add(dock);
        }

        for (indexLoop = 0; indexLoop < numberCrane; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            dock = new Crane(dataLine);
            arrayListDock.add(dock);
        }

        for (indexLoop = 0; indexLoop < numberPier; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            dock = new Pier(dataLine);
            arrayListDock.add(dock);
        }

        for (indexLoop = 0; indexLoop < numberCargo; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            cargo = new Cargo(dataLine);
            arrayListCargo.add(cargo);
        }
        scanner.close();
    }

    /**
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(Map map) {
        this.map = map;
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
}
