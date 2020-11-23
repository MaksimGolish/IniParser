package model.creator;

import exception.BackupPointDoesNotExist;
import model.Archive;
import model.points.AbstractRestoreType;
import model.points.FullRestorePoint;
import model.points.IncrementalRestorePoint;
import model.points.RestoreType;
import model.repository.AbstractRepository;
import model.storage.ArchivedStorage;
import model.storage.FileStorage;

import java.io.File;
import java.util.List;

public class FilePointCreator implements AbstractPointCreator {
    @Override
    public void create(AbstractRepository repository, List<File> storage, AbstractRestoreType type) {
        if(type == RestoreType.FULL)
            repository.add(new FullRestorePoint(
                    new ArchivedStorage(new Archive(storage))
            ));
        else if(type == RestoreType.INCREMENTAL)
            repository.add(new IncrementalRestorePoint(
                    new FileStorage(storage),
                    repository.getLast()
            ));
        else
            throw new IllegalStateException("Type " + type.toString() + " is not supported");
    }
}
