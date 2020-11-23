package model.points;

import lombok.Getter;
import lombok.Setter;
import model.storage.AbstractStorage;

public class IncrementalRestorePoint extends RestorePoint {
    @Getter
    @Setter
    private RestorePoint previous;

    public IncrementalRestorePoint(AbstractStorage storage, RestorePoint previous) {
        super(storage);
        this.previous = previous;
    }
}
