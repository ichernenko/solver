package partsOfSpeech;

public class ParentheticalWord implements PartOfSpeech{

    public ParentheticalWord(String[] grammemeArray){
        for (String grammema : grammemeArray) {
            switch(grammema) {
                default:
                    throw new RuntimeException("Unknown property of the parenthetical word - " + grammema);
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