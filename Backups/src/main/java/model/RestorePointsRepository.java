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

@NoArgsConstructor
public class RestorePointsRepository {
    private List<RestorePoint> points = new ArrayList<>();
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

    // Создаем новую точку с данными из инкрементальной точки,
    // удаляем старую полную точку, если следующая инкрементальная - устанавливаем для неё новую предыдущую
    public void merge(int i) {
        if(isDependent(i)) {
            FullRestorePoint newPoint = new FullRestorePoint(points.get(i + 1).getStorage());
            points.set(i + 1, newPoint);
            if(points.size() > i + 3 && isIncremental(i + 2))
                ((IncrementalRestorePoint) points.get(i + 2)).setPrevious(points.get(i+1));
            points.remove(i);
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
            cleanByAmount();
            cleanByDate();
            cleanBySize();
        }
    }

    private void mergeTail() {
        while(isDependent(0))
            merge(0);
    }

    private void cleanBySize() {
        if(isSizeOverflow())
            mergeTail();
        if(isSizeOverflow())
            while(getSize() > cleanerConfig.getSize())
                points.remove(0);
    }

    private void cleanByAmount() {
        if(isAmountOverflow())
            mergeTail();
        if(isAmountOverflow())
            points = points.subList(points.size() - cleanerConfig.getAmount(), points.size());
    }

    private void cleanByDate() {
        while(isExpired())
            points.remove(0);
    }
}
