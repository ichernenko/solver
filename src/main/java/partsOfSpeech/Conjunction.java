package partsOfSpeech;

public class Conjunction implements PartOfSpeech {

    public Conjunction(String[] grammemeArray){
        for (int i = 0; i < grammemeArray.length; i++) {
            String grammema = grammemeArray[i];
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
