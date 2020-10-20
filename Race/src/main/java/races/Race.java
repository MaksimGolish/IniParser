package model.races;

import model.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public abstract class Race<T> {
    protected List<Vehicle> vehicleList;
    abstract void register(T... vehicles);
    abstract T run();
}
