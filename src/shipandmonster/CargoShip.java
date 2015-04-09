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
// A class that will represent a cargo carrying vessel
// Data Members: name, Country of Registration,transponder, cargo Capacity, length, beam, draft, longitude, latitude and cargo  of the ship.
//
public class CargoShip {

    //This is the Class Space
    protected String name;
    protected String country;
    protected long transponder;
    protected double capacity;
    protected double length;
    protected double beam;
    protected double draft;
    protected double longitude;
    protected double latitude;
    protected Cargo cargo;
    protected char dockSymbol;

    private Scanner systemInput;

    //Create the object
    public CargoShip() {
        this.name = "Zenda";
        this.country = "Ruritania";
        this.transponder = 0;
        this.capacity = 10;
        this.length = 90;
        this.beam = 10;
        this.draft = 5;
        this.longitude = -2.977838;
        this.latitude = 53.410777;
        this.cargo = new Cargo();

        systemInput = new Scanner(System.in);
    }

    public CargoShip(String name, String country, long transponder, double capacity, double length, double beam, double draft, double longitude, double latitude, Cargo cargo) {
        this.name = name;
        this.country = country;
        this.transponder = transponder;
        this.capacity = capacity;
        this.length = length;
        this.beam = beam;
        this.draft = draft;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cargo = cargo;
        
        systemInput = new Scanner(System.in);
    }
    
    public CargoShip(String dataLine) {
        //This is the Constructor
        String[] token = dataLine.split(",");
        this.name = token[0];
        this.country = token[1];
        this.transponder = Long.parseLong(token[2]);
        this.capacity = Double.parseDouble(token[3]);
        this.length = Double.parseDouble(token[4]);
        this.beam = Double.parseDouble(token[5]);
        this.draft = Double.parseDouble(token[6]);
        this.longitude = Double.parseDouble(token[7]);
        this.latitude = Double.parseDouble(token[8]);
        if (token.length > 9) {
            this.cargo = new Cargo(token[9] + "," + token[10]);
        }

        systemInput = new Scanner(System.in);
    }

    // display ship information
    public void display() {        
        System.out.println("Cargo Ship: " + this.getName());
        System.out.println("Country of Origin: " + this.getCountry());
        System.out.println("Transponder: " + this.getTransponder());
        System.out.format("Length: %.0f metres\n", this.getLength());
        System.out.format("Beam: %.0f metres\n", this.getBeam());
        System.out.format("Draft: %.0f metres\n", this.getDraft());
        System.out.format("Capacity: %.0f tons\n", this.getCargoCapacity());
        System.out.println("Location (" + this.getLongitude() + "," + this.getLatitude() + ")");
        System.out.printf("Location (%d,%d)\n", MapConverter.lon2col(this.getLongitude()), MapConverter.lat2row(this.getLatitude()));
        if (this.getCargo() != null) {
            this.getCargo().display();
        } else {
            System.out.println("The cargo is already unloaded");
        }
    }

    // update ship information
    public void updateShipProperty(int property) {
        String inputString;
        int inputInt;
        double inputDouble;
        systemInput = new Scanner(System.in);
        switch (property) {
            case 1:
                System.out.println("Enter ship name: ");
                inputString = systemInput.next();
                setName(inputString);
                break;
            case 2:
                System.out.println("Enter ship registration: ");
                inputString = systemInput.next();
                setCountry(inputString);
                break;
            case 3:
                System.out.println("Enter ship transponder: ");
                inputInt = systemInput.nextInt();
                this.setTransponder(inputInt);
                break;
            case 4:
                System.out.println("Enter ship capacity: ");
                inputDouble = systemInput.nextDouble();
                this.setCargoCapacity(inputDouble);
                break;
            case 5:
                System.out.println("Enter ship length: ");
                inputDouble = systemInput.nextDouble();
                this.setLength(inputDouble);
                break;
            case 6:
                System.out.println("Enter ship beam: ");
                inputDouble = systemInput.nextDouble();
                this.setBeam(inputDouble);
                break;
            case 7:
                System.out.println("Enter ship draft: ");
                inputDouble = systemInput.nextDouble();
                this.setDraft(inputDouble);
                break;
            case 8:
                System.out.println("Enter ship longitude: ");
                inputDouble = systemInput.nextDouble();
                this.setLongitude(inputDouble);

                System.out.println("Enter ship latitude: ");
                inputDouble = systemInput.nextDouble();
                this.setLatitude(inputDouble);
                break;
            case 9:
                System.out.println("Enter column: ");
                inputInt = systemInput.nextInt();
                inputDouble = MapConverter.col2lon(inputInt);
                this.setLongitude(inputDouble);

                System.out.println("Enter row: ");
                inputInt = systemInput.nextInt();
                inputDouble = MapConverter.row2lat(inputInt);
                this.setLatitude(inputDouble);
                break;
        }
    }

    //generate data to one string line
    public String toString() {
        String string = "";
        if (this.getCargo() != null) {
            string = this.getName() + "," + this.getCountry() + "," + this.getTransponder() + "," + this.getCargoCapacity() + "," + this.getLength() + "," + this.getBeam() + "," + this.getDraft() + "," + this.getLongitude() + "," + this.getLatitude() + "," + this.getCargo().getDescription() + "," + this.getCargo().getTonnage();
        } else {
            string = this.getName() + "," + this.getCountry() + "," + this.getTransponder() + "," + this.getCargoCapacity() + "," + this.getLength() + "," + this.getBeam() + "," + this.getDraft() + "," + this.getLongitude() + "," + this.getLatitude();
        }
        return string;
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
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the transponder
     */
    public long getTransponder() {
        return transponder;
    }

    /**
     * @param transponder the transponder to set
     */
    public void setTransponder(long transponder) {
        this.transponder = transponder;
    }

    /**
     * @return the cargoCapacity
     */
    public double getCargoCapacity() {
        return capacity;
    }

    /**
     * @param cargoCapacity the cargoCapacity to set
     */
    public void setCargoCapacity(double cargoCapacity) {
        this.capacity = cargoCapacity;
    }

    /**
     * @return the length
     */
    public double getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * @return the beam
     */
    public double getBeam() {
        return beam;
    }

    /**
     * @param beam the beam to set
     */
    public void setBeam(double beam) {
        this.beam = beam;
    }

    /**
     * @return the draft
     */
    public double getDraft() {
        return draft;
    }

    /**
     * @param draft the draft to set
     */
    public void setDraft(double draft) {
        this.draft = draft;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the cargo
     */
    public Cargo getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    /**
     * @return the dockSymbol
     */
    public char getDockSymbol() {
        return dockSymbol;
    }

    /**
     * @param dockSymbol the dockSymbol to set
     */
    public void setDockSymbol(char dockSymbol) {
        this.dockSymbol = dockSymbol;
    }
}
