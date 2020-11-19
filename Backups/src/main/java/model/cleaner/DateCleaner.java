package model.cleaner;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.points.RestorePoint;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class DateCleaner implements AbstractCleaner {
    private Date date;

    @Override
    public List<RestorePoint> clean(List<RestorePoint> points) {
        while (isCleaningNeeded(points))
            points.remove(0);
        return points;
    }

    @Override
    public boolean isCleaningNeeded(List<RestorePoint> points) {
        if (points.isEmpty())
            return false;
        return points.get(0).getCreationTime().before(date);
    }
}
