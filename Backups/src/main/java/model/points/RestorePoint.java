package model.points;

import lombok.Data;
import model.storage.Storage;

import java.util.Date;
import java.util.UUID;

@Data
public class RestorePoint {
    private UUID id;
    private Storage storage;
    private Date creationTime;

    public RestorePoint(Storage storage) {
        this.storage = storage;
        id = UUID.randomUUID();
        creationTime = new Date();
    }

    @Override
    public String toString() {
        return "RestorePoint{" +
                "id=" + id +
                ", storage=" + storage +
                ", creationTime=" + creationTime +
                ", type" + getClass() +
                '}';
    }
}
