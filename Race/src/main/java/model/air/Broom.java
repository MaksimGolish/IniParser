package model.air;

public class Broom extends AirVehicle{
    public Broom() {
        super(20);
    }

    @Override
    public float getDistanceReducer(float length) {
        return length / 1000;
    }
}
