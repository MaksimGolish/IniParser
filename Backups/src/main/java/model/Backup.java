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
import java.util.*;

public class Backup {
    @Getter
    private final UUID id;
    @Getter
    private final Date creationDate;
    @Getter
    private int size = 0;
    @Getter
    private List<RestorePoint> restorePoints = new ArrayList<>();
    private Storage currentStorage;

    public Backup(BackupType type) {
        id = UUID.randomUUID();
        creationDate = new Date();
        switch (type){
            case FILES:
                currentStorage = new ArchivedStorage(new Archive());
            case ARCHIVED:
                currentStorage = new FileStorage();
        }
    }

    public void addFiles(File... files) {
        Arrays.stream(files)
                .forEach(file -> size += file.length());
        currentStorage.addFiles(files);
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
    }

    public void deletePoint(UUID pointId) {
        int index = restorePoints.indexOf(
                restorePoints
                        .stream()
                        .filter(point -> point.getId().equals(pointId))
                        .findFirst()
                        .orElseThrow(() -> new BackupPointDoesNotExist(pointId))
        );
//        if(restorePoints.get(index-1) instanceof IncrementalRestorePoint) {
//            // TODO Incremental merge
//        }

    }

    public RestorePoint getLast() {
        return restorePoints.get(restorePoints.size()-1);
    }
}
