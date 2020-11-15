package model.storage;

import lombok.Getter;
import lombok.Setter;
import model.Archive;

import java.io.File;
import java.util.List;

public class ArchivedStorage extends Storage {
    @Getter
    @Setter
    private Archive archive;

    public ArchivedStorage(Archive archive) {
        this.archive = archive;
    }

    @Override
    public void addFiles(File... addedFiles) {
        archive.addFiles(addedFiles);
    }

    @Override
    public List<File> getFiles() {
        return archive.unarchive();
    }
}
