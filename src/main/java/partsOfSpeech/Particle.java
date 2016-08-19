package partsOfSpeech;

public class Particle implements PartOfSpeech{

    public Particle(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                default:
                    throw new RuntimeException("Unknown property of the particle - " + grammeme);
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
