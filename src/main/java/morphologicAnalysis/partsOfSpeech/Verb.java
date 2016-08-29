package morphologicAnalysis.partsOfSpeech;

import java.io.Serializable;

public class Verb implements PartOfSpeech, Serializable {
    private String type;
    private String transitivity;
    private String reflexive;
    private String infinitive;
    private String tense;
    private String singular;
    private String gender;
    private String person;
    private String imperative;

    public Verb(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                case "сов"   :
                case "несов" :
                case "2вид"  : type = grammeme; break;
                case "перех" :
                case "непер" :
                case "пер/не": transitivity = grammeme; break;
                case "воз"   : reflexive = grammeme; break;
                case "инф"   : infinitive = grammeme; break;
                case "прош"  :
                case "наст"  :
                case "буд"   : tense = grammeme; break;
                case "ед"    :
                case "мн"    : singular = grammeme; break;
                case "муж"   :
                case "жен"   :
                case "ср"    :
                case "общ"   : gender = grammeme; break;
                case "1-е"   :
                case "2-е"   :
                case "3-е"   :
                case "безл"  : person = grammeme; break;
                case "пов"   : imperative = grammeme; break;
                default:
                    throw new RuntimeException("Unknown property of the verb - " + grammeme);
            }
        }
    }

    @Override
    public void print() {
        System.out.println("Часть речи: глагол (тип: " + type +
                                            ", переходность: " + transitivity +
                                            ", возвратность: " + reflexive +
                                            ", инфинитив: " + infinitive +
                                            ", время: " + tense +
                                            ", число: " + singular +
                                            ", род: " + gender +
                                            ", лицо: " + person +
                                            ", наклонение: " + imperative + ")");
    }

    @Override
    public String getAllProperties() {
        return  "гл " +
                (type == null ? "" : type + " ") +
                (transitivity == null ? "" : transitivity + " ") +
                (reflexive == null ? "" : reflexive + " ") +
                (infinitive == null ? "" : infinitive  + " ") +
                (tense == null ? "" : tense  + " ") +
                (singular == null ? "" : singular  + " ") +
                (gender == null ? "" : gender  + " ") +
                (person == null ? "" : person  + " ") +
                (imperative == null ? "" : imperative);
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
    public String getInfinitive() {
        return infinitive;
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
    public String getPerson() {
        return person;
    }
    public String getImperative() {
        return imperative;
    }
}
