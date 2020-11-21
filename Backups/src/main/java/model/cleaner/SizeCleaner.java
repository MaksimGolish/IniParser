package model.cleaner;

import exception.ValidationErrorException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import model.points.RestorePoint;
import model.repository.AbstractRepository;

import java.util.List;

public class SizeCleaner implements AbstractCleaner {
    @Getter
    private int size;

    public SizeCleaner(int size) {
        if(size < 0)
            throw new ValidationErrorException("Size can't be above zero");
        this.size = size;
    }

    public void setSize(int size) {
        if(size < 0)
            throw new ValidationErrorException("Size can't be above zero");
        this.size = size;
    }

    @Override
    public void clean(AbstractRepository repository) {
        while(isCleaningNeeded(repository))
            repository.delete(0);
    }

    @Override
    public boolean isCleaningNeeded(AbstractRepository repository) {
        if (repository.isEmpty())
            return false;
        return repository.getSize() > size;
    }
}
