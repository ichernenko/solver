package morphologicalAnalysis.partsOfSpeech;

import java.io.Serializable;

public class Numeral implements PartOfSpeech, Serializable {
    private String category;
    private String singular;
    private String gender;
    private String wordCase;
    private String animate;

    public Numeral(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                case "кол"  :
                case "неопр":
                case "поряд":
                case "собир": category = grammeme; break;
                case "ед"   :
                case "мн"   : singular = grammeme; break;
                case "муж"  :
                case "жен"  :
                case "ср"   :
                case "общ"  : gender = grammeme; break;
                case "им"   :
                case "род"  :
                case "дат"  :
                case "вин"  :
                case "тв"   :
                case "пр"   :
                case "парт" :
                case "счет" :
                case "мест" :
                case "зват" : wordCase = grammeme; break;
                case "одуш" :
                case "неод" : animate = grammeme; break;
                default:
                    throw new RuntimeException("Unknown property of the numeral - " + grammeme);
            }
        }
    }

    @Override
    public void print() {
        System.out.println("Часть речи: имя числительное (категория: " + category +
                                                        ", число: " + singular +
                                                        ", род: " + gender +
                                                        ", падеж: " + wordCase +
                                                        ", одушевленность: " + animate + ")");
    }

    @Override
    public String getAllProperties() {
        return  "числ " +
                (category == null ? "" : category + " ") +
                (singular == null ? "" : singular + " ") +
                (gender == null ? "" : gender + " ") +
                (wordCase == null ? "" : wordCase  + " ") +
                (animate == null ? "" : animate);
    }

    public String getCategory() {
        return category;
    }
    public String getSingular() {
        return singular;
    }
    public String getGender() {
        return gender;
    }
    public String getWordCase() {
        return wordCase;
    }
    public String getAnimate() {
        return animate;
    }
}
