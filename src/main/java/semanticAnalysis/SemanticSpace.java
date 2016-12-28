package semanticAnalysis;


public interface SemanticSpace {
    void addSemanticObject(Object object);
    void changeSemanticObjectProperty(String objectName, String objectProperty, Object objectValue);
    void printAllSemanticObjects();
}
