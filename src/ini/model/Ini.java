package ini.model;

import ini.exception.TypeMismatchException;

import java.lang.reflect.ParameterizedType;

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

    public int tryGetInt(String sectionName, String propertyKey) {
        Object prop = sections
                .findByName(sectionName)
                .getProperties()
                .findByKey(propertyKey).getValue();
        if(prop instanceof Integer){
            return (int) prop;
        } else {
            throw new TypeMismatchException("Property value is not int");
        }
    }

    public float tryGetFloat(String sectionName, String propertyKey) {
        Object prop = sections
                .findByName(sectionName)
                .getProperties()
                .findByKey(propertyKey).getValue();
        if(prop instanceof Float){
            return (float) prop;
        } else {
            throw new TypeMismatchException("Property value is not float");
        }
    }

    public String tryGetString(String sectionName, String propertyKey) {
        Object prop = sections
                .findByName(sectionName)
                .getProperties()
                .findByKey(propertyKey).getValue();
        if(prop instanceof String){
            return (String) prop;
        } else {
            throw new TypeMismatchException("Property value is not string");
        }
    }

    // TODO Parametrized getter
//    public <T> T tryGet(String sectionName, String propertyKey) {
//        Object prop = sections
//                .findByName(sectionName)
//                .getProperties()
//                .findByKey(propertyKey).getValue();
//        Class<T> type = (Class<T>) ((ParameterizedType) getClass()
//                .getGenericSuperclass())
//                .getActualTypeArguments()[0];
//        if(type.isInstance(prop)) {
//            System.out.println(type);
//            System.out.println(prop.getClass());
//            return (T) prop;
//        }
//        else {
//            throw new TypeMismatchException("Type mismatch: " + prop.getClass() + " and " + type);
//        }
//    }
}
