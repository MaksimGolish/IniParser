package model.creator;

import exception.BackupPointDoesNotExist;
import model.points.FullRestorePoint;
import model.points.IncrementalRestorePoint;
import model.points.RestoreType;
import model.repository.AbstractRepository;
import model.storage.AbstractStorage;

import java.io.File;
import java.util.List;

public class FilePointCreator implements PointCreator {
    @Override
    public void create(AbstractRepository repository, List<File> storage, RestoreType type) {
        switch (type) {
            case FULL:
                repository.add(new FullRestorePoint(new AbstractStorage(storage)));
                break;
            case INCREMENTAL:
                if(repository.isEmpty())
                    throw new BackupPointDoesNotExist();
                repository.add(new IncrementalRestorePoint(
                                new AbstractStorage(storage),
                                repository.getLast()
                ));
                break;
            default:
                throw new IllegalStateException("Restore type " + type + " is not supported");
        }
    }
}
