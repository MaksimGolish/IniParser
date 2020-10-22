package model.air;

public class Mortar extends AirVehicle {
    public Mortar() {
        super(8);
    }

    @Override
    public float getDistanceReducer(float length) {
        return 6;
    }
}
