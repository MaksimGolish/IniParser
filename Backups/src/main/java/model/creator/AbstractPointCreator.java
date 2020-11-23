package model.creator;

import model.points.AbstractRestoreType;
import model.points.RestoreType;
import model.repository.AbstractRepository;

import java.io.File;
import java.util.List;

public interface AbstractPointCreator {
    void create(AbstractRepository repository, List<File> storage, AbstractRestoreType type);
}
