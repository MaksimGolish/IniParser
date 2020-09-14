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

    private Property<?> parseProperty(String property) {
        property = trimComments(property);

        // Split property to key and value
        String[] args = property
                .replaceAll("\\s+","")
                .split("=");

        if(args.length!=2)
            throw new SyntaxErrorException("Wrong property declaration");

        // Checking type
        try {
            return new Property<>(args[0], Integer.parseInt(args[1])); // Is int
        } catch (Exception ignored){}
        try {
            return new Property<>(args[0], Float.parseFloat(args[1])); // Is float
        } catch (Exception ignored){}
        return new Property<>(args[0], args[1]);
    }

    private Section parseSection(Scanner scanner, String name) throws Exception {
        Section section = new Section(name);

        while(scanner.hasNextLine()) {
            String propertyString = scanner.nextLine();
            if(propertyString.isBlank()) // End of section
                break;

            propertyString = trimComments(propertyString);

            if(propertyString.isBlank()) // Is comment without properties
                continue;

            if(propertyString.matches(propertyPattern)) {
                section.addProperty(parseProperty(propertyString));
            } else {
                throw new SyntaxErrorException("Wrong property declaration");
            }
        }
        return section;
    }

    public Ini parse(File file) throws Exception {
        if(!file.getName().matches(".*\\.ini"))
            throw new WrongFileExtensionException("File extension is not INI");
        Ini ini = new Ini();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String currentLine = trimComments(scanner.nextLine());
            if(currentLine.isBlank()) // Is comment
                continue;
            if(currentLine.matches(sectionPattern)) {
                ini.addSection(parseSection(scanner, currentLine
                        .replaceAll("\\[", "")
                        .replaceAll("]","")
                        .replaceAll(" ", "")));
            } else {
                throw new SyntaxErrorException("Section declaration not found");
            }
        }
        return ini;
    }
}
