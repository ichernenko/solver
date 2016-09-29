package morphologicalAnalysis.morphologicalGroups;

import morphologicalAnalysis.partsOfSpeech.PartOfSpeech;

import java.io.Serializable;

public class Unknown implements PartOfSpeech, Serializable {

    public Unknown(String[] grammemes) {
//        for (String grammeme : grammemes) {
//            switch (grammeme) {
//                default:
//                    throw new RuntimeException("Unknown property of the unknown word - " + grammeme);
//            }
//        }
    }

    @Override
    public String getAllProperties() {
        return "неизв";
    }

    @Override
    public void print() {
        System.out.println("Морфологическая группа: неизвестное слово");
    }
}
