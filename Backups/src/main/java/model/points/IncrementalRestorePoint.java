package model.points;

import lombok.Getter;
import model.storage.Storage;

public class IncrementalRestorePoint extends RestorePoint {
    @Getter
    private final RestorePoint previous;

    public IncrementalRestorePoint(Storage storage, RestorePoint previous) {
        super(storage);
        this.previous = previous;
    }
}
