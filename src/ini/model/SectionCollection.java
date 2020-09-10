package ini.model;

import ini.exception.SectionNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SectionCollection {
    private final List<Section> sections = new ArrayList<>();

    public List<Section> toList() {
        return sections;
    }

    public void add(Section section) {
        sections.add(section);
    }

    public Section findByName(String name) {
        for(Section section : sections) {
            if(section.getName().equals(name))
                return section;
        }
        throw new SectionNotFoundException("Section \"" + name + "\" does not exist");
    }


}