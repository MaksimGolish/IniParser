package model.algorithm;

import model.cleaner.AbstractCleaner;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractHybridAlgorithm implements AbstractCleaningAlgorithm {
    protected List<AbstractCleaner> cleaners;

    public AbstractHybridAlgorithm(List<AbstractCleaner> cleaners) {
        this.cleaners = cleaners;
    }

    public AbstractHybridAlgorithm(AbstractCleaner... cleaners) {
        this.cleaners = Arrays.asList(cleaners);
    }
}
