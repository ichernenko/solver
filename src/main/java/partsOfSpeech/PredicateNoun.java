package partsOfSpeech;

public class PredicateNoun implements PartOfSpeech{

    public PredicateNoun(String[] grammemeArray){
        for (int i = 0; i < grammemeArray.length; i++) {
            String grammema = grammemeArray[i];
            switch(grammema) {
                default:
                    throw new RuntimeException("Unknown property of the predicate noun - " + grammema);
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
