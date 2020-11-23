package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Archive {
    private final List<File> files;

    public Archive() {
        files = new ArrayList<>();
    }

    public Archive(List<File> files) {
        this.files = files;
    }

    public List<File> unarchive() {
        return files;
    }

    public void addFiles(File... files) {
        this.files.addAll(Arrays.asList(files));
    }
}
