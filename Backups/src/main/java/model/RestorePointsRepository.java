package model;

import exception.BackupPointDoesNotExist;
import exception.PointCannotBeDeletedException;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
        int index = points.indexOf(
                points.stream()
                        .filter(point -> point.getId().equals(pointId))
                        .findFirst()
                        .orElseThrow(() -> new BackupPointDoesNotExist(pointId))
        );
//        if(restorePoints.get(index-1) instanceof IncrementalRestorePoint) {
//            // TODO Incremental merge
//        }
        if(!(points.get(index) instanceof IncrementalRestorePoint))
            points.remove(index);
        else
            throw new PointCannotBeDeletedException();
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
        if(isCleaningNeeded()) {
            cleanByAmount();
            cleanByDate();
            cleanBySize();
        }
    }

    private void cleanBySize() {
        if(isSizeOverflow())
            while(getSize() > cleanerConfig.getSize())
                points.remove(0);
    }

    private void cleanByAmount() {
        if(isAmountOverflow())
            points = points.subList(points.size() - cleanerConfig.getAmount(), points.size());
    }

    private void cleanByDate() {
        while(isExpired())
            points.remove(0);
    }
}
