import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PhraseHandler {
    private String phrase = null;
    static String fileName = "src\\main\\resources\\mainDictionary.dic";


    PhraseHandler(String phrase) {
        this.phrase = phrase;
    }

    public void printFoundVerb() {
        try {
            Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)
                    .filter(a -> a.equals(phrase))
                    .forEach(System.out::println);
        }
        catch(Exception e) {
            System.out.println("TTTTT");
        }
    }
}
