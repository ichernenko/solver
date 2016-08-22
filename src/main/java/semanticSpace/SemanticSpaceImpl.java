package semanticSpace;

public class SemanticSpaceImpl implements SemanticSpace {
    private SemanticNode[] nodes;

    public SemanticSpaceImpl() {
        // Вообще, конструктор должен принимать на вход
        // массивы семантических данных с предыдущего уровня,
        // а именно, с семантического анализа.
        // Пока это прототип! "Мальчик курит папиросу."
    }

    @Override
    public String getAnswer(Question question) {
        String answer;
        switch(question.getCode()) {
            case 1: answer = "Мальчик"; break;
            case 2: answer = "Мальчик"; break;
            case 3: answer = "Курит"; break;
            case 4: answer = "Папиросу"; break;
            default: answer = "Нет информации"; break;
        }
        return answer;
    }
}
