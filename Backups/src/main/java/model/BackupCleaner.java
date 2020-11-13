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
        this.points = pointsToClean;
        if(mode == CleanerMode.ALL) {
            cleanByAmount();
            cleanByDate();
            cleanBySize();
            return points;
        }
        if(amount != null)
            cleanByAmount();
        if(date != null)
            cleanByDate();
        if(size != null)
            cleanBySize();
        return points;
    }

    private void cleanByAmount() {
        points = points.subList(points.size() - amount, points.size());
    }

    private void cleanByDate() {
        points = points.stream()
                .filter(point -> date.after(date))
                .collect(Collectors.toList());
    }

    private void cleanBySize() {
        // TODO Implement
    }

    @Builder
    public BackupCleaner(int amount, Date date, int size, CleanerMode mode) {
        this.amount = amount;
        this.date = date;
        this.size = size;
        this.mode = mode;
    }
}
