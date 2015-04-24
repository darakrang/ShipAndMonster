/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;
/**
 *
 * @author Dara
 */
public class Pier extends Dock{
    public Pier(){
        this.name = "North Crane 3";
        this.section = 'S';
        this.number = 2;
        this.depth = 15;
        this.length = 100;
        this.width = 6;
        this.position = new Position();
        this.position.setLongitude(-3.013056);
        this.position.setLatitude(53.450606);
        this.symbol = 'P';
    }

    public Pier(String name, char section, int number, double depth, double length, double width, double longitude, double latitude) {
        super(name, section, number, depth, length, width, longitude, latitude);
    }
    
    public Pier(String dataLine) {
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
    }
    
    /**
     * display Crane information
     */
    @Override
    public void display() {
        System.out.println(this.getName());
        System.out.println("Pier Number: " + this.getNumber());     
        System.out.format("Size: %.0f*%.0f*%.0f metres\n", this.getDepth(), this.getLength(), this.getWidth());
        System.out.println("Location (" + this.getLongitude() + "," + this.getLatitude() + ")");
        System.out.printf("Location (%d,%d)\n", MapConverter.lon2col(this.getLongitude()), MapConverter.lat2row(this.getLatitude()));
    }
}
