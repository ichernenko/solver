package partsOfSpeech;

public class Interjection implements PartOfSpeech{

    public Interjection(String[] grammemeArray){
        for (int i = 0; i < grammemeArray.length; i++) {
            String grammema = grammemeArray[i];
            switch(grammema) {
                default:
                    throw new RuntimeException("Unknown property of the interjection - " + grammema);
            }
        }
    }

    @Override
    public String getAllProperties() {
        return  "межд ";
    }

    @Override
    public void print() {
        System.out.println("Часть речи: междометие");
    }
}