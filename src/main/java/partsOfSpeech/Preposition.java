package partsOfSpeech;


public class Preposition implements PartOfSpeech{
    private String wordCase;

    public Preposition(String[] grammemeArray){
        for (String grammema : grammemeArray) {
            switch(grammema) {
                case "им"  :
                case "род" :
                case "дат" :
                case "вин" :
                case "тв"  :
                case "пр"  : wordCase = grammema; break;
                default:
                    throw new RuntimeException("Unknown property of the preposition - " + grammema);
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
