package morphologicalAnalysis.morphologicalGroups;


import morphologicalAnalysis.partsOfSpeech.PartOfSpeech;

import java.io.Serializable;

public class NameRu  implements PartOfSpeech, Serializable {
    public NameRu(String[] grammemes) {
//        for (String grammeme : grammemes) {
//            switch (grammeme) {
//                default:
//                    throw new RuntimeException("Unknown property of the unknown word - " + grammeme);
//            }
//        }
    }

    @Override
    public String getAllProperties() {
        return "чел_имя";
    }

    @Override
    public void print() {
        System.out.println("Морфологическая группа: человеческое имя");
    }
}
