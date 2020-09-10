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

    public int getInt(String sectionName, String propertyKey) {
        Object prop = getPropertyObject(sectionName, propertyKey);
        if(prop instanceof Integer){
            return (int) prop;
        } else {
            throw new TypeMismatchException("Property value is not int");
        }
    }

    public float getFloat(String sectionName, String propertyKey) {
        Object prop = getPropertyObject(sectionName, propertyKey);
        if(prop instanceof Float){
            return (float) prop;
        } else {
            throw new TypeMismatchException("Property value is not float");
        }
    }

    public String getString(String sectionName, String propertyKey) {
        Object prop = getPropertyObject(sectionName, propertyKey);
        if(prop instanceof String){
            return (String) prop;
        } else {
            throw new TypeMismatchException("Property value is not string");
        }
    }

    private Object getPropertyObject(String sectionName, String propertyKey) {
        return sections
                .findByName(sectionName)
                .getProperties()
                .findByKey(propertyKey).getValue();
    }
}