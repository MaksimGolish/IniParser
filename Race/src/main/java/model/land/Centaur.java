package model.vehicles.land;

public class Centaur extends LandVehicle {
    public Centaur() {
        super(15, 8);
    }

    float getRestDuration() {
        return 2;
    }
}
