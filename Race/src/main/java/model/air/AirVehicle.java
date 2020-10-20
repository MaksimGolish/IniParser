package model.air;

import model.Vehicle;

public abstract class AirVehicle extends Vehicle {
    protected AirVehicle(int speed) {
        super(speed);
    }

    @Override
    public float getTime(float length) {
        return length * 100 / (100 - getDistanceReducer(length)) / getSpeed();
    }

    protected abstract float getDistanceReducer(float length);
}
