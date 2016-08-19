package partsOfSpeech;

public class Conjunction implements PartOfSpeech {

    public Conjunction(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                default:
                    throw new RuntimeException("Unknown property of the conjunction - " + grammeme);
            }
        }
    }

    @Override
    public String getAllProperties() {
        return  "союз ";
    }

    @Override
    public void print() {
        System.out.println("Часть речи: союз");
    }
}
