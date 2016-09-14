package morphologicalAnalysis.partsOfSpeech;

import java.io.Serializable;

public class ParentheticalWord implements PartOfSpeech, Serializable {

    public ParentheticalWord(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                default:
                    throw new RuntimeException("Unknown property of the parenthetical word - " + grammeme);
            }
        }
    }

    @Override
    public String getAllProperties() {
        return  "ввод ";
    }

    @Override
    public void print() {
        System.out.println("Часть речи: вводное слово");
    }
}