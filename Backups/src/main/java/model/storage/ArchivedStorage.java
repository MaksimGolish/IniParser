package model.storage;

import model.Archive;

import java.io.File;
import java.util.List;

public class ArchivedStorage implements Storage {
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

    @Override
    public int getSize() {
        return 0;
    }
}
