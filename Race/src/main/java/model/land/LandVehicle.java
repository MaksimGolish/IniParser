package model.vehicles.land;

import model.vehicles.Vehicle;

public abstract class LandVehicle extends Vehicle {
    protected int restInterval;
    protected int stopCounter = 0;
    abstract float getRestDuration();

    protected LandVehicle(int speed, int restInterval) {
        super(speed);
        this.restInterval = restInterval;
    }

    public int getRestInterval() {
        return restInterval;
    }

    public void setRestInterval(int restInterval) {
        this.restInterval = restInterval;
    }
}
