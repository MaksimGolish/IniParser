package model.repository;

import model.points.RestorePoint;

import java.util.List;

public interface AbstractRepository {
    long getSize();
    int getPointsAmount();
    void add(RestorePoint point);
    boolean isEmpty();
    RestorePoint get(int i);
    RestorePoint getLast();
    void clean();
    List<RestorePoint> getPoints();
    void setPoints(List<RestorePoint> points);
    void delete(int index);
}
