package model.land;

public class Centaur extends LandVehicle {
    public Centaur() {
        super(15, 8);
    }

    @Override
    protected float getRestDuration(int stopCounter) {
        return 2;
    }
}
