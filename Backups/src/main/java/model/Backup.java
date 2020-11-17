package model;

import exception.BackupPointDoesNotExist;
import lombok.Getter;
import model.points.FullRestorePoint;
import model.points.IncrementalRestorePoint;
import model.points.RestorePoint;
import model.points.RestoreType;
import model.storage.ArchivedStorage;
import model.storage.FileStorage;
import model.storage.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Backup {
    @Getter
    private final UUID id;
    @Getter
    private final Date creationDate;
    @Getter
    private final RestorePointsRepository restorePoints;
    private final Storage currentStorage;

    public Backup(BackupType type) {
        id = UUID.randomUUID();
        creationDate = new Date();
        restorePoints = new RestorePointsRepository();
        switch (type){
            case FILES:
                currentStorage = new ArchivedStorage(new Archive());
                break;
            case ARCHIVED:
                currentStorage = new FileStorage();
                break;
            default:
                throw new IllegalStateException(
                        "Backup mode " + type + " is not supported"
                );
        }
    }

    public long getSize() {
        return restorePoints.getSize();
    }

    public int getAmount() {
        return restorePoints.getPointsAmount();
    }

    public void addFiles(File... files) throws FileNotFoundException {
        for(var file : files)
            if(!file.isFile())
                throw new FileNotFoundException();
        currentStorage.addFiles(files);
    }

    public void setCleaner(CleanerConfig config) {
        restorePoints.setCleanerConfig(config);
    }

    public void save(RestoreType type) {
        switch (type) {
            case FULL:
                restorePoints.add(new FullRestorePoint(currentStorage));
                break;
            case INCREMENTAL:
                if(restorePoints.isEmpty())
                    throw new BackupPointDoesNotExist();
                restorePoints.add(
                        new IncrementalRestorePoint(
                                currentStorage,
                                getLast()
                        )
                );
                break;
        }
        clean();
    }

    public void deletePoint(UUID pointId) {
        restorePoints.deleteById(pointId);
    }

    public void delete(int i) {
        restorePoints.deleteByIndex(i);
    }

    public RestorePoint getLast() {
        return restorePoints.getLast();
    }

    public void clean() {
        restorePoints.clean();
    }
}
