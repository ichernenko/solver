package partsOfSpeech;

public class Verb implements PartOfSpeech {
    private String type;
    private String transitivity;
    private String reflexive;
    private String infinitive;
    private String tense;
    private String singular;
    private String gender;
    private String person;
    private String imperative;

    public Verb(String[] grammemeArray){
        for (String grammema : grammemeArray) {
            switch(grammema) {
                case "сов"   :
                case "несов" :
                case "2вид"  : type = grammema; break;
                case "перех" :
                case "непер" :
                case "пер/не": transitivity = grammema; break;
                case "воз"   : reflexive = grammema; break;
                case "инф"   : infinitive = grammema; break;
                case "прош"  :
                case "наст"  :
                case "буд"   : tense = grammema; break;
                case "ед"    :
                case "мн"    : singular = grammema; break;
                case "муж"   :
                case "жен"   :
                case "ср"    :
                case "общ"   : gender = grammema; break;
                case "1-е"   :
                case "2-е"   :
                case "3-е"   :
                case "безл"  : person = grammema; break;
                case "пов"   : imperative = grammema; break;
                default:
                    throw new RuntimeException("Unknown property of the verb - " + grammema);
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
