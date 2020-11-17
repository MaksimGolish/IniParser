package model;

import exception.BackupPointDoesNotExist;
import exception.IllegalPointTransformationException;
import exception.PointCannotBeDeletedException;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.points.FullRestorePoint;
import model.points.IncrementalRestorePoint;
import model.points.RestorePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class RestorePointsRepository {
    private final List<RestorePoint> points = new ArrayList<>();
    @Setter
    private CleanerConfig cleanerConfig;

    public long getSize() {
        return points.stream()
                .map(point -> point.getStorage().getSize())
                .mapToLong(Long::longValue)
                .sum();
    }

    public int getPointsAmount() {
        return points.size();
    }

    public void add(RestorePoint point) {
        points.add(point);
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public void deleteById(UUID pointId) {
        int index = getIndex(pointId);
        if(!isDependent(index))
            points.remove(index);
        else
            throw new PointCannotBeDeletedException();
    }

    public void deleteByIndex(int i) {
        if(!isDependent(i)) {
            points.remove(i);
        } else {
            throw new PointCannotBeDeletedException();
        }
    }

    private void transformNextToFull(int i) {
        if(isDependent(i)) {
            FullRestorePoint newPoint = new FullRestorePoint(points.get(i + 1).getStorage());
            points.set(i + 1, newPoint);
            if(points.size() > i + 2 && isIncremental(i + 2))
                ((IncrementalRestorePoint) points.get(i + 2)).setPrevious(points.get(i+1));
        } else
            throw new IllegalPointTransformationException();
    }

    private int getIndex(UUID id) {
        return points.indexOf(
                points.stream()
                        .filter(point -> point.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new BackupPointDoesNotExist(id)));
    }

    private boolean isDependent(int i) {
        if(points.size() > i + 1)
            return isIncremental(i + 1);
        return false;
    }

    private boolean isIncremental(int i) {
        return points.get(i) instanceof IncrementalRestorePoint;
    }

    public RestorePoint getLast() {
        return points.get(points.size() - 1);
    }

    private boolean isCleaningNeeded() {
        if(cleanerConfig == null)
            return false;
        // Выглядит оч не оч, но более красиво тут вряд ли можно сделать
        if(cleanerConfig.getMode() == CleanerMode.ALL)
            return (isExpired() || !cleanerConfig.isDateEnabled()) &&
                    (isAmountOverflow() || !cleanerConfig.isAmountEnabled()) &&
                    (isSizeOverflow() || !cleanerConfig.isSizeEnabled()) &&
                    !cleanerConfig.isNull();
        else if(cleanerConfig.getMode() == CleanerMode.ANY)
            return isExpired() || isAmountOverflow() || isSizeOverflow();
        throw new IllegalStateException(
                "Cleaner mode " + cleanerConfig.getMode().toString() + "is not supported"
        );
    }

    private boolean isExpired() {
        if(cleanerConfig.getDate() != null && !points.isEmpty())
            return points.get(0).getCreationTime().before(cleanerConfig.getDate());
        return false;
    }

    private boolean isAmountOverflow() {
        if(cleanerConfig.getAmount() != null && !points.isEmpty())
            return points.size() > cleanerConfig.getAmount();
        return false;
    }

    private boolean isSizeOverflow() {
        if(cleanerConfig.getSize() != null && !points.isEmpty())
            return getSize() > cleanerConfig.getSize();
        return false;
    }

    public void clean() {
        if(isCleaningNeeded()) {
            while(isAmountOverflow() || isSizeOverflow() || isExpired()) {
                if(isDependent(0))
                    transformNextToFull(0);
                points.remove(0);
            }
        }
    }
}
