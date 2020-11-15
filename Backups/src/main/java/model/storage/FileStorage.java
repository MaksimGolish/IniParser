package model.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileStorage extends Storage {
    private final List<File> files = new ArrayList<>();

    @Override
    public void addFiles(File... addedFiles) {
        files.addAll(Arrays.asList(addedFiles));
    }

    @Override
    public List<File> getFiles() {
        return files;
    }
}
