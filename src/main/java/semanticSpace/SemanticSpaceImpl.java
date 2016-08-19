package semanticSpace;

public class SemanticSpaceImpl implements SemanticSpace {
    private static SemanticSpace space = new SemanticSpaceImpl();
    private SemanticNode[] nodes;

    private SemanticSpaceImpl() {
        // Вообще, конструктор должен принимать на вход
        // массивы семантических данных с предыдущего уровня,
        // а именно, с семантического анализа.
        // Пока это прототип! "Мальчик курит папиросу."
    }

    public static SemanticSpace getInstance() {
        return space;
    }

    @Override
    public String getAnswer(Question question) {
        return null;
    }
}
