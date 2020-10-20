package model.air;

public class MagicCarpet extends AirVehicle {
    public MagicCarpet() {
        super(10);
    }

    @Override
    public int getDistanceReducer(int length) {
        if(length < 1000) return 0;
        else if(length < 5000) return 3;
        else if(length < 10000) return 10;
        else return 5;
    }
}
