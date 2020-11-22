package model.cleaner;

import model.algorithm.AbstractCleaningAlgorithm;
import model.points.RestorePoint;
import model.repository.AbstractRepository;

import java.util.List;

public interface AbstractCleaner extends AbstractCleaningAlgorithm {
    boolean isCleaningNeeded(AbstractRepository repository);
}
