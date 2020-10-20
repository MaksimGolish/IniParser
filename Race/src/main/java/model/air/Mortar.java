package model.air;

public class Mortar extends AirVehicle {
    public Mortar() {
        super(8);
    }

    @Override
    public int getDistanceReducer(int length) {
        return 6;
    }
}
