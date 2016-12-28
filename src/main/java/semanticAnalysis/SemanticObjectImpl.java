package semanticAnalysis;


import java.util.HashMap;
import java.util.Map;

public class SemanticObjectImpl implements SemanticObject{
    Map<String, SemanticObjectProperty> semanticObjectProperties = new HashMap<>();

    SemanticObjectImpl() {
        // 1. Получаем информацию о структуре семантического объекта из БД (пока из БД, дальше посмотрим)
        // 2. Создаем семантический объект и заполняем все его свойства и "методы"
    }

    public void addSemanticObjectProperty(String semanticObjectPropertyName, SemanticObjectProperty semanticObjectPropertyValue) {
        semanticObjectProperties.put(semanticObjectPropertyName, semanticObjectPropertyValue);
    }

    @Override
    public void printAllSemanticObjectProperties() {

    }
}
