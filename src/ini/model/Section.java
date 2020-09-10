package ini.model;

public class Section {
    private String name;
    private final PropertyCollection properties;

    public Section(String name, PropertyCollection properties) {
        this.name = name;
        this.properties = properties;
    }

    public Section(String name) {
        this.name = name;
        properties = new PropertyCollection();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyCollection getProperties() {
        return properties;
    }

    public void addProperty(Property<?> property) {
        properties.add(property);
    }
}
