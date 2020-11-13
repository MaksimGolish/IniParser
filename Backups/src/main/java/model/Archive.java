package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Archive {
    private final List<File> files = new ArrayList<>();

    public Archive() {
    }

    public Archive(File... files) {
        addFiles(files);
    }

    public List<File> unarchive() {
        return files;
    }

    public void addFiles(File... files) {
        this.files.addAll(Arrays.asList(files));
    }
}
