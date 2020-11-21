package model.points;

import lombok.Data;
import model.storage.AbstractStorage;

import java.util.Date;
import java.util.UUID;

@Data
public class RestorePoint {
    private UUID id;
    private AbstractStorage storage;
    private Date creationTime;

    public RestorePoint(AbstractStorage storage) {
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
                ", type=" + getClass() +
                '}';
    }
}
