package semanticSpace;

import java.util.List;

public class SemanticNodeImpl implements SemanticNode {
    private int id;
    private String name;
    private List<String> propertyArray;
    private List<SemanticLink> linkArray;

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
    public List<String> getPropertyArray() {
        return propertyArray;
    }
    public void setPropertyArray(List<String> propertyArray) {
        this.propertyArray = propertyArray;
    }
    public List<SemanticLink> getLinkArray() {
        return linkArray;
    }
    public void setLinkArray(List<SemanticLink> linkArray) {
        this.linkArray = linkArray;
    }
}
