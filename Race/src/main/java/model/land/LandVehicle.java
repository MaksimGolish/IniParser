package model.land;

import model.Vehicle;

public abstract class LandVehicle extends Vehicle {
    protected int restInterval;
    protected int stopCounter = 0;

    public abstract float getRestDuration();

    protected LandVehicle(int speed, int restInterval) {
        super(speed);
        this.restInterval = restInterval;
    }

    private float countRestTime(int length) {
        float result = 0;
        for(int i = 0; i < length / getSpeed() / getRestInterval(); i++)
            result += getRestDuration();
        return result;
    }

    @Override
    public float getTime(int length) {
        return length / getSpeed() + countRestTime(length);
    }

    private int getRestInterval() {
        return restInterval;
    }
}
