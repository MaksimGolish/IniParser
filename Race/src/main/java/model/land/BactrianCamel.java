package model.land;

public class BactrianCamel extends LandVehicle {
    public BactrianCamel() {
        super(10, 30);
    }

    @Override
    protected float getRestDuration(int stopCounter) {
        if(stopCounter==1)
            return 5;
        else
            return 10;
    }
}
