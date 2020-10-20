package model;

public abstract class Vehicle {
    private final float speed;

    protected Vehicle(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public abstract float getTime(int length);
}
