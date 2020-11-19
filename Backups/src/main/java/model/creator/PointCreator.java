package model.creator;

import model.points.RestoreType;
import model.repository.AbstractRepository;

import java.io.File;
import java.util.List;

public interface PointCreator {
    void create(AbstractRepository repository, List<File> storage, RestoreType type);
}
