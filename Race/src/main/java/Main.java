import model.Vehicle;
import model.air.MagicCarpet;
import model.air.Mortar;
import races.Race;
import races.UniversalRace;

public class Main {
    public static void main(String[] args) {
        Race<Vehicle> race = new UniversalRace(1000);
        race.register(new Mortar(), new MagicCarpet());
    }
}
