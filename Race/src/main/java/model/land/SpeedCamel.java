package model.vehicles.land;

public class SpeedCamel extends LandVehicle {
    public SpeedCamel() {
        super(40, 10);
    }

    @Override
    float getRestDuration() {
        stopCounter++;
        switch (stopCounter) {
            case 1: return 5;
            case 2: return 6.5f;
            default: return 8;
        }
    }
}
