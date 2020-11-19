package model.creator;

import model.Archive;
import model.points.FullRestorePoint;
import model.points.IncrementalRestorePoint;
import model.points.RestoreType;
import model.repository.AbstractRepository;
import model.storage.ArchivedStorage;

import java.io.File;
import java.util.List;

public class ArchivePointCreator implements PointCreator {
    @Override
    public void create(AbstractRepository repository, List<File> storage, RestoreType type) {
        switch (type) {
            case FULL:
                repository.add(new FullRestorePoint(
                        new ArchivedStorage(new Archive(storage))
                ));
            case INCREMENTAL:
                repository.add(new IncrementalRestorePoint(
                        new ArchivedStorage(new Archive(storage)),
                        repository.getLast()
                ));
        }
    }
}
