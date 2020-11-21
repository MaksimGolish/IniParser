package model.storage;

import java.io.File;
import java.util.List;

public abstract class AbstractStorage {
    public abstract void addFiles(File... addedFiles);
    public abstract List<File> getFiles();
    public abstract boolean deleteFile(File file);
    public long getSize() {
        return getFiles().stream()
                .map(File::length)
                .mapToLong(Long::longValue)
                .sum();
    }
    public boolean exists(File file) {
        return getFiles().contains(file);
    }
}
