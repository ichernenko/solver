package partsOfSpeech;

public class Pronoun implements PartOfSpeech {
    private String type;
    private String singular;
    private String gender;
    private String wordCase;
    private String animate;

    public Pronoun(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                case "сущ"  :
                case "прил" :
                case "числ" :
                case "нар"  : type = grammeme; break;
                case "ед"   :
                case "мн"   : singular = grammeme; break;
                case "муж"  :
                case "жен"  :
                case "ср"   :
                case "общ"  : gender = grammeme; break;
                case "им"   :
                case "род"  :
                case "дат"  :
                case "вин"  :
                case "тв"   :
                case "пр"   :
                case "парт" :
                case "счет" :
                case "мест" :
                case "зват" : wordCase = grammeme; break;
                case "одуш" :
                case "неод" : animate = grammeme; break;
                default:
                    throw new RuntimeException("Unknown property of the pronoun - " + grammeme);
            }
        }
    }

    @Override
    public void print() {
        System.out.println("Часть речи: местоимение (тип: " + type +
                                                ", число: " + singular +
                                                ", род: " + gender +
                                                ", падеж: " + wordCase +
                                                ", одушевленность: " + animate + ")");
    }

    @Override
    public String getAllProperties() {
        return  "мест " +
                (type == null ? "" : type + " ") +
                (singular == null ? "" : singular + " ") +
                (gender == null ? "" : gender + " ") +
                (wordCase == null ? "" : wordCase  + " ") +
                (animate == null ? "" : animate);
    }

    public String getType() {
        return type;
    }
    public String getSingular() {
        return singular;
    }
    public String getGender() {
        return gender;
    }
    public String getWordCase() {
        return wordCase;
    }
    public String getAnimate() {
        return animate;
    }
}
