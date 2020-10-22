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
import races.AirRace;
import races.LandRace;
import races.Race;
import races.UniversalRace;

public class RaceTest {
    @Test(expected = NoRegisteredRacersException.class)
    public void testUnregisteredRace() {
        UniversalRace race = new UniversalRace(1000);
        race.run();
    }

    @Test(expected = IllegalLengthException.class)
    public void testIllegalRaceLength() {
        UniversalRace race = new UniversalRace(-1);
    }

    @Test
    public void airRaceTest() {
        Broom broom = new Broom();
        MagicCarpet magicCarpet = new MagicCarpet();
        Mortar mortar = new Mortar();
        AirRace race = new AirRace(1000);
        race.register(broom, magicCarpet, mortar);
        Assert.assertTrue(race.run() instanceof Broom);
    }

    @Test
    public void landRaceTest() {
        AllTerrainBoots allTerrainBoots = new AllTerrainBoots();
        BactrianCamel bactrianCamel = new BactrianCamel();
        Centaur centaur = new Centaur();
        SpeedCamel speedCamel = new SpeedCamel();
        LandRace race = new LandRace(1000);
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
        Race<Vehicle> race = new UniversalRace(1000);
        race.register(allTerrainBoots, bactrianCamel, centaur,
                speedCamel, broom, magicCarpet, mortar);
        Assert.assertTrue(race.run() instanceof SpeedCamel);
    }

    @Test
    public void runRaceTwice() {
        // Land race
        Broom broom = new Broom();
        MagicCarpet magicCarpet = new MagicCarpet();
        Mortar mortar = new Mortar();
        AirRace airRace = new AirRace(1000);
        airRace.register(broom, magicCarpet, mortar);
        Assert.assertEquals(airRace.run(), airRace.run());
        // Air race
        AllTerrainBoots allTerrainBoots = new AllTerrainBoots();
        BactrianCamel bactrianCamel = new BactrianCamel();
        Centaur centaur = new Centaur();
        SpeedCamel speedCamel = new SpeedCamel();
        LandRace landRace = new LandRace(1000);
        landRace.register(allTerrainBoots, bactrianCamel, centaur, speedCamel);
        Assert.assertEquals(landRace.run(), landRace.run());
        // Any race
        Race<Vehicle> race = new UniversalRace(1000);
        race.register(allTerrainBoots, bactrianCamel, centaur,
                speedCamel, broom, magicCarpet, mortar);
        Assert.assertEquals(race.run(), race.run());
    }
}
