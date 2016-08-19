package partsOfSpeech;

public class Conjunction implements PartOfSpeech {

    public Conjunction(String[] grammemeArray){
        for (String grammema : grammemeArray) {
            switch(grammema) {
                default:
                    throw new RuntimeException("Unknown property of the conjunction - " + grammema);
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
