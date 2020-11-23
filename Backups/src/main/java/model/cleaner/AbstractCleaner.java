package model.cleaner;

import model.algorithm.AbstractCleaningAlgorithm;
import model.repository.AbstractRepository;

public interface AbstractCleaner extends AbstractCleaningAlgorithm {
    boolean isCleaningNeeded(AbstractRepository repository);
}
