package morphologicalAnalysis.partsOfSpeech;

import java.io.Serializable;

public class Interjection implements PartOfSpeech, Serializable {

    public Interjection(String[] grammemes){
//        for (String grammeme : grammemes) {
//            switch(grammeme) {
//                default:
//                    throw new RuntimeException("Unknown property of the interjection - " + grammeme);
//            }
//        }
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