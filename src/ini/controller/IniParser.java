package ini.controller;

import ini.exception.SyntaxErrorException;
import ini.exception.WrongFileExtensionException;
import ini.model.Ini;
import ini.model.Property;
import ini.model.Section;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class IniParser {
    String sectionPattern = "\\[.*]";
    String propertyWithCommentsPattern = "[a-zA-Z]* ?[=] ?.*[;].*";
    String propertyPattern = "[a-zA-Z]* ?[=] ?.*";
    String commentPattern = "\\s*;.*";

    public IniParser() {
    }

    private void parseSection(Scanner scanner, String name, Ini ini) {
        Section section = new Section(name);

        while(scanner.hasNextLine()) {
            String property = scanner.nextLine();
            if(property.matches(commentPattern)||property.isBlank()) {
                continue;
            } else if(property.matches(propertyPattern)) {
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
                if(args[1].matches("^-?\\d+$")) { // Is string integer
                    section.addProperty(new Property<>(args[0], Integer.parseInt(args[1])));
                } else if(args[1].matches("^-?\\d+$.^-?\\d+$")) { // Is string float
                    section.addProperty(new Property<>(args[0], Float.parseFloat(args[1])));
                } else {
                    section.addProperty(new Property<>(args[0], args[1]));
                }
            } else if(property.matches(sectionPattern)) {
                ini.addSection(section);
                parseSection(scanner, property.replaceAll("\\[", "")
                        .replaceAll("]",""), ini);
            } else {
                throw new SyntaxErrorException("Wrong property declaration");
            }
        }
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
                parseSection(scanner, currentLine
                        .replaceAll("\\[", "")
                        .replaceAll("]",""), ini);
            } else {
                throw new SyntaxErrorException("Section declaration not found");
            }
        }
        return ini;
    }
}