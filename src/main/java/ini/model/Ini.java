package ini.model;

import ini.exception.TypeMismatchException;
import ini.collections.SectionCollection;

public class Ini {
    private final SectionCollection sections;

    public Ini() {
        sections = new SectionCollection();
    }

    public Ini(SectionCollection  sections) {
        this.sections = sections;
    }

    public SectionCollection getSections() {
        return sections;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public int getInt(String sectionName, String propertyKey) throws TypeMismatchException {
        try {
            return Integer.parseInt(getPropertyObject(sectionName, propertyKey));
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("Cannot cast property value to int");
        }
    }

    public float getFloat(String sectionName, String propertyKey) throws TypeMismatchException {
        try {
            return Float.parseFloat(getPropertyObject(sectionName, propertyKey));
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("Cannot cast property value to float");
        }
    }

    public String getString(String sectionName, String propertyKey) throws TypeMismatchException {
        return getPropertyObject(sectionName, propertyKey);
    }

    private String getPropertyObject(String sectionName, String propertyKey) {
        return sections
                .findByName(sectionName)
                .getProperties()
                .findByKey(propertyKey).getValue();
    }

    @Override
    public String toString() {
        StringBuilder iniString = new StringBuilder();
        for(Section section : sections.toList())
            iniString.append(section.toString()).append("\n");
        return iniString.toString();
    }
}