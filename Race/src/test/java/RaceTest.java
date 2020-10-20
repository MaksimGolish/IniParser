import exception.IllegalLengthException;
import exception.NoRegisteredRacersException;
import model.Vehicle;
import model.air.AirVehicle;
import model.air.Broom;
import model.air.MagicCarpet;
import model.air.Mortar;
import model.land.*;
import org.junit.Assert;
import org.junit.Test;
import races.Race;

public class RaceTest {
    @Test(expected = NoRegisteredRacersException.class)
    public void testUnregisteredRace() {
        Race<Vehicle> race = new Race<>(1000);
        race.run();
    }

    @Test(expected = IllegalLengthException.class)
    public void testIllegalRaceLength() {
        Race<Vehicle> race = new Race<>(-1);
    }

    @Test
    public void airRaceTest() {
        Broom broom = new Broom();
        MagicCarpet magicCarpet = new MagicCarpet();
        Mortar mortar = new Mortar();
        Race<AirVehicle> race = new Race<>(1000);
        race.register(broom, magicCarpet, mortar);
        Assert.assertTrue(race.run() instanceof Broom);
    }

    @Test
    public void landRaceTest() {
        AllTerrainBoots allTerrainBoots = new AllTerrainBoots();
        BactrianCamel bactrianCamel = new BactrianCamel();
        Centaur centaur = new Centaur();
        SpeedCamel speedCamel = new SpeedCamel();
        Race<LandVehicle> race = new Race<>(1000);
        race.register(allTerrainBoots, bactrianCamel, centaur, speedCamel);
        Assert.assertTrue(race.run() instanceof SpeedCamel);
    }

    @Test
    public void anyRaceTest() {
        AllTerrainBoots allTerrainBoots = new AllTerrainBoots();
        BactrianCamel bactrianCamel = new BactrianCamel();
        Centaur centaur = new Centaur();
        SpeedCamel speedCamel = new SpeedCamel();
        Broom broom = new Broom();
        MagicCarpet magicCarpet = new MagicCarpet();
        Mortar mortar = new Mortar();
        Race<Vehicle> race = new Race<>(1000);
        race.register(allTerrainBoots, bactrianCamel, centaur,
                speedCamel, broom, magicCarpet, mortar);
        Assert.assertTrue(race.run() instanceof SpeedCamel);
    }
}
