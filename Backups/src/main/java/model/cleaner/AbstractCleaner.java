package model.cleaner;

import model.points.RestorePoint;

import java.util.List;

public interface AbstractCleaner {
    List<RestorePoint> clean(List<RestorePoint> points);
    boolean isCleaningNeeded(List<RestorePoint> points);
}
