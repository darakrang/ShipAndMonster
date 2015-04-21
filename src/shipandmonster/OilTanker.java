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
public class OilTanker extends CargoShip{
    //Create the object
    public OilTanker() {
        this.name = "Zenda";
        this.country = "Ruritania";
        this.transponder = 0;
        this.capacity = 10;
        this.length = 90;
        this.beam = 10;
        this.draft = 5;
        this.longitude = -2.977838;
        this.latitude = 53.410777;
        this.cargo = new Oil();
    }

    public OilTanker(String name, String country, long transponder, double capacity, double length, double beam, double draft, double longitude, double latitude, Cargo cargo) {
        super(name, country, transponder, capacity, length, beam, draft, longitude, latitude, cargo);
    }

    public OilTanker(String dataLine) {
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
    }

    // display ship information
    @Override
    public String display() {
        String result = "";
        result += "Tanker: " + this.getName();
        result += "\nCountry of Origin: " + this.getCountry();
        result += "\nTransponder: " + this.getTransponder();
        result += String.format("\nLength: %.0f metres\n", this.getLength());
        result += String.format("Beam: %.0f metres\n", this.getBeam());
        result += String.format("Draft: %.0f metres\n", this.getDraft());
        result += String.format("Capacity: %.0f tons\n", this.getCargoCapacity());
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
    @Override
    public Oil getCargo() {
        return (Oil)cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(Box cargo) {
        this.cargo = cargo;
    }
}
