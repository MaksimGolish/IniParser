package model.cleaner;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.points.RestorePoint;

import java.util.List;

@Data
@AllArgsConstructor
public class SizeCleaner implements AbstractCleaner {
    private int size;

    @Override
    public List<RestorePoint> clean(List<RestorePoint> points) {
        while(isCleaningNeeded(points))
            points.remove(0);
        return points;
    }

    @Override
    public boolean isCleaningNeeded(List<RestorePoint> points) {
        if (points.isEmpty())
            return false;
        return getFullSize(points) > size;
    }

    private long getFullSize(List<RestorePoint> points) {
        return points.stream()
                .map(point -> point.getStorage().getSize())
                .mapToLong(Long::longValue)
                .sum();
    }
}
