package partsOfSpeech;

public class ParentheticalWord implements PartOfSpeech{

    public ParentheticalWord(String[] grammemeArray){
        for (int i = 0; i < grammemeArray.length; i++) {
            String grammema = grammemeArray[i];
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