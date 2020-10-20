package model.land;

public class BactrianCamel extends LandVehicle {
    public BactrianCamel() {
        super(10, 30);
    }

    @Override
    public float getRestDuration() {
        stopCounter++;
        if(stopCounter==1)
            return 5;
        else
            return 10;
    }
}
