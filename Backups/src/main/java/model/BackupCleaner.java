package model;

import lombok.Builder;
import model.points.RestorePoint;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BackupCleaner {
    private Integer amount;
    private Date date;
    private Integer size;
    private CleanerMode mode;
    private List<RestorePoint> points;

    public List<RestorePoint> clean(List<RestorePoint> pointsToClean) {
        points = pointsToClean;
        if(mode == CleanerMode.ANY
                && (isSizeOverflow() || isAmountOverflow() || isExpired())) {
            cleanAll();
        } else if(mode == CleanerMode.ANY && isSizeOverflow()
                && isAmountOverflow() && isExpired())
            cleanAll();
        return points;
    }

    private void cleanAll() {
        cleanBySize();
        cleanByAmount();
        cleanByDate();
    }

    private void cleanByAmount() {
        if(amount!=null)
            points = points.subList(points.size() - amount, points.size());
    }

    private void cleanByDate() {
        if(date != null)
            points = points.stream()
                    .filter(point -> date.after(date))
                    .collect(Collectors.toList());
    }

    private void cleanBySize() {
        if(size != null)
            while(points.size() >= size)
                points.remove(0);
    }

    private boolean isExpired() {
        return points.get(0).getCreationTime().before(date);
    }

    private boolean isAmountOverflow() {
        return points.size() > amount;
    }

    private boolean isSizeOverflow() {
        return points.stream()
                .map(point -> point.getStorage().getSize())
                .mapToLong(Long::longValue)
                .sum() > size;
    }

    @Builder
    public BackupCleaner(int amount, Date date, int size, CleanerMode mode) {
        this.amount = amount;
        this.date = date;
        this.size = size;
        this.mode = mode;
    }
}
