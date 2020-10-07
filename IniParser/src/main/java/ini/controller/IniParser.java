package ini.controller;

import ini.exception.SyntaxErrorException;
import ini.exception.WrongFileExtensionException;
import ini.model.Ini;
import ini.model.Property;
import ini.model.Section;

import java.io.File;
import java.util.Scanner;

public class IniParser {
    private final String sectionPattern;
    private final String propertyPattern;

    public IniParser() {
        sectionPattern = "\\[[a-zA-Z_]*]\\s*";
        propertyPattern = "[a-zA-Z]* ?[=] ?[a-zA-Z0-9./]*\\s*";
    }
    private String trimComments(String string) {
        if(string.indexOf(';')!=-1)
            return string.substring(0, string.indexOf(";"));
        else
            return string;
    }

    private Property parseProperty(String property) {
        property = trimComments(property);

        // Split property to key and value
        String[] args = property
                .replaceAll("\\s+","")
                .split("=");

        if(args.length!=2)
            throw new SyntaxErrorException("Wrong property declaration");

        return new Property(args[0], args[1]);
    }

    private Section parseSection(String name){
        name = name
                .replaceAll("\\[", "")
                .replaceAll("]","")
                .replaceAll(" ", "");
        return new Section(name);
    }

    public Ini parse(File file) throws Exception {
        if(!file.getName().matches(".*\\.ini"))
            throw new WrongFileExtensionException("File extension is not INI");
        Ini ini = new Ini();
        Scanner scanner = new Scanner(file);
        Section currentSection = null;
        for(int lineNum = 1; scanner.hasNextLine(); lineNum++) {
            String currentLine = trimComments(scanner.nextLine());
            if(currentLine.isBlank()) // Is comment
                continue;
            if(currentLine.matches(sectionPattern)) {
                if (currentSection != null)
                    ini.addSection(currentSection);
                currentSection = parseSection(currentLine);
                continue;
            }
            if(currentLine.matches(propertyPattern)) {
                if(currentSection==null)
                    throw new SyntaxErrorException("Section not found");
                currentSection.addProperty(parseProperty(currentLine));
                continue;
            }
            throw new SyntaxErrorException("Wrong syntax in line "
                    + lineNum +": " + currentLine);
        }

        if(currentSection!=null)
            ini.addSection(currentSection);

        return ini;
    }
}
