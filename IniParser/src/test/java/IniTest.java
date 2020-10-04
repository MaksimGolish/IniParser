import ini.controller.IniParser;
import ini.exception.SectionNotFoundException;
import ini.exception.SyntaxErrorException;
import ini.exception.TypeMismatchException;
import ini.exception.WrongFileExtensionException;
import ini.model.Ini;
import ini.model.Property;
import ini.model.Section;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class IniTest {
    @Test
    public void tryParseCorrectIni() throws Exception {
        boolean successful = false;
        try {
            Ini ini = new IniParser().parse(new File("src/test/resources/test.ini"));
            successful = true;
        } catch (Exception ignored){}
        Assert.assertTrue(successful);
    }

    @Test
    public void tryParseIncorrectIni() throws Exception {
        boolean successful = false;
        try {
            Ini ini = new IniParser().parse(new File("src/test/resources/incorrect.ini"));
        } catch (SyntaxErrorException ignored){
            successful = true;
        }
        Assert.assertTrue(successful);
    }

    @Test
    public void tryIncorrectSection() throws Exception {
        boolean successful = false;
        try {
            Ini ini = new IniParser().parse(new File("src/test/resources/incorrect_section.ini"));
        } catch (SyntaxErrorException e){
            successful = true;
        }
        Assert.assertTrue(successful);
    }

    @Test
    public void tryNotIniFile() throws Exception {
        boolean successful = false;
        try {
            Ini ini = new IniParser().parse(new File("src/test/resources/text.txt"));
        } catch (WrongFileExtensionException e){
            successful = true;
        }
        Assert.assertTrue(successful);
    }

    @Test
    public void testGetters() throws Exception {
        Ini ini = new IniParser().parse(new File("src/test/resources/test.ini"));
        Assert.assertEquals("/sata/panorama",
                ini.getString("COMMON", "DiscCachePath"));
        Assert.assertEquals(1.0,
                ini.getFloat("COMMON", "LogNMCD"), 0);
        Assert.assertEquals(5000,
                ini.getInt("COMMON", "StatisterTimeMs"));

        boolean successful = false;
        try {
            ini.getInt("COMMON", "DiscCachePath");
        } catch (TypeMismatchException e) {
            successful = true;
        }
        Assert.assertTrue(successful);
    }
}
