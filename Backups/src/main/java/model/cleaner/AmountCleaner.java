package model.cleaner;

import exception.ValidationErrorException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import model.points.RestorePoint;
import model.repository.AbstractRepository;

import java.util.List;

public class AmountCleaner implements AbstractCleaner {
    @Getter
    private int amount;

    public AmountCleaner(int amount) {
        if(amount < 0)
            throw new ValidationErrorException("Amount can't be above zero");
        this.amount = amount;
    }

    public void setAmount(int amount) {
        if(amount < 0)
            throw new ValidationErrorException("Amount can't be above zero");
        this.amount = amount;
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
        return repository.getPointsAmount() > amount;
    }
}
