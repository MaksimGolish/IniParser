package model.land;

public class Centaur extends LandVehicle {
    public Centaur() {
        super(15, 8);
    }

    @Override
    public float getRestDuration() {
        return 2;
    }
}
