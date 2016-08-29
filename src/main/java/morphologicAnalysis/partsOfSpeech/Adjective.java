package morphologicAnalysis.partsOfSpeech;

import java.io.Serializable;

public class Adjective implements PartOfSpeech, Serializable {
    private String shortForm;
    private String invariability;
    private String degreeOfComparison;
    private String singular;
    private String animate;
    private String gender;
    private String wordCase;

    public Adjective(String[] grammemes){
        for (String grammeme : grammemes) {
            switch(grammeme) {
                case "крат" : shortForm = grammeme; break;
                case "неизм": invariability = grammeme; break;
                case "сравн":
                case "прев" : degreeOfComparison = grammeme; break;
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
                    throw new RuntimeException("Unknown property of the adjective - " + grammeme);
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
