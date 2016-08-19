package partsOfSpeech;

public class Participle implements PartOfSpeech{
    private String shortForm;
    private String type;
    private String transitivity;
    private String reflexive;
    private String passive;
    private String tense;
    private String singular;
    private String gender;
    private String wordCase;
    private String animate;

    public Participle(String[] grammemeArray){
        for (String grammema : grammemeArray) {
            switch(grammema) {
                case "крат"  : shortForm = grammema; break;
                case "сов"   :
                case "несов" :
                case "2вид"  : type = grammema; break;
                case "перех" :
                case "непер" :
                case "пер/не": transitivity = grammema; break;
                case "воз"   : reflexive = grammema; break;
                case "страд" : passive = grammema; break;
                case "прош"  :
                case "наст"  : tense = grammema; break;
                case "ед"    :
                case "мн"    : singular = grammema; break;
                case "муж"   :
                case "жен"   :
                case "ср"    : gender = grammema; break;
                case "им"    :
                case "род"   :
                case "дат"   :
                case "вин"   :
                case "тв"    :
                case "пр"    :
                case "парт"  :
                case "счет"  :
                case "мест"  :
                case "зват"  : wordCase = grammema; break;
                case "одуш"  :
                case "неод"  : animate = grammema; break;
                default:
                    throw new RuntimeException("Unknown property of the participle - " + grammema);
            }
        }
    }

    @Override
    public void print() {
        System.out.println("Часть речи: причастие (форма: " + shortForm +
                                                ", тип: " + type +
                                                ", переходность: " + transitivity +
                                                ", возвратность: " + reflexive +
                                                ", страдательность: " + passive +
                                                ", время: " + tense +
                                                ", число: " + singular +
                                                ", род: " + gender +
                                                ", падеж: " + wordCase +
                                                ", одушевленность: " + animate + ")");
    }

    @Override
    public String getAllProperties() {
        return  "прич " +
                (shortForm == null ? "" : shortForm + " ") +
                (type == null ? "" : type + " ") +
                (transitivity == null ? "" : transitivity + " ") +
                (reflexive == null ? "" : reflexive + " ") +
                (passive == null ? "" : passive + " ") +
                (tense == null ? "" : tense + " ") +
                (singular == null ? "" : singular + " ") +
                (gender == null ? "" : gender + " ") +
                (wordCase == null ? "" : wordCase + " ") +
                (animate == null ? "" : animate);
    }

    public String getShortForm() {
        return shortForm;
    }
    public String getType() {
        return type;
    }
    public String getTransitivity() {
        return transitivity;
    }
    public String getReflexive() {
        return reflexive;
    }
    public String getPassive() {
        return passive;
    }
    public String getTense() {
        return tense;
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
