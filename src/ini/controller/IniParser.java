package ini.controller;

import ini.exception.SyntaxErrorException;
import ini.exception.WrongFileExtensionException;
import ini.model.Ini;
import ini.model.Property;
import ini.model.Section;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IniParser {
    String sectionPattern = "\\[.*]";
    String propertyWithCommentsPattern = "[a-zA-Z]* ?[=] ?.*[;].*";
    String propertyPattern = "[a-zA-Z]* ?[=] ?.*";
    String commentPattern = "\\s*;.*";

    public IniParser() {
    }

    private @NotNull Property<?> parseProperty(String property) {
        if(property.matches(propertyWithCommentsPattern)) {
            // Delete comments
            property = property.substring(0, property.indexOf(";"));
        }
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

    private @NotNull Section parseSection(Scanner scanner, String name) {
        Section section = new Section(name);

        while(scanner.hasNextLine()) {
            String propertyString = scanner.nextLine();
            if(propertyString.matches(commentPattern)) {
                continue;
            } else if(propertyString.matches(propertyPattern)) {
                section.addProperty(parseProperty(propertyString));
            } else if(propertyString.isBlank()) {
                break;
            } else {
                throw new SyntaxErrorException("Wrong property declaration");
            }
        }
        return section;
    }

    public Ini parse(File file) throws FileNotFoundException {
        if(!file.getName().matches(".*\\.ini"))
            throw new WrongFileExtensionException("File extension is not INI");
        Ini ini = new Ini();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            if(currentLine.isBlank()||currentLine.matches(commentPattern))
                continue;
            if(currentLine.matches(sectionPattern)) {
                ini.addSection(parseSection(scanner, currentLine
                        .replaceAll("\\[", "")
                        .replaceAll("]","")));
            } else {
                throw new SyntaxErrorException("Section declaration not found");
            }
        }
        return ini;
    }
}
