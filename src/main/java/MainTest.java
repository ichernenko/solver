
public class MainTest {
    public static void main(String... args) {
        PhraseHandler ph = new PhraseHandler("  рыть брать мыла  /раму сушить");
        //ph.printAllLexemes();
        //ph.printAllWordProperty();
        ph.findLexemeInDictionary();
        ph.printAllFoundLexemes();
    }
}
