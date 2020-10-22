package model.land;

import model.Vehicle;

public abstract class LandVehicle extends Vehicle {
    protected final int restInterval;

    protected abstract float getRestDuration(int stopCounter);

    protected LandVehicle(int speed, int restInterval) {
        super(speed);
        this.restInterval = restInterval;
    }

    private float countRestTime(float length) {
        float result = 0;
        int counter = 1;
        for(int i = 0; i < length / getSpeed() / getRestInterval(); i++) {
            result += getRestDuration(counter);
            counter++;
        }
        return result;
    }

    @Override
    public float getTime(float length) {
        return length / getSpeed() + countRestTime(length);
    }

    private int getRestInterval() {
        return restInterval;
    }
}
