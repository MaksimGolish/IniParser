package model.cleaner;

import model.points.RestorePoint;
import model.repository.AbstractRepository;

import java.util.List;

public interface AbstractCleaner {
    void clean(AbstractRepository repository);
    boolean isCleaningNeeded(AbstractRepository repository);
}
