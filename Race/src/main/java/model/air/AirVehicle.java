package model.air;

import model.Vehicle;

public abstract class AirVehicle extends Vehicle {
    protected AirVehicle(int speed) {
        super(speed);
    }

    @Override
    public float getTime(int length) {
        return length / getSpeed() * (100 - getDistanceReducer(length)) / 100;
    }

    protected abstract int getDistanceReducer(int length);
}
