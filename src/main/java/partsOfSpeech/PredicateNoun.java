package partsOfSpeech;

public class PredicateNoun implements PartOfSpeech{

    public PredicateNoun(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                default:
                    throw new RuntimeException("Unknown property of the predicate noun - " + grammeme);
            }
        }
    }

    @Override
    public String getAllProperties() {
        return  "предик ";
    }

    @Override
    public void print() {
        System.out.println("Часть речи: предикатив");
    }}
