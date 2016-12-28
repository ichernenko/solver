package semanticAnalysis;


import java.util.HashMap;
import java.util.Map;

public class SemanticSpaceImpl implements SemanticSpace {
    private Map<String, SemanticObject> semanticObjects = new HashMap<>();

    @Override
    public void printAllSemanticObjects() {
        semanticObjects.forEach((semanticObjectKey, semanticObjectVakue) -> {
            System.out.println(semanticObjectKey.toString());
        });
        System.out.println("Работает!!!");
    }

    @Override
    public void addSemanticObject(String semanticObjectName, SemanticObject semanticObject) {
        semanticObjects.put(semanticObjectName, semanticObject);
    }

    @Override
    public void changeSemanticObjectProperty(String semanticObjectName, String semanticObjectProperty, Object semanticObjectValue) {
        SemanticObject semanticObject = semanticObjects.get(semanticObjectName);
      //  SemanticObjectProperty semanticObjectProperty = semanticObject.getSemanticObjectProperty(semanticObjectProperty);
    }

    public static void main(String[] args) {
        SemanticSpaceImpl semanticSpace = new SemanticSpaceImpl();
        semanticSpace.addSemanticObject("");
        semanticSpace.printAllSemanticObjects();
    }
}
