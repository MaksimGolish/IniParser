package model.repository;

import exception.BackupPointDoesNotExist;
import exception.PointCannotBeDeletedException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.algorithm.AbstractCleaningAlgorithm;
import model.points.IncrementalRestorePoint;
import model.points.RestorePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class RestorePointsRepository implements AbstractRepository {
    @Getter
    @Setter
    private List<RestorePoint> points = new ArrayList<>();
    @Setter
    private AbstractCleaningAlgorithm cleaningAlgorithm;

    public RestorePointsRepository(AbstractCleaningAlgorithm cleaningAlgorithm) {
        this.cleaningAlgorithm = cleaningAlgorithm;
    }

    @Override
    public long getSize() {
        return points.stream()
                .map(point -> point.getStorage().getSize())
                .mapToLong(Long::longValue)
                .sum();
    }

    @Override
    public int getPointsAmount() {
        return points.size();
    }

    @Override
    public void add(RestorePoint point) {
        points.add(point);
    }

    @Override
    public boolean isEmpty() {
        return points.isEmpty();
    }

    @Override
    public RestorePoint get(int i) {
        return points.get(i);
    }

    @Override
    public RestorePoint getLast() {
        return points.get(points.size() - 1);
    }

    @Override
    public void delete(int index) {
        if(isDependent(index))
            throw new PointCannotBeDeletedException();
        points.remove(index);
    }

    private boolean isDependent(int index) {
        if(points.size() > index + 1)
            return points.get(index + 1) instanceof IncrementalRestorePoint;
        return false;
    }

    private int getIndex(UUID id) {
        return points.indexOf(
                points.stream()
                        .filter(point -> point.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new BackupPointDoesNotExist(id)));
    }


    public void clean() {
        if(cleaningAlgorithm != null)
            cleaningAlgorithm.clean(this);
    }
}
