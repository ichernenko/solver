package partsOfSpeech;


public class AdverbialParticiple implements PartOfSpeech {
    private String type;
    private String transitivity;
    private String reflexive;
    private String tense;

    public AdverbialParticiple(String[] grammemeArray){
        for (String grammema : grammemeArray) {
            switch(grammema) {
                case "сов"   :
                case "несов" : type = grammema; break;
                case "перех" :
                case "непер" :
                case "пер/не": transitivity = grammema; break;
                case "воз"   : reflexive = grammema; break;
                case "прош"  :
                case "наст"  : tense = grammema; break;
                default:
                    throw new RuntimeException("Unknown property of the adverbial participle - " + grammema);
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
