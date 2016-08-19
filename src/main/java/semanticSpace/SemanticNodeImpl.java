package semanticSpace;

import java.util.List;

public class SemanticNodeImpl implements SemanticNode {
    private int id;
    private String name;
    private List<String> properties;
    private List<SemanticLink> links;

    SemanticNodeImpl(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<String> getProperties() {
        return properties;
    }
    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
    public List<SemanticLink> getLinks() {
        return links;
    }
    public void setLinks(List<SemanticLink> links) {
        this.links = links;
    }
}
