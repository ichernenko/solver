package partsOfSpeech;

public class Noun implements PartOfSpeech {
    private String animate;
    private String singular;
    private String gender;
    private String wordCase;
    private String common;

    public Noun(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                case "одуш":
                case "неод": animate = grammeme; break;
                case "ед"  :
                case "мн"  : singular = grammeme; break;
                case "муж" :
                case "жен" :
                case "ср"  :
                case "общ" : gender = grammeme; break;
                case "им"  :
                case "род" :
                case "дат" :
                case "вин" :
                case "тв"  :
                case "пр"  :
                case "парт":
                case "счет":
                case "мест":
                case "зват": wordCase = grammeme; break;
                case "нарц":
                case "собс": common = grammeme; break;
                default:
                    throw new RuntimeException("Unknown property of the noun - " + grammeme);
            }
        }
    }

    @Override
    public void print() {
        System.out.println("Часть речи: имя существительное (одушевленность: " + animate +
                ", число: " + singular +
                ", род: " + gender +
                ", падеж: " + wordCase +
                ", нарицательность: " + common + ")");
    }

    @Override
    public String getAllProperties() {
        return  "сущ " +
                (animate == null ? "" : animate + " ") +
                (singular == null ? "" : singular + " ") +
                (gender == null ? "" : gender + " ") +
                (wordCase == null ? "" : wordCase + " ") +
                (common == null ? "" : common);
    }

    public String getAnimate() {
        return animate;
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
    public String getCommon() {
        return common;
    }
}
