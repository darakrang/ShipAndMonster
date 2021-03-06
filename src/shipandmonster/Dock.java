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
//Class Dock
//
// The class Dock will represent an area where a ship can be moored
// Data Members: number, depth, length, width,longitude and latitude of the dock
public class Dock {

    //This is the Class Space
    protected String name;
    protected char section;
    protected int number;
    protected double depth;
    protected double length;
    protected double width;
    protected Position position;
    protected char symbol;
    protected boolean targeted;

    private Scanner systemInput;

    //Create the object
    public Dock(){
        this.name = "Rudolf’s Dock";
        this.section = 'N';
        this.number = 100;
        this.depth = 15;
        this.length = 100;
        this.width = 6;
        this.position = new Position();
        this.position.setLongitude(-2.977838);
        this.position.setLatitude(53.410777);
        this.symbol = 'D';
        
        systemInput = new Scanner(System.in);
    }

    public Dock(String name, char section, int number, double depth, double length, double width, double longitude, double latitude) {
        this.name = name;
        this.section = section;
        this.number = number;
        this.depth = depth;
        this.length = length;
        this.width = width;
        this.position = new Position();
        this.position.setLongitude(longitude);
        this.position.setLatitude(latitude);
        
        systemInput = new Scanner(System.in);
    }
    
    public Dock(String dataLine) {
        //This is the Constructor
        String[] token = dataLine.split(",");
        this.name = token[0];
        this.section = token[1].charAt(0);
        this.number = Integer.parseInt(token[2]);            
        this.length = Double.parseDouble(token[3]);
        this.width = Double.parseDouble(token[4]);
        this.depth = Double.parseDouble(token[5]);
        this.position = new Position();
        this.position.setLongitude(Double.parseDouble(token[6]));
        this.position.setLatitude(Double.parseDouble(token[7]));
        systemInput = new Scanner(System.in);
    }

    /**
     * display dock information
     */
    public void display() {
        System.out.println(this.getName());
        System.out.println("Dock section: " + this.getSection());
        System.out.println("Dock Number: " + this.getNumber());
        System.out.format("Size: %.0f*%.0f*%.0f metres\n", this.getDepth(), this.getLength(), this.getWidth());
        System.out.println("Location (" + this.getLongitude() + "," + this.getLatitude() + ")");
        System.out.printf("Location (%d,%d)\n", MapConverter.lon2col(this.getLongitude()), MapConverter.lat2row(this.getLatitude()));
    }
    
    public String displayDock() {
        String report = new String();
        report +=(this.getName()+"\n");
        report +=("Dock section: " + this.getSection()+"\n");
        report +=("Dock Number: " + this.getNumber()+"\n");
        report += String.format("Size: %.0f*%.0f*%.0f metres\n", this.getDepth(), this.getLength(), this.getWidth());
        report +=("Location (" + this.getLongitude() + "," + this.getLatitude() + ")"+"\n");
        report += String.format("Location (%d,%d)\n\n", MapConverter.lon2col(this.getLongitude()), MapConverter.lat2row(this.getLatitude()));
        return report;
    }
    
    // update Dock Property
    public void updateDockProperty(int property) {
        int inputInt;
        double inputDouble;
        String inputString;
        systemInput = new Scanner(System.in);
        switch (property) {
            case 1:
                System.out.println("Enter dock name: ");
                inputString = systemInput.next();
                setName(inputString);
                break;
            case 2:
                System.out.println("Enter dock section: ");
                inputString = systemInput.next();
                setSection(inputString.charAt(0));
                break;
            case 3:
                System.out.println("Enter dock number: ");
                inputInt = systemInput.nextInt();
                setNumber(inputInt);
                break;
            case 4:
                System.out.println("Enter dock length: ");
                inputDouble = systemInput.nextDouble();
                setLength(inputDouble);
                break;
            case 5:
                System.out.println("Enter dock width: ");
                inputDouble = systemInput.nextDouble();
                setWidth(inputDouble);
                break;
            case 6:
                System.out.println("Enter dock depth: ");
                inputDouble = systemInput.nextDouble();
                setDepth(inputDouble);
                break;
            case 7:
                System.out.println("Enter longitude: ");
                inputDouble = systemInput.nextDouble();
                setLongitude(inputDouble);

                System.out.println("Enter latitude: ");
                inputDouble = systemInput.nextDouble();
                setLatitude(inputDouble);
                break;
        }
    }

    //generate data to one string line
    @Override
    public String toString() {
        String string = "";
        string = this.getName() + "," + this.getSection() + "," + this.getNumber() + "," + this.getLength() + "," + this.getWidth() + "," + this.getDepth() + "," + this.getLongitude() + "," + this.getLatitude();
        return string;
    }

    public Position getPosition()
    {
        return this.position;
    }
    
    public void setPosition(Position p)
    {
        this.position = p;
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
     * @return the section
     */
    public char getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(char section) {
        this.section = section;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the depth
     */
    public double getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(double depth) {
        this.depth = depth;
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
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return this.position.getLongitude();
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.position.setLongitude(longitude);
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return this.position.getLatitude();
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.position.setLatitude(latitude);
    }

    /**
     * @return the symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the targeted
     */
    public boolean isTargeted() {
        return targeted;
    }

    /**
     * @param targeted the targeted to set
     */
    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
    }

   
}
