package model.storage;

import exception.FileAlreadyExistsException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileStorage extends Storage {
    private final List<File> files = new ArrayList<>();

    @Override
    public void addFiles(File... addedFiles) {
        for(var file : addedFiles)
            if(exists(file))
                throw new FileAlreadyExistsException();
        files.addAll(Arrays.asList(addedFiles));
    }

    @Override
    public List<File> getFiles() {
        return files;
    }

    @Override
    public boolean deleteFile(File file) {
        return files.remove(file);
    }
}
