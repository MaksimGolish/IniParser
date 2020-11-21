package model.cleaner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import model.points.RestorePoint;
import model.repository.AbstractRepository;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class DateCleaner implements AbstractCleaner {
    private Date date;

    @Override
    public void clean(AbstractRepository repository) {
        while (isCleaningNeeded(repository))
            repository.delete(0);
    }

    @Override
    public boolean isCleaningNeeded(AbstractRepository repository) {
        if (repository.isEmpty())
            return false;
        return repository.get(0).getCreationTime().before(date);
    }
}
