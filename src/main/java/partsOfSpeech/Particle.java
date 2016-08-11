package partsOfSpeech;

public class Particle implements PartOfSpeech{

    public Particle(String[] grammemeArray){
        for (int i = 0; i < grammemeArray.length; i++) {
            String grammema = grammemeArray[i];
            switch(grammema) {
                default:
                    throw new RuntimeException("Unknown property of the particle - " + grammema);
            }
        }
    }

    @Override
    public String getAllProperties() {
        return  "част ";
    }

    @Override
    public void print() {
        System.out.println("Часть речи: частица");
    }
}
