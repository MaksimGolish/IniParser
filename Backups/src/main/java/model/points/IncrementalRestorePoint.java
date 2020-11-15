package model.points;

import lombok.Getter;
import lombok.Setter;
import model.storage.Storage;

public class IncrementalRestorePoint extends RestorePoint {
    @Getter
    @Setter
    private RestorePoint previous;

    public IncrementalRestorePoint(Storage storage, RestorePoint previous) {
        super(storage);
        this.previous = previous;
    }
}
