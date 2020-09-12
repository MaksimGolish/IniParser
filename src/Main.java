import ini.controller.IniParser;
import ini.model.Ini;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        IniParser iniParser = new IniParser();
        Ini ini = iniParser.parse(new File("src/test.ini"));
        System.out.println(ini.getString("COMMON", "DiscCachePath"));
        System.out.println(ini.getString("Section", "login"));
        System.out.println(ini.getFloat("COMMON", "LogNMCD"));
        System.out.println(ini.getInt("COMMON", "StatisterTimeMs"));
        System.out.println(ini.toString());
    }
}