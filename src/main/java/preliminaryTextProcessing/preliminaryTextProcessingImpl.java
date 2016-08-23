package preliminaryTextProcessing;

public class PreliminaryTextProcessingImpl implements PreliminaryTextProcessing {
    private String text;

    public PreliminaryTextProcessingImpl(String text) {
        this.text = text;
    }

    @Override
    public String processText() {
        return  text.replaceAll("^\\s+|\\s+$","")
                .replaceAll("\\s+"," ")
                .replaceAll(" (,|;|\\.|!|\\?|:|\\)|\\}|\\]|%)","$1")
                .replaceAll("(\\(|\\{|\\[|№) ","$1")
                .replaceAll("\\.{3,}","…")
                .replaceAll("(\\.|,|;|!|\\?|:)\\1+","$1")
                .replaceAll("(!|\\?){2,}","¡")
                .replaceAll(" \\((\\?|\\!)\\)","")
                // удаление пробелов в числах
                .replaceAll("([0-9]) ([0-9])*?","$1$2")
                // pL - буквы русского алфавита
                .replaceAll("(,|;|\\.|!|\\?|:|\\)|\\}|\\]|¡)([0-9]|\\pL)", "$1 $2");
    }
}
