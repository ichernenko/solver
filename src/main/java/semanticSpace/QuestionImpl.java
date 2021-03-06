package semanticSpace;

public class QuestionImpl implements Question {
    private int code = 0;

    // Для создания прототипа возьмем первоначальную фразу
    // "Мальчик курит папиросу."
    // Закрытые вопросы, которые можно задать:
    // 1. Кто курит папиросу? - вопрос "кто делает действие над чем-то"
    // 2. Кто курит? - вопрос "кто делает это действие"
    // 3. Что делает мальчик? - вопрос "о действии мальчика"
    // 4. Что курит мальчик? - вопрос "об объекте действия мальчика"
    // Если заданный вопрос соответствует шаблонному, то ему присваивается код
    public QuestionImpl(String question) {
        switch(question) {
            case "Кто курит папиросу?"  : code = 1; break;
            case "Кто курит?"           : code = 2; break;
            case "Что делает мальчик?"  : code = 3; break;
            case "Что курит мальчик?"   : code = 4; break;
            default                     : code = 0;
        }
    }

    public int getCode() {
        return code;
    }
}
