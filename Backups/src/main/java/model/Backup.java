package model;

import exception.NotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import model.creator.PointCreator;
import model.points.RestorePoint;
import model.points.RestoreType;
import model.repository.AbstractRepository;

import java.io.File;
import java.util.*;

@Data
public class Backup {
    private final UUID id;
    private final Date creationDate;
    @NonNull
    private final AbstractRepository pointsRepository;
    @NonNull
    private PointCreator creator;
    private final List<File> currentFiles;

    @Builder
    public Backup(PointCreator creator, AbstractRepository pointsRepository) {
        this.id = UUID.randomUUID();
        this.creationDate = new Date();
        this.pointsRepository = pointsRepository;
        this.creator = creator;
        this.currentFiles = new ArrayList<>();
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
        creator.create(pointsRepository, currentFiles, type);
        clean();
    }


    public RestorePoint getLast() {
        return pointsRepository.getLast();
    }

    public void clean() {
        pointsRepository.clean();
    }
}
