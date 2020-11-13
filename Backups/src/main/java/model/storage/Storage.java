package model.storage;

import java.io.File;
import java.util.List;

public interface Storage {
    void addFiles(File... addedFiles);
    List<File> getFiles();
    int getSize();
}
