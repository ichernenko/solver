package morphologicalAnalysis.partsOfSpeech;

import java.io.Serializable;

public class Participle implements PartOfSpeech, Serializable {
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

    public Participle(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                case "крат"  : shortForm = grammeme; break;
                case "сов"   :
                case "несов" :
                case "2вид"  : type = grammeme; break;
                case "перех" :
                case "непер" :
                case "пер/не": transitivity = grammeme; break;
                case "воз"   : reflexive = grammeme; break;
                case "страд" : passive = grammeme; break;
                case "прош"  :
                case "наст"  : tense = grammeme; break;
                case "ед"    :
                case "мн"    : singular = grammeme; break;
                case "муж"   :
                case "жен"   :
                case "ср"    : gender = grammeme; break;
                case "им"    :
                case "род"   :
                case "дат"   :
                case "вин"   :
                case "тв"    :
                case "пр"    :
                case "парт"  :
                case "счет"  :
                case "мест"  :
                case "зват"  : wordCase = grammeme; break;
                case "одуш"  :
                case "неод"  : animate = grammeme; break;
                default:
                    throw new RuntimeException("Unknown property of the participle - " + grammeme);
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
