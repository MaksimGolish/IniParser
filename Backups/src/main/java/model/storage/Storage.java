package model.storage;

import java.io.File;
import java.util.List;

public abstract class Storage {
    public abstract void addFiles(File... addedFiles);
    public abstract List<File> getFiles();
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
