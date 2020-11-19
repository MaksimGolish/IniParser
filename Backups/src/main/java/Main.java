import model.Backup;
import model.cleaner.AmountCleaner;
import model.algorithm.AnyTriggerAlgorithm;
import model.creator.FilePointCreator;
import model.points.RestoreType;
import model.repository.RestorePointsRepository;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        FilePointCreator creator = new FilePointCreator();
        RestorePointsRepository restorePointsRepository = new RestorePointsRepository(
                new AnyTriggerAlgorithm(
                        new AmountCleaner(1)
                )
        );
        Backup backup =
                Backup.builder()
                        .creator(creator)
                        .pointsRepository(restorePointsRepository)
                        .build();
        backup.addFiles(
                new File("src/test/resources/test1.txt"),
                new File("src/test/resources/test2.txt"),
                new File("src/test/resources/test3.txt")
        );
        backup.save(RestoreType.FULL);
        backup.save(RestoreType.FULL);
    }
}
