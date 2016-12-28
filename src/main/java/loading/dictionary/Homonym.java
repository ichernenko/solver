package loading.dictionary;

public class Homonym {
    private int wordsNumber = 0;
    private WordProperty[] wordProperties;

    public Homonym(WordProperty[] wordProperties) {
        this.wordProperties = wordProperties;
    }

    public int getWordsNumber() {
        return wordsNumber;
    }
    public void setWordsNumber(int wordsNumber) {
        this.wordsNumber = wordsNumber;
    }
    public WordProperty[] getWordProperties() {
        return wordProperties;
    }
}
