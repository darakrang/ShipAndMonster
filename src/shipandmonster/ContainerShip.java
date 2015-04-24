/*
 * Dara Krang
 * 1000910872
 */
package shipandmonster;
/**
 *
 * @author Dara
 */
//Class Cargoship
//
// A class that will represent a cargo carrying vessel
// Data Members: name, Country of Registration,transponder, cargo Capacity, length, beam, draft, longitude, latitude and cargo  of the ship.
//
public class ContainerShip extends CargoShip{
    //Create the object
    public ContainerShip() {
        this.name = "Zenda";
        this.country = "Ruritania";
        this.transponder = 0;
        this.capacity = 10;
        this.length = 90;
        this.beam = 10;
        this.draft = 5;
        this.position = new Position();
        this.position.setLongitude(-2.977838);
        this.position.setLatitude(53.410777);
        this.cargo = new Box();
    }

    public ContainerShip(String name, String country, long transponder, double capacity, double length, double beam, double draft, double longitude, double latitude, Cargo cargo) {
        super(name, country, transponder, capacity, length, beam, draft, longitude, latitude, cargo);
    }
    
    public ContainerShip(String dataLine) {
        //This is the Constructor
        String[] token = dataLine.split(",");
        this.name = token[0];
        this.country = token[1];
        this.transponder = Long.parseLong(token[2]);
        this.capacity = Double.parseDouble(token[3]);
        this.length = Double.parseDouble(token[4]);
        this.beam = Double.parseDouble(token[5]);
        this.draft = Double.parseDouble(token[6]);
        this.position = new Position();
        this.position.setLongitude(Double.parseDouble(token[7]));
        this.position.setLatitude(Double.parseDouble(token[8]));
        if (token.length > 9) {
            this.cargo = new Cargo(token[9] + "," + token[10]);
        }
    }

    // display ship information
    public String display() {
        String result = "";
        result += "Container Ship: " + this.name;
        result += "\nCountry of Origin: " + this.country;
        result += "\nTransponder: " + this.transponder;
        result += String.format("\nLength: %.0f metres\n", this.length);
        result += String.format("Beam: %.0f metres\n", this.beam);
        result += String.format("Draft: %.0f metres\n", this.draft);
        result += String.format("Capacity: %.0f tons\n", this.capacity);
        result += "Location (" + this.getLongitude() + "," + this.getLatitude() + ")";
        result += String.format("\nLocation (%d,%d)\n", MapConverter.lon2col(this.getLongitude()), MapConverter.lat2row(this.getLatitude()));
        if (this.getCargo() != null) {
            result += this.getCargo().display();
        } else {
            result += "The cargo is already unloaded\n";
        }
        return result;
    }
    
    /**
     * @return the cargo
     */
    public Box getCargo() {
        return (Box)cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(Box cargo) {
        this.cargo = cargo;
    }

}
