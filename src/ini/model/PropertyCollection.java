package ini.model;

import ini.exception.PropertyNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class PropertyCollection {
    private final List<Property<?>> properties = new ArrayList<>();

    public PropertyCollection() {
    }

    public List<Property<?>> toList() {
        return properties;
    }

    public void add(Property<?> property) {
        properties.add(property);
    }

    public Property<?> findByKey(String name) {
        for(Property<?> property : properties) {
            if(property.getKey().equals(name))
                return property;
        }
        throw new PropertyNotFoundException("Property \"" + name + "\" does not exist");
    }
}
