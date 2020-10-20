package model.land;

public class AllTerrainBoots extends LandVehicle {
    public AllTerrainBoots() {
        super(6, 60);
    }

    @Override
    public float getRestDuration() {
        stopCounter++;
        if(stopCounter==1)
            return 10;
        else
            return 5;
    }
}
