package model;

import exception.BackupPointDoesNotExist;
import exception.IllegalMergeException;
import exception.PointCannotBeDeletedException;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.points.FullRestorePoint;
import model.points.IncrementalRestorePoint;
import model.points.RestorePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

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

    public void transformNextToFull(int i) {
        if(isDependent(i)) {
            FullRestorePoint newPoint = new FullRestorePoint(points.get(i + 1).getStorage());
            points.set(i + 1, newPoint);
            if(points.size() > i + 2 && isIncremental(i + 2))
                ((IncrementalRestorePoint) points.get(i + 2)).setPrevious(points.get(i+1));
        } else
            throw new IllegalMergeException();
    }

    public int getIndex(UUID id) {
        return points.indexOf(
                points.stream()
                        .filter(point -> point.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new BackupPointDoesNotExist(id)));
    }

    public boolean isDependent(int i) {
        if(points.size() > i + 1)
            return isIncremental(i + 1);
        return false;
    }

    public boolean isIncremental(int i) {
        return points.get(i) instanceof IncrementalRestorePoint;
    }

    public RestorePoint getLast() {
        return points.get(points.size() - 1);
    }

    private boolean isCleaningNeeded() {
        if(cleanerConfig == null)
            return false;
        if(cleanerConfig.getMode() == CleanerMode.ALL)
            return isExpired() && isAmountOverflow() && isSizeOverflow();
        else if(cleanerConfig.getMode() == CleanerMode.ANY)
            return isExpired() || isAmountOverflow() || isSizeOverflow();
        throw new IllegalStateException(
                "Cleaner mode " + cleanerConfig.getMode().toString() + "is not supported"
        );
    }

    private boolean isExpired() {
        if(cleanerConfig.getDate() != null)
            return points.get(0).getCreationTime().before(cleanerConfig.getDate());
        return false;
    }

    private boolean isAmountOverflow() {
        if(cleanerConfig.getAmount() != null)
            return points.size() > cleanerConfig.getAmount();
        return false;
    }

    private boolean isSizeOverflow() {
        if(cleanerConfig.getSize() != null)
            return getSize() > cleanerConfig.getSize();
        return false;
    }

    public void clean() {
        if (isCleaningNeeded()) {
            cleanByCondition(this::isSizeOverflow);
            cleanByCondition(this::isAmountOverflow);
            cleanByCondition(this::isExpired);
        }
    }

    public void cleanByCondition(Supplier<Boolean> cleaningCondition) {
        while(cleaningCondition.get()) {
            if(isDependent(0))
                transformNextToFull(0);
            points.remove(0);
        }
    }
}
