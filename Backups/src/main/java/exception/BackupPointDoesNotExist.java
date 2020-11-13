package exception;

import java.util.UUID;

public class BackupPointDoesNotExist extends RuntimeException {
    public BackupPointDoesNotExist() {
        super("Point does not exist");
    }

    public BackupPointDoesNotExist(UUID uuid) {
        super("Backup point " + uuid.toString() + " does not exist");
    }
}
