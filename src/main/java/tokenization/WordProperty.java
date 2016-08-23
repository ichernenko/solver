package tokenization;

import partsOfSpeech.*;


public class WordProperty {
    private int lemmaId;
    private PartOfSpeech partOfSpeech;

    WordProperty(int lemmaId, String partOfSpeechString, String tag) {
        this.lemmaId = lemmaId;
        String[] grammemes = tag != null ? tag.split(" ") : new String[]{};

        switch(partOfSpeechString) {
            case "сущ"    : partOfSpeech = new Noun(grammemes); break;
            case "прил"   : partOfSpeech = new Adjective(grammemes); break;
            case "числ"   : partOfSpeech = new Numeral(grammemes); break;
            case "мест"   : partOfSpeech = new Pronoun(grammemes); break;
            case "гл"     : partOfSpeech = new Verb(grammemes); break;
            case "нар"    : partOfSpeech = new Adverb(grammemes); break;
            case "предик" : partOfSpeech = new PredicateNoun(grammemes); break;
            case "прич"   : partOfSpeech = new Participle(grammemes); break;
            case "дееп"   : partOfSpeech = new AdverbialParticiple(grammemes); break;
            case "предл"  : partOfSpeech = new Preposition(grammemes); break;
            case "союз"   : partOfSpeech = new Conjunction(grammemes); break;
            case "част"   : partOfSpeech = new Particle(grammemes); break;
            case "межд"   : partOfSpeech = new Interjection(grammemes); break;
            case "ввод"   : partOfSpeech = new ParentheticalWord(grammemes); break;
        }
    }

    public int getLemmaId() {
        return lemmaId;
    }
    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }
}