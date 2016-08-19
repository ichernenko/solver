package partsOfSpeech;


public class Preposition implements PartOfSpeech{
    private String wordCase;

    public Preposition(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                case "им"  :
                case "род" :
                case "дат" :
                case "вин" :
                case "тв"  :
                case "пр"  : wordCase = grammeme; break;
                default:
                    throw new RuntimeException("Unknown property of the preposition - " + grammeme);
            }
        }
    }

    @Override
    public void print() {
        System.out.println("Часть речи: предлог (падеж: " + wordCase + ")");
    }

    @Override
    public String getAllProperties() {
        return  "предл " +
                (wordCase == null ? "" : wordCase);
    }

    public String getWordCase() {
        return wordCase;
    }
}
