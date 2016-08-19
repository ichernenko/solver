package partsOfSpeech;

public class Particle implements PartOfSpeech{

    public Particle(String[] grammemeArray){
        for (String grammema : grammemeArray) {
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
