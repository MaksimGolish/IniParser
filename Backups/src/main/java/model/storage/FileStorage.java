package model.storage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileStorage implements Storage {
    List<File> files;

    @Override
    public void addFiles(File... addedFiles) {
        files.addAll(Arrays.asList(addedFiles));
    }

    @Override
    public List<File> getFiles() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }
}
