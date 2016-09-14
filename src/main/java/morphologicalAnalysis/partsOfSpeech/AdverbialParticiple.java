package morphologicalAnalysis.partsOfSpeech;


import java.io.Serializable;

public class AdverbialParticiple implements PartOfSpeech, Serializable {
    private String type;
    private String transitivity;
    private String reflexive;
    private String tense;

    public AdverbialParticiple(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                case "сов"   :
                case "несов" : type = grammeme; break;
                case "перех" :
                case "непер" :
                case "пер/не": transitivity = grammeme; break;
                case "воз"   : reflexive = grammeme; break;
                case "прош"  :
                case "наст"  : tense = grammeme; break;
                default:
                    throw new RuntimeException("Unknown property of the adverbial participle - " + grammeme);
            }
        }
    }

    @Override
    public void print() {
        System.out.println("Часть речи: деепричастие (тип: " + type +
                                                    ", переходность: " + transitivity +
                                                    ", возвратность: " + reflexive +
                                                    ", время: " + tense + ")");
    }

    @Override
    public String getAllProperties() {
        return  "дееп " +
                (type == null ? "" : type + " ") +
                (transitivity == null ? "" : transitivity + " ") +
                (reflexive == null ? "" : reflexive + " ") +
                (tense == null ? "" : tense);
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
    public String getTense() {
        return tense;
    }
}
