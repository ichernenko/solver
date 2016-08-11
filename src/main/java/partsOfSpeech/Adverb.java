package partsOfSpeech;

public class Adverb implements PartOfSpeech{
    private String category;
    private String adverbial;
    private String attributive;

    public Adverb(String[] grammemeArray){
        for (int i = 0; i < grammemeArray.length; i++) {
            String grammema = grammemeArray[i];
            switch(grammema) {
                case "обст"  : ;
                case "опред" : ;
                case "вопр"  : ;
                case "сравн" : category = grammema; break;
                case "врем"  : ;
                case "места" : ;
                case "напр"  : ;
                case "причин": ;
                case "цель"  : adverbial = grammema; break;
                case "кач"   : ;
                case "спос"  : ;
                case "степ"  : attributive = grammema; break;
                default:
                    throw new RuntimeException("Unknown property of the adverb - " + grammema);
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
