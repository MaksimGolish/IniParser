package model;

import exception.EmptyStorageException;
import exception.NotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import model.creator.AbstractPointCreator;
import model.points.RestorePoint;
import model.points.RestoreType;
import model.repository.AbstractRepository;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

@Data
public class Backup {
    private final UUID id;
    private final Date creationDate;
    @NonNull
    private final AbstractRepository pointsRepository;
    @NonNull
    private AbstractPointCreator creator;
    private final List<File> currentFiles;
    private final Logger log = Logger.getLogger(Backup.class);

    @Builder
    public Backup(AbstractPointCreator creator, AbstractRepository pointsRepository) {
        this.id = UUID.randomUUID();
        this.creationDate = new Date();
        this.pointsRepository = pointsRepository;
        this.creator = creator;
        this.currentFiles = new ArrayList<>();
        log.info("Backup created, UUID: " + id + ", creation time: " + creationDate);
    }

    public long getSize() {
        return pointsRepository.getSize();
    }

    public int getAmount() {
        return pointsRepository.getPointsAmount();
    }

    public void addFiles(File... files) {
        for(var file : files)
            if(!file.isFile())
                throw new NotFoundException();
        currentFiles.addAll(Arrays.asList(files));
    }

    public boolean deleteFile(File file) {
        return currentFiles.remove(file);
    }

    public void delete(int index) {
        pointsRepository.delete(index);
    }

    public void save(RestoreType type) {
        if(currentFiles.isEmpty())
            throw new EmptyStorageException();
        creator.create(pointsRepository, currentFiles, type);
        log.info("Restore point created, UUID: " +
                pointsRepository.getLast().getId() +
                "creation time: " +
                pointsRepository.getLast().getCreationTime() +
                ", files: " +
                pointsRepository.getLast().getStorage().getFiles());
        clean();
    }


    public RestorePoint getLast() {
        return pointsRepository.getLast();
    }

    public void clean() {
        pointsRepository.clean();
    }
}
