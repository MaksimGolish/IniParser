package model.cleaner;

import model.algorithm.AbstractHybridCleaningAlgorithm;
import model.repository.AbstractRepository;

public interface AbstractCleaner extends AbstractHybridCleaningAlgorithm {
    boolean isCleaningNeeded(AbstractRepository repository);
}
