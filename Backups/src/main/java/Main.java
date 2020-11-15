import model.Backup;
import model.BackupType;
import model.CleanerConfig;
import model.CleanerMode;
import model.points.RestoreType;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Backup backup = new Backup(BackupType.FILES);
        backup.addFiles(
                new File("src/main/resources/test1.txt"),
                new File("src/main/resources/test2.txt")
        );
        backup.save(RestoreType.FULL);
        backup.save(RestoreType.INCREMENTAL);
        backup.addFiles(
                new File("src/main/resources/test1.txt"),
                new File("src/main/resources/test2.txt"),
                new File("src/main/resources/test3.txt"),
                new File("src/main/resources/test4.txt")
        );
        backup.save(RestoreType.INCREMENTAL);
        backup.setCleaner(
                CleanerConfig.builder()
                        .mode(CleanerMode.ANY)
                        .amount(3)
                        .build()
        );
        backup.save(RestoreType.INCREMENTAL);
    }
}
