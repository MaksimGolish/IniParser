package model.algorithm;

import model.cleaner.AbstractCleaner;
import model.repository.AbstractRepository;

import java.util.Arrays;
import java.util.List;

public class AllTriggerAlgorithm extends AbstractHybridAlgorithm {
    public AllTriggerAlgorithm(List<AbstractCleaner> cleaners) {
        super(cleaners);
    }

    public AllTriggerAlgorithm(AbstractCleaner... cleaners) {
        super(cleaners);
    }

    @Override
    public void clean(AbstractRepository repository) {
        if(cleaners.stream().allMatch(cleaner -> cleaner.isCleaningNeeded(repository)))
            cleaners.forEach(
                    cleaner -> cleaner.clean(repository)
            );
    }
}
