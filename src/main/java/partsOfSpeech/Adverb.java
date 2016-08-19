package partsOfSpeech;

public class Adverb implements PartOfSpeech{
    private String category;
    private String adverbial;
    private String attributive;

    public Adverb(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                case "обст"  :
                case "опред" :
                case "вопр"  :
                case "сравн" : category = grammeme; break;
                case "врем"  :
                case "места" :
                case "напр"  :
                case "причин":
                case "цель"  : adverbial = grammeme; break;
                case "кач"   :
                case "спос"  :
                case "степ"  : attributive = grammeme; break;
                default:
                    throw new RuntimeException("Unknown property of the adverb - " + grammeme);
            }
        }
    }

    @Override
    public void print() {
        System.out.println("Часть речи: наречие (разряд: " + category +
                                    ", обстоятельственное: " + adverbial +
                                    ", определительное: " + attributive + ")");
    }

    @Override
    public String getAllProperties() {
        return  "нар " +
                (category == null ? "" : category + " ") +
                (adverbial == null ? "" : adverbial + " ") +
                (attributive == null ? "" : attributive);
    }

    public String getCategory() {
        return category;
    }
    public String getAdverbial() {
        return adverbial;
    }
    public String getAttributive() {
        return attributive;
    }
}
