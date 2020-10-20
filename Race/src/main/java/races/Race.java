package races;

import exception.IllegalLengthException;
import exception.NoRegisteredRacersException;
import model.Vehicle;

import java.util.*;

public class Race<T extends Vehicle> {
    protected int length;
    protected List<T> vehicleList = new ArrayList<>();
    protected boolean isReady = false;

    protected Race(int length) {
        if(length <= 0)
            throw new IllegalLengthException();
        this.length = length;
    }

    public void register(T... vehicles) {
        isReady = true;
        vehicleList.addAll(Arrays.asList(vehicles));
    }

    public Vehicle run() {
        if(!isReady)
            throw new NoRegisteredRacersException();
        return Collections.min(vehicleList, Comparator.comparing(
                vehicle -> vehicle.getTime(length)
        ));
    }
}
