package model.air;

public class Broom extends AirVehicle{
    public Broom() {
        super(20);
    }

    @Override
    public int getDistanceReducer(int length) {
        return length / 1000;
    }
}
