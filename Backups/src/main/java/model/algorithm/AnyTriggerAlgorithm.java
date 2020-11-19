package model.algorithm;

import model.algorithm.AbstractCleaningAlgorithm;
import model.cleaner.AbstractCleaner;
import model.repository.AbstractRepository;

import java.util.Arrays;
import java.util.List;

public class AnyTriggerAlgorithm implements AbstractCleaningAlgorithm {
    private final List<AbstractCleaner> cleaners;

    public AnyTriggerAlgorithm(List<AbstractCleaner> cleaners) {
        this.cleaners = cleaners;
    }

    public AnyTriggerAlgorithm(AbstractCleaner... cleaners) {
        this.cleaners = Arrays.asList(cleaners);
    }

    @Override
    public void clean(AbstractRepository repository) {
        if(cleaners.stream().anyMatch(cleaner -> cleaner.isCleaningNeeded(repository.getPoints())))
            cleaners.forEach(
                    cleaner -> repository.setPoints(
                            cleaner.clean(repository.getPoints()))
            );
    }
}
