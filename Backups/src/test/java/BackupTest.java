import exception.PointCannotBeDeletedException;
import model.Backup;
import model.algorithm.AllTriggerAlgorithm;
import model.algorithm.AnyTriggerAlgorithm;
import model.cleaner.*;
import model.creator.FilePointCreator;
import model.points.RestoreType;
import model.repository.RestorePointsRepository;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.*;

public class BackupTest {
    @Test
    public void createBackupTest(){
        Backup backup = Backup.builder()
                .pointsRepository(new RestorePointsRepository())
                .creator(new FilePointCreator())
                .build();
        backup.addFiles(
                new File("src/test/resources/test1.txt"),
                new File("src/test/resources/test2.txt"),
                new File("src/test/resources/test3.txt"),
                new File("src/test/resources/test4.txt")
        );
        backup.save(RestoreType.FULL);
        Assert.assertEquals(1, backup.getAmount());
    }

    @Test
    public void storageTest(){
        List<File> files = new ArrayList<>(
                Arrays.asList(
                        new File("src/test/resources/test1.txt"),
                        new File("src/test/resources/test2.txt"),
                        new File("src/test/resources/test3.txt"))
        );
        Backup backup = Backup.builder()
                .pointsRepository(new RestorePointsRepository())
                .creator(new FilePointCreator())
                .build();
        backup.addFiles(
                new File("src/test/resources/test1.txt"),
                new File("src/test/resources/test2.txt"),
                new File("src/test/resources/test3.txt")
        );
        backup.save(RestoreType.FULL);
        Assert.assertEquals(
                files,
                backup.getPointsRepository()
                        .getLast()
                        .getStorage()
                        .getFiles()
        );
    }

    @Test
    public void amountCleaningTest() throws InterruptedException {
        List<File> files = new ArrayList<>(
                Arrays.asList(
                        new File("src/test/resources/test1.txt"),
                        new File("src/test/resources/test2.txt"))
        );
        Backup backup = Backup.builder()
                .pointsRepository(
                        new RestorePointsRepository(
                                new AnyTriggerAlgorithm(
                                        new AmountCleaner(1)
                                )
                ))
                .creator(new FilePointCreator())
                .build();
        backup.addFiles(
                new File("src/test/resources/test1.txt"),
                new File("src/test/resources/test2.txt"));
        backup.save(RestoreType.FULL);
        Assert.assertEquals(
                files,
                backup.getPointsRepository()
                        .getLast()
                        .getStorage()
                        .getFiles()
        );
        backup.save(RestoreType.FULL);
        Assert.assertEquals(1, backup.getAmount());
    }

    @Test
    public void sizeTest() {
        Backup backup = Backup.builder()
                .pointsRepository(
                        new RestorePointsRepository(
                                new AnyTriggerAlgorithm(
                                        new SizeCleaner(30)
                                )
                        ))
                .creator(new FilePointCreator())
                .build();
        backup.addFiles(
                new File("src/test/resources/10b.txt"),
                new File("src/test/resources/15b.txt"));
        backup.save(RestoreType.FULL);
        backup.save(RestoreType.FULL);
        Assert.assertEquals(1, backup.getAmount());
    }

    @Test
    public void timeTest() throws InterruptedException {
        Backup backup = Backup.builder()
                .pointsRepository(
                        new RestorePointsRepository(
                                new AnyTriggerAlgorithm(
                                        new DateCleaner(new Date(System.currentTimeMillis()+3000L))
                                )
                        ))
                .creator(new FilePointCreator())
                .build();
        backup.addFiles(
                new File("src/test/resources/test1.txt"),
                new File("src/test/resources/test2.txt"));
        backup.save(RestoreType.FULL);
        Thread.sleep(5000);
        backup.save(RestoreType.FULL);
        Assert.assertEquals(1, backup.getAmount());
    }

    @Test
    public void allConditionsTest() throws InterruptedException {
        Backup backup = Backup.builder()
                .pointsRepository(
                        new RestorePointsRepository(
                                new AllTriggerAlgorithm(
                                        new SizeCleaner(30),
                                        new DateCleaner(new Date(System.currentTimeMillis()+3000L)),
                                        new AmountCleaner(1)
                                )
                        ))
                .creator(new FilePointCreator())
                .build();
        backup.addFiles(
                new File("src/test/resources/10b.txt"),
                new File("src/test/resources/15b.txt"));
        backup.save(RestoreType.FULL);
        Thread.sleep(5000);
        backup.save(RestoreType.FULL);
        Assert.assertEquals(1, backup.getAmount());
    }

    @Test(expected = PointCannotBeDeletedException.class)
    public void testDependentPointDeletion(){
        Backup backup = Backup.builder()
                .pointsRepository(
                        new RestorePointsRepository())
                .creator(new FilePointCreator())
                .build();
        backup.addFiles(
                new File("src/test/resources/10b.txt"),
                new File("src/test/resources/15b.txt"));
        backup.save(RestoreType.FULL);
        backup.save(RestoreType.INCREMENTAL);
        backup.delete(0);
    }
}
