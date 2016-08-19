package partsOfSpeech;

public class Adjective implements PartOfSpeech {
    private String shortForm;
    private String invariability;
    private String degreeOfComparison;
    private String singular;
    private String animate;
    private String gender;
    private String wordCase;

    public Adjective(String[] grammemeArray){
        for (String grammema : grammemeArray) {
            switch(grammema) {
                case "крат" : shortForm = grammema; break;
                case "неизм": invariability = grammema; break;
                case "сравн":
                case "прев" : degreeOfComparison = grammema; break;
                case "ед"   :
                case "мн"   : singular = grammema; break;
                case "муж"  :
                case "жен"  :
                case "ср"   :
                case "общ"  : gender = grammema; break;
                case "им"   :
                case "род"  :
                case "дат"  :
                case "вин"  :
                case "тв"   :
                case "пр"   :
                case "парт" :
                case "счет" :
                case "мест" :
                case "зват" : wordCase = grammema; break;
                case "одуш" :
                case "неод" : animate = grammema; break;
                default:
                    throw new RuntimeException("Unknown property of the adjective - " + grammema);
            }
        }
    }

    @Override
    public void print() {
        System.out.println("Часть речи: имя прилагательное (краткая форма: " + shortForm +
                                                            ", неизменяемость: " + invariability +
                                                            ", степень сравнения: " + degreeOfComparison +
                                                            ", число: " + singular +
                                                            ", род: " + gender +
                                                            ", падеж: " + wordCase +
                                                            ", одушевленность: " + animate +")");
    }

    @Override
    public String getAllProperties() {
        return  "прил " +
                (shortForm == null ? "" : shortForm + " ") +
                (invariability == null ? "" : invariability + " ") +
                (degreeOfComparison == null ? "" : degreeOfComparison + " ") +
                (singular == null ? "" : singular + " ") +
                (animate == null ? "" : animate + " ") +
                (gender == null ? "" : gender + " ") +
                (wordCase == null ? "" : wordCase);
    }

    public String getShortForm() {
        return shortForm;
    }
    public String getInvariability() {
        return invariability;
    }
    public String getDegreeOfComparison() {
        return degreeOfComparison;
    }
    public String getSingular() {
        return singular;
    }
    public String getAnimate() {
        return animate;
    }
    public String getGender() {
        return gender;
    }
    public String getWordCase() {
        return wordCase;
    }
}
