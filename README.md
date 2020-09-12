> ### OOP, lab #1
# Ini Parser

The program written in Java

Usage example:
    
    iniParser iniParser = new IniParser();
    Ini ini = iniParser.parse(new File("src/test.ini"));
    System.out.println(ini.getString("COMMON", "DiscCachePath"));