package semanticSpace;


import java.util.Date;
import java.util.List;

public class SemanticLinkImpl  implements SemanticLink{
    private String name;
    private List<String> properties;
    private int linkedId;
    private Date beginDate;
    private Date endDate;

    SemanticLinkImpl(String name, int linkedId) {
        this.name = name;
        this.linkedId = linkedId;

    }

    public String getName() {
        return name;
    }
    public int getLinkedId() {
        return linkedId;
    }
    public List<String> getProperties() {
        return properties;
    }
    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
