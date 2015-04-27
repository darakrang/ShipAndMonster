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

    // read ship file and return as array list object
    public ArrayList<CargoShip> readShipFile(String fileInfo) throws FileNotFoundException {
        ArrayList<CargoShip> shipList = new ArrayList<CargoShip>();
        CargoShip ship;
        int numberCargoShip, numberContainerShip, numberOilTanker, indexLoop;
        Scanner scanner = readFile(fileInfo);
        String dataLine = scanner.nextLine();
        String token[] = dataLine.split(",");
        numberCargoShip = Integer.parseInt(token[0].replaceAll("\\s+", ""));
        numberContainerShip = Integer.parseInt(token[1].replaceAll("\\s+", ""));
        numberOilTanker = Integer.parseInt(token[2].replaceAll("\\s+", ""));
        for (indexLoop = 0; indexLoop < numberCargoShip; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            ship = new CargoShip(dataLine);
            shipList.add(ship);
        }
        for (indexLoop = 0; indexLoop < numberContainerShip; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            ship = new ContainerShip(dataLine);
            shipList.add(ship);
        }
        for (indexLoop = 0; indexLoop < numberOilTanker; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            ship = new OilTanker(dataLine);
            shipList.add(ship);
        }
        scanner.close();
        return shipList;
    }

    // read monster file and return as array list object
    public ArrayList<SeaMonster> readMonsterFile(String fileInfo) throws FileNotFoundException {
        ArrayList<SeaMonster> monsterList = new ArrayList<SeaMonster>();
        SeaMonster monster;
        int numberKraken, numberLeviathan, numberSeaSerpen, numberGodzilla, indexLoop;
        Scanner scanner = readFile(fileInfo);
        String dataLine = scanner.nextLine();
        String token[] = dataLine.split(",");
        numberKraken = Integer.parseInt(token[0].replaceAll("\\s+", ""));
        numberLeviathan = Integer.parseInt(token[1].replaceAll("\\s+", ""));
        numberSeaSerpen = Integer.parseInt(token[2].replaceAll("\\s+", ""));
        numberGodzilla = Integer.parseInt(token[2].replaceAll("\\s+", ""));
        for (indexLoop = 0; indexLoop < numberKraken; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            monster = new Kraken(dataLine);
            monsterList.add(monster);
        }
        for (indexLoop = 0; indexLoop < numberLeviathan; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            monster = new Leviathan(dataLine);
            monsterList.add(monster);
        }
        for (indexLoop = 0; indexLoop < numberSeaSerpen; indexLoop++) {
            if (!scanner.hasNext()) {
                break;
            }
            dataLine = scanner.nextLine();
            monster = new SeaSerpent(dataLine);
            monsterList.add(monster);
        }
        if (numberGodzilla == 1) {
            if (scanner.hasNext()) {
                dataLine = scanner.nextLine();
                monster = new Godzilla(dataLine);
                monsterList.add(monster);
            }
        }
        scanner.close();
        return monsterList;
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

    //write port data to file
    public void writetoFilePort(Port port, String filePath) {
        File file = new File(filePath);
        Formatter myFormatter;

        ArrayList<Dock> arrayListDock = new ArrayList<Dock>();
        ArrayList<Dock> arrayListCrane = new ArrayList<Dock>();
        ArrayList<Dock> arrayListPier = new ArrayList<Dock>();

        for (Dock dock : port.getArrayListDock()) {
            if (dock instanceof Crane) {
                arrayListCrane.add(dock);
            } else if (dock instanceof Pier) {
                arrayListPier.add(dock);
            } else {
                arrayListDock.add(dock);
            }
        }

        try {
            myFormatter = new Formatter(file);
            myFormatter.format("%s\n", port.toString());
            for (Dock dock : arrayListDock) {
                myFormatter.format("%s\n", dock.toString());
            }
            for (Dock dock : arrayListCrane) {
                myFormatter.format("%s\n", dock.toString());
            }
            for (Dock dock : arrayListPier) {
                myFormatter.format("%s\n", dock.toString());
            }
            for (Cargo cargo : port.getArrayListCargo()) {
                myFormatter.format("%s\n", cargo.toString());
            }
            myFormatter.flush();
            myFormatter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    //write ship data to file
    public void writetoFileShip(ArrayList<CargoShip> arrayListShip, String filePath) {
        File file = new File(filePath);
        Formatter myFormatter;
        ArrayList<CargoShip> cargoList = new ArrayList<CargoShip>();
        ArrayList<CargoShip> tankerList = new ArrayList<CargoShip>();
        ArrayList<CargoShip> containerList = new ArrayList<CargoShip>();
        int numCargoShip, numTanker, numContainer;
        for (CargoShip ship : arrayListShip) {
            if (ship instanceof ContainerShip) {
                containerList.add(ship);
            } else if (ship instanceof OilTanker) {
                tankerList.add(ship);
            } else {
                cargoList.add(ship);
            }
        }
        numCargoShip = cargoList.size();
        numContainer = containerList.size();
        numTanker = tankerList.size();

        try {
            myFormatter = new Formatter(file);
            myFormatter.format("%d,%d,%d\n", numCargoShip, numContainer, numTanker);
            for (CargoShip ship : cargoList) {
                myFormatter.format("%s\n", ship.toString());
            }
            for (CargoShip ship : containerList) {
                myFormatter.format("%s\n", ship.toString());
            }
            for (CargoShip ship : tankerList) {
                myFormatter.format("%s\n", ship.toString());
            }
            myFormatter.flush();
            myFormatter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //write ship data to file
    public void writetoFileMonster(ArrayList<SeaMonster> arrayListMonster, String filePath) {
        File file = new File(filePath);
        Formatter myFormatter;
        ArrayList<SeaMonster> arrayListKraken = new ArrayList<SeaMonster>();
        ArrayList<SeaMonster> arrayListLeviathan = new ArrayList<SeaMonster>();
        ArrayList<SeaMonster> arrayListSeaSerpent = new ArrayList<SeaMonster>();
        Godzilla godzilla = new Godzilla();
        int numberKraken, numberLeviathan, numberSeaSerpent, numberGodzilla = 0;
        for (SeaMonster monster : arrayListMonster) {
            if (monster instanceof Kraken) {
                arrayListKraken.add(monster);
            } else if (monster instanceof Leviathan) {
                arrayListLeviathan.add(monster);
            } else if (monster instanceof SeaSerpent) {
                arrayListSeaSerpent.add(monster);
            } else {
                godzilla = (Godzilla) monster;
                numberGodzilla = 1;
            }
        }
        numberKraken = arrayListKraken.size();
        numberSeaSerpent = arrayListLeviathan.size();
        numberLeviathan = arrayListSeaSerpent.size();
        try {
            myFormatter = new Formatter(file);
            myFormatter.format("%d,%d,%d,%d\n", numberKraken, numberSeaSerpent, numberLeviathan, numberGodzilla);
            for (SeaMonster monster : arrayListKraken) {
                myFormatter.format("%s\n", monster.toString());
            }
            for (SeaMonster monster : arrayListLeviathan) {
                myFormatter.format("%s\n", monster.toString());
            }
            for (SeaMonster monster : arrayListSeaSerpent) {
                myFormatter.format("%s\n", monster.toString());
            }
            if (numberGodzilla == 1) {
                myFormatter.format("%s\n", godzilla.toString());
            }
            myFormatter.flush();
            myFormatter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
