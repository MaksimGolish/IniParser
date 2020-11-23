package model.algorithm;

import model.cleaner.AbstractCleaner;
import model.repository.AbstractRepository;

import java.util.Arrays;
import java.util.List;

public class AnyTriggerAlgorithm extends AbstractHybridAlgorithm {
    public AnyTriggerAlgorithm(List<AbstractCleaner> cleaners) {
        super(cleaners);
    }

    public AnyTriggerAlgorithm(AbstractCleaner... cleaners) {
        super(cleaners);
    }

    @Override
    public void clean(AbstractRepository repository) {
        if(cleaners.stream().anyMatch(cleaner -> cleaner.isCleaningNeeded(repository)))
            cleaners.forEach(
                    cleaner -> cleaner.clean(repository)
            );
    }
}
